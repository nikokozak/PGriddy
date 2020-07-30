public class Gradient {

    public int gradientCenterX, gradientCenterY; // center of gradient in GridX and GridY coordinates.
    public double radius;
    public double initWeight;
    public double minWeight, maxWeight;
    public double frequency, shift;
    public boolean inverse, blend, subtract;
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
        this.radius = 10.0;
        this.initWeight = 1.0;
        this.minWeight = 0.0;
        this.maxWeight = 1.0;
        this.frequency = 0.5;
        this.shift = 2*Math.PI;
        this.feather = 1.0;
        this.inverse = false;
        this.blend = false;
        this.subtract = false;
        this.opacity = 1.0;
        this.gradientCenterX = -1; // -1 used to mark uninitialized centerPoint
        this.gradientCenterY = -1;

    }

    public PointGrid applyWeightsToPoints(PointGrid pg) {

        for (Grid_Point currPoint : pg) {

            switch (this.type) {
                case RADIAL -> applyWeightToPoint(false, Type.RADIAL, currPoint, pg);
                case RADIAL_SLOW -> applyWeightToPoint(true, Type.RADIAL, currPoint, pg);
                case RADIAL_SMOOTH -> applyWeightToPoint(false, Type.RADIAL_SMOOTH, currPoint, pg);
                case RADIAL_SMOOTH_SLOW -> applyWeightToPoint(true, Type.RADIAL_SMOOTH, currPoint, pg);
                case PERIODIC -> applyWeightToPoint(false, Type.PERIODIC, currPoint, pg);
                case PERIODIC_SLOW -> applyWeightToPoint(true, Type.PERIODIC, currPoint, pg);
            };

        }

        return pg;

    }

    private void applyWeightToPoint(boolean slow, Type type, Grid_Point currPoint, PointGrid pg) {

        Grid_Point centerPoint = makeDefaultCenterPoint(pg);

        double maximumDistance = slow?
                getFarthestDistanceFromPointExact(centerPoint.gridIndexX, centerPoint.gridIndexY, pg) :
                getFarthestDistanceFromPointApprox(centerPoint.gridIndexX, centerPoint.gridIndexY, pg);

        double currentDistance = slow? pg.grid_exact_dist(currPoint, centerPoint) : pg.grid_approx_dist(currPoint, centerPoint);
        double radius = (slow? this.radius : Math.pow(this.radius, 2)) - maximumDistance;
        double weight = 0;

        switch(type) {
            case RADIAL -> weight = getRadialWeightForPoint(radius, currentDistance, maximumDistance);
            case RADIAL_SMOOTH -> weight = getSmoothWeightForPoint(radius, currentDistance, maximumDistance);
            case PERIODIC -> weight = getSinWeightForPoint(radius, currentDistance, maximumDistance);
        }

        currPoint.weight = this.blend ? clampWeight(this.subtract ? (currPoint.weight - weight) : (currPoint.weight + weight)) : weight;

    }

    private double getRadialWeightForPoint (double radius, double distanceToCenter, double maxDistance) {
        double minimumDistance = 0;
        double weight;

        if (this.inverse) weight = Helpers.map(distanceToCenter, minimumDistance, maxDistance + radius, 0, this.initWeight) * this.opacity;
        else weight = Helpers.map(distanceToCenter, maxDistance + radius, minimumDistance, 0, this.initWeight) * this.opacity;

        return weight;

    }

    private double getSmoothWeightForPoint (double radius, double distanceToCenter, double maxDistance) {
        double minimumDistance = 0;
        double weight;

        if (this.inverse) weight = Helpers.easeInOutCubic(Helpers.map(distanceToCenter, minimumDistance, maxDistance + radius, 0, this.initWeight), this.initWeight, this.initWeight,  this.feather) * this.opacity;
        else weight = Helpers.easeInOutCubic(Helpers.map(distanceToCenter, maxDistance + radius, minimumDistance, 0, this.initWeight), 0, this.initWeight,  this.feather) * this.opacity;

        return weight;

    }

    private double getSinWeightForPoint (double radius, double distanceToCenter, double maxDistance) {
        double minimumDistance = 0;
        double weight;

        if (this.inverse) weight = Helpers.map(Helpers.sinMap(Helpers.map(distanceToCenter, minimumDistance, maxDistance + radius, 0, 2*Math.PI), this.frequency, this.shift,  this.maxWeight), 1, -1, this.minWeight, 1) * this.opacity;
        else weight = Helpers.map(Helpers.sinMap(Helpers.map(distanceToCenter, minimumDistance, maxDistance, 0, 2*Math.PI), this.frequency,  this.shift,  this.maxWeight), -1, 1, this.minWeight, 1) * this.opacity;

        return weight;

    }

    private double clampWeight (double weight) {

        return Helpers.clamp(weight, 0, 1);
    }

    private Grid_Point makeDefaultCenterPoint(PointGrid pg) {

        if (this.gradientCenterX == -1 || this.gradientCenterY == -1) {
            return pg.get_point(pg.xPoints / 2, pg.yPoints / 2);
        } else {
            return pg.get_point(this.gradientCenterX, this.gradientCenterY);
        }

    }

    private static double getFarthestDistanceFromPointExact(int _col, int _row, PointGrid _pg) {

        // Returns the greatest distance from current point possible within a given grid,
        // using Pythagorean method. Use approx version of this function if performance is a concern.
        // Where:
        // _col, _row -> grid coordinates of point to be sampled from.
        // _pg -> grid to sample from.

        double max_distance = 0;
        Grid_Point point = Getters.get_grid_point(_col, _row, _pg);

        switch(_pg.check_quad(point)) {
            case 1 -> max_distance = _pg.grid_exact_dist(point, Getters.get_grid_point(0, _pg.yPoints - 1, _pg));
            case 2 -> max_distance = _pg.grid_exact_dist(point, Getters.get_grid_point(_pg.xPoints - 1, _pg.yPoints - 1, _pg));
            case 3 -> max_distance = _pg.grid_exact_dist(point, Getters.get_grid_point(_pg.xPoints - 1, 0, _pg));
            case 4 -> max_distance = _pg.grid_exact_dist(point, Getters.get_grid_point(0, 0, _pg));
        }

        return max_distance;

    }

    private static double getFarthestDistanceFromPointApprox(int _col, int _row, PointGrid _pg) {

        // Returns the greatest distance from current point possible within a given grid,
        // using a relative dist approximation method.
        // Where:
        // _col, _row -> grid coordinates of point to be sampled from.
        // _pg -> grid to sample from.

        double max_distance = 0;
        Grid_Point point = Getters.get_grid_point(_col, _row, _pg);

        switch(_pg.check_quad(point)) {
            case 1 -> max_distance = _pg.grid_approx_dist(point, Getters.get_grid_point(0, _pg.yPoints - 1, _pg));
            case 2 -> max_distance = _pg.grid_approx_dist(point, Getters.get_grid_point(_pg.xPoints - 1, _pg.yPoints - 1, _pg));
            case 3 -> max_distance = _pg.grid_approx_dist(point, Getters.get_grid_point(_pg.xPoints - 1, 0, _pg));
            case 4 -> max_distance = _pg.grid_approx_dist(point, Getters.get_grid_point(0, 0, _pg));
        }

        return max_distance;

    }

}
