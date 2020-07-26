import java.util.ArrayList;

public class Gradient {

    public int gradientCenterX, gradientCenterY; // center of gradient in GridX and GridY coordinates.
    public double radius;
    public double initWeight;
    public double minWeight, maxWeight;
    public double frequency, shift;
    public boolean inverse, blend;
    public double opacity;
    public double feather;
    private final Type type;

    public Type getType() {
        return type;
    }

    public enum Type {
        RADIAL,
        RADIAL_SLOW,
        RADIAL_SMOOTH,
        RADIAL_SMOOTH_SLOW,
        PERIODIC,
        PERIODIC_SLOW
    }

    public Gradient(Type type) {

        this.type = type;
        this.radius = 1.0;
        this.initWeight = 0.0;
        this.minWeight = 0.0;
        this.maxWeight = 1.0;
        this.frequency = 0.5;
        this.shift = 2*Math.PI;
        this.feather = 1.0;
        this.inverse = false;
        this.blend = false;
        this.opacity = 1.0;
        this.gradientCenterX = -1; // -1 used to mark uninitialized centerPoint
        this.gradientCenterY = -1;

    }

    public Point_Grid applyValuesToPoints(Point_Grid pg) {

        for (Grid_Point currPoint : pg) {
            return switch (this.type) {
                case RADIAL -> apply_radGradient(false, pg);
                case RADIAL_SLOW -> apply_radGradient(true, pg);
                case RADIAL_SMOOTH -> apply_radGradient_ease(false, pg);
                case RADIAL_SMOOTH_SLOW -> apply_radGradient_ease(true, pg);
                case PERIODIC -> apply_sinGradient(false, pg);
                case PERIODIC_SLOW -> apply_sinGradient(true, pg);
            };
        }

        return pg;

    }

    public Point_Grid apply_radGradient(boolean slow, Point_Grid pg) {

        // Modifies weights of Grid_Points, adding to a cubic radial gradient
        // Distances from user-define centerpoint are calculated using a^2 + b^2 = c^2 in slow mode.
        // Therefore, sqrt() is used extensively - this function should be avoided at all costs if possible.
        // This version is approx. 4x slower than !slow;
        // Distances from user-define centerpoint are calculated using an approximation (non-sqrt) in !slow mode.
        // Where:
        // _col, _row -> centerpoint of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _init_weight -> initial weight value for gradient
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _opacity -> opacity of gradient

        Grid_Point centerPoint = makeDefaultCenterPoint(pg);

        double MIN = 0;
        double MAX = slow? get_farthest_distance_from_point_exact(this.gradientCenterX, this.gradientCenterY, pg) : get_farthest_distance_from_point_approx(this.gradientCenterX, this.gradientCenterY, pg);
        double dist;
        double rad = slow? this.radius : Math.pow(this.radius, 2);

        rad = rad - MAX;
        double currWeight;

        for (Grid_Point currPoint : pg) {

            dist = slow? pg.grid_exact_dist(currPoint, centerPoint) : pg.grid_approx_dist(currPoint, centerPoint);

            if (this.inverse) currWeight = Helpers.map(dist, MIN, MAX + rad, 0, this.initWeight) * this.opacity;
            else currWeight = Helpers.map(dist, MAX + rad, MIN, 0, this.initWeight) * this.opacity;

            currPoint.weight = this.blend ? clampWeight(currPoint.weight + currWeight) : currWeight;

        }

        return pg;

    }

    private Grid_Point makeDefaultCenterPoint(Point_Grid pg) {

        if (this.gradientCenterX == -1 || this.gradientCenterY == -1) {
            return pg.get_point(pg.x / 2, pg.y / 2);
        } else {
            return pg.get_point(this.gradientCenterX, this.gradientCenterY);
        }

    }

    public Point_Grid apply_radGradient_ease(boolean slow, Point_Grid pg) {

        // Modifies weights of Grid_Points according to a in-out easing function.
        // Distances from user-define centerpoint are calculated using an approximation (non-sqrt)
        // Where:
        // _col, _row -> centerpoint of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _init_weight -> initial weight value for gradient
        // _feather -> how "hard" the gradient edge is (period of easing func)
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _opacity -> opacity of gradient

        double MIN = 0;
        double MAX = slow? get_farthest_distance_from_point_exact(this.gradientCenterX, this.gradientCenterY, pg) : get_farthest_distance_from_point_approx(this.gradientCenterX, this.gradientCenterY, pg);
        double dist;
        double rad = slow? this.radius : Math.pow(this.radius, 2);

        rad = rad - MAX;
        double currWeight;

        for (Grid_Point currPoint : pg) {
            Grid_Point gradientCenter = Getters.get_grid_point(this.gradientCenterX, this.gradientCenterY, pg);

            dist = slow? pg.grid_exact_dist(currPoint, gradientCenter) : pg.grid_approx_dist(currPoint, gradientCenter);

            if (this.inverse) currWeight = Helpers.easeInOutCubic(Helpers.map(dist, MIN, MAX + rad, 0, this.initWeight), this.initWeight, this.initWeight,  this.feather) * this.opacity;
            else currWeight = Helpers.easeInOutCubic(Helpers.map(dist, MAX + rad, MIN, 0, this.initWeight), 0, this.initWeight,  this.feather) * this.opacity;

            currPoint.weight = this.blend ? clampWeight(currPoint.weight + currWeight) : currWeight;

        }

        return pg;

    }

