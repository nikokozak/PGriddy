public class Applicators {

    public static <T extends Iterable<Grid_Point>> void color(int _col, T _l) {

        // Sets all Points in the Grid to _col.
        // Where:
        // _col -> Processing color()

        for (Grid_Point currPoint : _l) {
            currPoint.col = _col;
        }
    }

    public static void grid_select_color(int _col, Selection _s, PointGrid _pg) {

        // Sets all Points in a given region of a Grid to _col.
        // Where:
        // _col -> Processing color()
        // _s -> Selection to limit color application

        Grid_Point currPoint;

        for (int x = _s.startCol; x <= _s.endCol; x++) {
            for (int y = _s.startRow; y <= _s.endRow; y++) {
                currPoint = _pg.get_point(x, y);
                currPoint.col = _col;
            }
        }
    }

    public static <T extends Iterable<Grid_Point>> void weight(double _weight, T _l) {

        // Sets all Points to a given weight.
        // Where:
        // _weight -> weight to set. DOUBLE[0.0-1.0]

        _weight = Helpers.clamp(_weight, 0.0, 1.0);

        for (Grid_Point currPoint : _l) {
            currPoint.weight = _weight;
        }

    }

    public static void grid_select_weight(double _weight, Selection _s, PointGrid _pg) {

        // Sets all Points in a given region of a Grid to _weight.
        // Where:
        // _weight -> weight to set: DOUBLE[0.0-1.0]
        // _s -> Selection to limit weight application

        Grid_Point currPoint;

        for (int x = _s.startCol; x <= _s.endCol; x++) {
            for (int y = _s.startRow; y <= _s.endRow; y++) {
                currPoint = _pg.get_point(x, y);
                currPoint.weight = _weight;
            }
        }
    }

    public static <T extends Iterable<Grid_Point>> void weight_add(double _to_add, T _l) {

        // Adds a given weight to all points equally.
        // Where:
        // _to_add -> weight to add. DOUBLE[0.0-1.0]

        _to_add = Helpers.clamp(_to_add, 0.0, 1.0);

            for (Grid_Point currPoint : _l) {
                currPoint.weight = Helpers.clamp(currPoint.weight + _to_add, 0, 1);
            }
    }

    public static void grid_select_weight_add(double _to_add, Selection _s, PointGrid _pg) {

        // Adds a given weight to all points equally (within Selection).
        // Where:
        // _weight -> weight to set: DOUBLE[0.0-1.0]
        // _s -> Selection to limit weight application

        _to_add = Helpers.clamp(_to_add, 0.0, 1.0);
        Grid_Point currPoint;

        for (int x = _s.startCol; x <= _s.endCol; x++) {
            for (int y = _s.startRow; y <= _s.endRow; y++) {
                currPoint = _pg.get_point(x, y);
                currPoint.weight += _to_add;
            }
        }
    }

    public static <T extends Iterable<Grid_Point>> void weight_multiply(double _factor, T _l) {

        // Multiplies the weights of all points by a given number.
        // Where:
        // _factor -> factor by which to multiply

        for (Grid_Point currPoint : _l) {
            currPoint.weight = Helpers.clamp(currPoint.weight * _factor, 0, 1);
        }
    }

    public static void grid_select_weight_multiply(double _factor, Selection _s, PointGrid _pg) {

        // Multiplies the weights of all points by a given number (within selection).
        // Where:
        // _factor -> factor by which to multiply
        // _s -> Selection to limit weight application

        Grid_Point currPoint;

        for (int x = _s.startCol; x <= _s.endCol; x++) {
            for (int y = _s.startRow; y <= _s.endRow; y++) {
                currPoint = _pg.get_point(x, y);
                currPoint.weight = Helpers.clamp(currPoint.weight * _factor, 0, 1);
            }
        }
    }

    public static <T extends Iterable<Grid_Point>> void weight_reset(T _l) {

        // Sets all Points to weight 1.0.

        for (Grid_Point currPoint : _l) {
            currPoint.weight = 1.0;
        }
    }


    public static void grid_select_weight_reset(Selection _s, PointGrid _pg) {

        // Sets all Points to weight 1.0.
        // Where:
        // _s -> Selection to limit weight application

        Grid_Point currPoint;

        for (int x = _s.startCol; x <= _s.endCol; x++) {
            for (int y = _s.startRow; y <= _s.endRow; y++) {
                currPoint = _pg.get_point(x, y);
                currPoint.weight = 1.0;
            }
        }
    }

    public static <T extends Iterable<Grid_Point>> void weight_filter(double _low, double _high, T _l) {

        // Sets all weights outside the threshold to zero.
        // Where:
        // _low -> floor of threshold
        // _high -> ceiling of threshold

        for (Grid_Point currPoint : _l) {
            if (currPoint.weight < _low || currPoint.weight > _high) {
                currPoint.weight = 0;
            }
        }
    }

    public static void grid_select_weight_filter(double _low, double _high, Selection _s, PointGrid _pg) {

        // Sets all weights outside the threshold to zero (within selection).
        // Where:
        // _low -> floor of threshold
        // _high -> ceiling of threshold
        // _s -> Selection to limit weight application

        Grid_Point currPoint;

        for (int x = _s.startCol; x <= _s.endCol; x++) {
            for (int y = _s.startRow; y <= _s.endRow; y++) {
                currPoint = _pg.get_point(x, y);
                if (currPoint.weight < _low || currPoint.weight > _high) {
                    currPoint.weight = 1.0;
                }
            }
        }
    }
}
