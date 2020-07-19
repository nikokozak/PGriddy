import java.util.ArrayList;

public class Gradients {

    public static Point_Grid apply_radGradient(int _col, int _row, double _rad, double _init_weight, boolean _inverse, boolean _blend, double _opacity, Point_Grid _pg) {

        // Modifies weights of Grid_Points, adding to a cubic radial gradient
        // Distances from user-define centerpoint are calculated using an approximation (non-sqrt)
        // Where:
        // _col, _row -> centerpoint of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _init_weight -> initial weight value for gradient
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _opacity -> opacity of gradient

        double MAX = 1, MIN = 0;
        double dist;
        double rad = Math.pow(_rad, 2);

        Grid_Point center_point = Getters.get_grid_point(_col, _row, _pg);
        switch(_pg.check_quad(center_point)) {
            case 1 -> MAX = _pg.grid_approx_dist(center_point, Getters.get_grid_point(0, _pg.y - 1, _pg));
            case 2 -> MAX = _pg.grid_approx_dist(center_point, Getters.get_grid_point(_pg.x - 1, _pg.y - 1, _pg));
            case 3 -> MAX = _pg.grid_approx_dist(center_point, Getters.get_grid_point(_pg.x - 1, 0, _pg));
            case 4 -> MAX = _pg.grid_approx_dist(center_point, Getters.get_grid_point(0, 0, _pg));
        }

        rad = rad - MAX;
        double currWeight;

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                dist = _pg.grid_approx_dist(currPoint, center_point);
                if (_inverse) currWeight = Helpers.map(dist, MIN, MAX + rad, 0, _init_weight) * _opacity;
                else currWeight = Helpers.map(dist, MAX + rad, MIN, 0, _init_weight) * _opacity;
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + currWeight, 0, 1);
                else currPoint.weight = currWeight;
            }
        }

        return _pg;

    }

    public static Point_Grid apply_radGradient_slow(int _col, int _row, double _rad, double _init_weight, boolean _inverse, boolean _blend, double _opacity, Point_Grid _pg) {

        // Modifies weights of Grid_Points, adding to a linear radial gradient
        // Distances from user-define centerpoint are calculated using a^2 + b^2 = c^2
        // Therefore, sqrt() is used extensively - this function should be avoided at all costs if possible.
        // This version is approx. 4x slower than radGradient();
        // Where:
        // _col, _row -> centerpoint of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _init_weight -> initial weight value for gradient
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _opacity -> opacity of gradient

        double MAX = 1, MIN = 0;
        double dist;

        Grid_Point center_point = Getters.get_grid_point(_col, _row, _pg);
        switch(_pg.check_quad(center_point)) {
            case 1 -> MAX = _pg.grid_exact_dist(center_point, Getters.get_grid_point(0, _pg.y - 1, _pg));
            case 2 -> MAX = _pg.grid_exact_dist(center_point, Getters.get_grid_point(_pg.x - 1, _pg.y - 1, _pg));
            case 3 -> MAX = _pg.grid_exact_dist(center_point, Getters.get_grid_point(_pg.x - 1, 0, _pg));
            case 4 -> MAX = _pg.grid_exact_dist(center_point, Getters.get_grid_point(0, 0, _pg));
        }

        _rad = _rad - MAX;
        double currWeight;

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                dist = _pg.grid_exact_dist(currPoint, center_point);
                if (_inverse) currWeight = Helpers.map(dist, MIN, MAX + _rad, 0, _init_weight) * _opacity;
                else currWeight = Helpers.map(dist, MAX + _rad, MIN, 0, _init_weight) * _opacity;
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + currWeight, 0, 1);
                else currPoint.weight = currWeight;
            }
        }

        return _pg;

    }

    public static Point_Grid apply_radGradient_ease(int _col, int _row, double _rad, double _init_weight, double _feather, boolean _inverse, boolean _blend, double _opacity, Point_Grid _pg) {

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

        double MAX = 1, MIN = 0;
        double dist;
        double rad = Math.pow(_rad, 2);

        Grid_Point center_point = Getters.get_grid_point(_col, _row, _pg);
        switch(_pg.check_quad(center_point)) {
            case 1 -> MAX = _pg.grid_approx_dist(center_point, Getters.get_grid_point(0, _pg.y - 1, _pg));
            case 2 -> MAX = _pg.grid_approx_dist(center_point, Getters.get_grid_point(_pg.x - 1, _pg.y - 1, _pg));
            case 3 -> MAX = _pg.grid_approx_dist(center_point, Getters.get_grid_point(_pg.x - 1, 0, _pg));
            case 4 -> MAX = _pg.grid_approx_dist(center_point, Getters.get_grid_point(0, 0, _pg));
        }

        rad = rad - MAX;
        double currWeight;

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                dist = _pg.grid_approx_dist(currPoint, center_point);
                if (_inverse) currWeight = Helpers.easeInOutCubic(Helpers.map(dist, MIN, MAX + rad, 0, _init_weight), _init_weight, _init_weight,  _feather) * _opacity;
                else currWeight = Helpers.easeInOutCubic(Helpers.map(dist, MAX + rad, MIN, 0, _init_weight), 0, _init_weight,  _feather) * _opacity;
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + currWeight, 0, 1);
                else currPoint.weight = currWeight;
            }
        }

        return _pg;

    }

    public static Point_Grid apply_radGradient_slow_ease(int _col, int _row, double _rad, double _init_weight, double _feather, boolean _inverse, boolean _blend, double _opacity, Point_Grid _pg) {

        // Modifies weights of Grid_Points according to a in-out easing function.
        // Distances from user-define centerpoint are calculated using a^2 + b^2 = c^2
        // Therefore, sqrt() is used extensively - this function should be avoided at all costs if possible.
        // This version is approx. 4x slower than radGradient();
        // Where:
        // _col, _row -> centerpoint of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _init_weight -> initial weight value for gradient
        // _feather -> how "hard" the gradient edge is (period of easing func)
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _opacity -> opacity of gradient

        double MAX = 1, MIN = 0;
        double dist;

        Grid_Point center_point = Getters.get_grid_point(_col, _row, _pg);
        switch(_pg.check_quad(center_point)) {
            case 1 -> MAX = _pg.grid_exact_dist(center_point, Getters.get_grid_point(0, _pg.y - 1, _pg));
            case 2 -> MAX = _pg.grid_exact_dist(center_point, Getters.get_grid_point(_pg.x - 1, _pg.y - 1, _pg));
            case 3 -> MAX = _pg.grid_exact_dist(center_point, Getters.get_grid_point(_pg.x - 1, 0, _pg));
            case 4 -> MAX = _pg.grid_exact_dist(center_point, Getters.get_grid_point(0, 0, _pg));
        }

        _rad = _rad - MAX;
        double currWeight;

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                dist = _pg.grid_exact_dist(currPoint, center_point);
                if (_inverse) currWeight = Helpers.easeInOutCubic(Helpers.map(dist, MIN, MAX + _rad, 0, _init_weight), _init_weight, _init_weight,  _feather) * _opacity;
                else currWeight = Helpers.easeInOutCubic(Helpers.map(dist, MAX + _rad, MIN, 0, _init_weight), 0, _init_weight,  _feather) * _opacity;
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + currWeight, 0, 1);
                else currPoint.weight = currWeight;
            }
        }

        return _pg;

    }

    public static Point_Grid apply_sinGradient(int _col, int _row, double _rad, double _min_weight, double _max_weight, double _frequency, double _shift, boolean _inverse, boolean _blend, double _opacity, Point_Grid _pg) {

        // Modifies weights of Grid_Points according to a sin function.
        // Distances from user-define centerpoint are calculated using an approximation (non-sqrt)
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

        double MAX = 1, MIN = 0;
        double dist;
        double rad = Math.pow(_rad, 2);

        Grid_Point center_point = Getters.get_grid_point(_col, _row, _pg);
        switch(_pg.check_quad(center_point)) {
            case 1 -> MAX = _pg.grid_approx_dist(center_point, Getters.get_grid_point(0, _pg.y - 1, _pg));
            case 2 -> MAX = _pg.grid_approx_dist(center_point, Getters.get_grid_point(_pg.x - 1, _pg.y - 1, _pg));
            case 3 -> MAX = _pg.grid_approx_dist(center_point, Getters.get_grid_point(_pg.x - 1, 0, _pg));
            case 4 -> MAX = _pg.grid_approx_dist(center_point, Getters.get_grid_point(0, 0, _pg));
        }

        rad = rad - MAX;
        double currWeight;

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                dist = _pg.grid_approx_dist(currPoint, center_point);
                if (_inverse) currWeight = Helpers.map(Helpers.sinMap(Helpers.map(dist, MIN, MAX + rad, 0, 2*Math.PI), _frequency, _shift,  _max_weight), 1, -1, _min_weight, 1) * _opacity;
                else currWeight = Helpers.map(Helpers.sinMap(Helpers.map(dist, MIN, MAX, 0, 2*Math.PI), _frequency,  _shift,  _max_weight), -1, 1, _min_weight, 1) * _opacity;
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + currWeight, 0, 1);
                else currPoint.weight = currWeight;
            }
        }

        return _pg;

    }

    public static Point_Grid apply_sinGradient_slow(int _col, int _row, double _rad, double _min_weight, double _max_weight, double _frequency, double _shift, boolean _inverse, boolean _blend, double _opacity, Point_Grid _pg) {

        // Modifies weights of Grid_Points according to a sin function.
        // Distances from user-define centerpoint are calculated using pythagorean distance formula. Thus sqrt() is employed.
        // Avoid this function when possible - it is approx. 4 x slower than sinGradient()
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

        double MAX = 1, MIN = 0;
        double dist;

        Grid_Point center_point = Getters.get_grid_point(_col, _row, _pg);
        switch(_pg.check_quad(center_point)) {
            case 1 -> MAX = _pg.grid_exact_dist(center_point, Getters.get_grid_point(0, _pg.y - 1, _pg));
            case 2 -> MAX = _pg.grid_exact_dist(center_point, Getters.get_grid_point(_pg.x - 1, _pg.y - 1, _pg));
            case 3 -> MAX = _pg.grid_exact_dist(center_point, Getters.get_grid_point(_pg.x - 1, 0, _pg));
            case 4 -> MAX = _pg.grid_exact_dist(center_point, Getters.get_grid_point(0, 0, _pg));
        }

        _rad = _rad - MAX;
        double currWeight;

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                dist = _pg.grid_exact_dist(currPoint, center_point);
                if (_inverse) currWeight = Helpers.map(Helpers.sinMap(Helpers.map(dist, MIN, MAX + _rad, 0, 2*Math.PI), _frequency, _shift,  _max_weight), 1, -1, _min_weight, 1) * _opacity;
                else currWeight = Helpers.map(Helpers.sinMap(Helpers.map(dist, MIN, MAX, 0, 2*Math.PI), _frequency,  _shift,  _max_weight), -1, 1, _min_weight, 1) * _opacity;
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + currWeight, 0, 1);
                else currPoint.weight = currWeight;
            }
        }

        return _pg;
    }

}