    public Point_Grid apply_sinGradient(boolean slow, Point_Grid pg) {

        // Modifies weights of Grid_Points according to a sin function.
        // Distances from user-define centerpoint are calculated using an approximation (non-sqrt) in !slow mode and
        // with pythagorean method (sqrt) in slow mode. Avoid slow if performance is an issue.
        // Where:
        // _col, _row -> centerpoint of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _min_weight -> lower bound for applied weight
        // _max_weight -> upper bound for applied weight
        // _frequency -> frequency of sin function
        // _shift -> shift of sin function
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _opacity -> opacity of gradient

        double MIN = 0;
        double MAX = slow? get_farthest_distance_from_point_exact(this.gradientCenterX, this.gradientCenterY, pg) : get_farthest_distance_from_point_approx(this.gradientCenterX, this.gradientCenterY, pg);
        double dist;
        double rad = slow? this.radius : Math.pow(this.radius, 2);

        rad = rad - MAX;
        double currWeight;

        for (Grid_Point currPoint : pg) {
            Grid_Point gradientCenter = Getters.get_grid_point(this.gradientCenterX, this.gradientCenterY, pg);


            dist = slow? pg.grid_exact_dist(currPoint, gradientCenter) : pg.grid_approx_dist(currPoint, gradientCenter);
            if (this.inverse) currWeight = Helpers.map(Helpers.sinMap(Helpers.map(dist, MIN, MAX + rad, 0, 2*Math.PI), this.frequency, this.shift,  this.maxWeight), 1, -1, this.minWeight, 1) * this.opacity;
            else currWeight = Helpers.map(Helpers.sinMap(Helpers.map(dist, MIN, MAX, 0, 2*Math.PI), this.frequency,  this.shift,  this.maxWeight), -1, 1, this.minWeight, 1) * this.opacity;

            currPoint.weight = this.blend ? clampWeight(currPoint.weight + currWeight) : currWeight;

        }

        return pg;

    }

    private double clampWeight (double weight) {

        return Helpers.clamp(weight, 0, 1);
    }

    private static double get_farthest_distance_from_point_exact(int _col, int _row, Point_Grid _pg) {

        // Returns the greatest distance from current point possible within a given grid,
        // using Pythagorean method. Use approx version of this function if performance is a concern.
        // Where:
        // _col, _row -> grid coordinates of point to be sampled from.
        // _pg -> grid to sample from.

        double max_distance = 0;
        Grid_Point point = Getters.get_grid_point(_col, _row, _pg);

        switch(_pg.check_quad(point)) {
            case 1 -> max_distance = _pg.grid_exact_dist(point, Getters.get_grid_point(0, _pg.y - 1, _pg));
            case 2 -> max_distance = _pg.grid_exact_dist(point, Getters.get_grid_point(_pg.x - 1, _pg.y - 1, _pg));
            case 3 -> max_distance = _pg.grid_exact_dist(point, Getters.get_grid_point(_pg.x - 1, 0, _pg));
            case 4 -> max_distance = _pg.grid_exact_dist(point, Getters.get_grid_point(0, 0, _pg));
        }

        return max_distance;

    }

    private static double get_farthest_distance_from_point_approx(int _col, int _row, Point_Grid _pg) {

        // Returns the greatest distance from current point possible within a given grid,
        // using a relative dist approximation method.
        // Where:
        // _col, _row -> grid coordinates of point to be sampled from.
        // _pg -> grid to sample from.

        double max_distance = 0;
        Grid_Point point = Getters.get_grid_point(_col, _row, _pg);

        switch(_pg.check_quad(point)) {
            case 1 -> max_distance = _pg.grid_approx_dist(point, Getters.get_grid_point(0, _pg.y - 1, _pg));
            case 2 -> max_distance = _pg.grid_approx_dist(point, Getters.get_grid_point(_pg.x - 1, _pg.y - 1, _pg));
            case 3 -> max_distance = _pg.grid_approx_dist(point, Getters.get_grid_point(_pg.x - 1, 0, _pg));
            case 4 -> max_distance = _pg.grid_approx_dist(point, Getters.get_grid_point(0, 0, _pg));
        }

        return max_distance;

    }

}
