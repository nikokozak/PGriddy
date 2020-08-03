public class Applicators {

    public static <T extends Iterable<GridPoint>> void color(int _col, T _l) {

        // Sets all Points in the Grid to _col.
        // Where:
        // _col -> Processing color()

        for (GridPoint currPoint : _l) {
            currPoint.col(_col);
        }
    }

    public static void gridSelectColor(int _col, Selection _s, PointGrid _pg) {

        // Sets all Points in a given region of a Grid to _col.
        // Where:
        // _col -> Processing color()
        // _s -> Selection to limit color application

        GridPoint currPoint;

        for (int x = _s.startCol(); x <= _s.endCol(); x++) {
            for (int y = _s.startRow(); y <= _s.endRow(); y++) {
                currPoint = _pg.getPoint(x, y);
                currPoint.col(_col);
            }
        }
    }

    public static <T extends Iterable<GridPoint>> void weight(double _weight, T _l) {

        // Sets all Points to a given weight.
        // Where:
        // _weight -> weight to set. DOUBLE[0.0-1.0]

        _weight = Helpers.clamp(_weight, 0.0, 1.0);

        for (GridPoint currPoint : _l) {
            currPoint.weight(_weight);
        }

    }

    public static void gridSelectWeight(double _weight, Selection _s, PointGrid _pg) {

        // Sets all Points in a given region of a Grid to _weight.
        // Where:
        // _weight -> weight to set: DOUBLE[0.0-1.0]
        // _s -> Selection to limit weight application

        GridPoint currPoint;

        for (int x = _s.startCol(); x <= _s.endCol(); x++) {
            for (int y = _s.startRow(); y <= _s.endRow(); y++) {
                currPoint = _pg.getPoint(x, y);
                currPoint.weight(_weight);
            }
        }
    }

    public static <T extends Iterable<GridPoint>> void weightAdd(double _to_add, T _l) {

        // Adds a given weight to all points equally.
        // Where:
        // _to_add -> weight to add. DOUBLE[0.0-1.0]

        _to_add = Helpers.clamp(_to_add, 0.0, 1.0);

            for (GridPoint currPoint : _l) {
                currPoint.weight(Helpers.clamp(currPoint.weight() + _to_add, 0, 1));
            }
    }

    public static void gridSelectWeightAdd(double _to_add, Selection _s, PointGrid _pg) {

        // Adds a given weight to all points equally (within Selection).
        // Where:
        // _weight -> weight to set: DOUBLE[0.0-1.0]
        // _s -> Selection to limit weight application

        _to_add = Helpers.clamp(_to_add, 0.0, 1.0);
        GridPoint currPoint;

        for (int x = _s.startCol(); x <= _s.endCol(); x++) {
            for (int y = _s.startRow(); y <= _s.endRow(); y++) {
                currPoint = _pg.getPoint(x, y);
                currPoint.weight(currPoint.weight() + _to_add);
            }
        }
    }

    public static <T extends Iterable<GridPoint>> void weightMultiply(double _factor, T _l) {

        // Multiplies the weights of all points by a given number.
        // Where:
        // _factor -> factor by which to multiply

        for (GridPoint currPoint : _l) {
            currPoint.weight(Helpers.clamp(currPoint.weight() * _factor, 0, 1));
        }
    }

    public static void gridSelectWeightMultiply(double _factor, Selection _s, PointGrid _pg) {

        // Multiplies the weights of all points by a given number (within selection).
        // Where:
        // _factor -> factor by which to multiply
        // _s -> Selection to limit weight application

        GridPoint currPoint;

        for (int x = _s.startCol(); x <= _s.endCol(); x++) {
            for (int y = _s.startRow(); y <= _s.endRow(); y++) {
                currPoint = _pg.getPoint(x, y);
                currPoint.weight(Helpers.clamp(currPoint.weight() * _factor, 0, 1));
            }
        }
    }

    public static <T extends Iterable<GridPoint>> void weightReset(T _l) {

        // Sets all Points to weight 1.0.

        for (GridPoint currPoint : _l) {
            currPoint.weight(1.0);
        }
    }


    public static void gridSelectWeightReset(Selection _s, PointGrid _pg) {

        // Sets all Points to weight 1.0.
        // Where:
        // _s -> Selection to limit weight application

        GridPoint currPoint;

        for (int x = _s.startCol(); x <= _s.endCol(); x++) {
            for (int y = _s.startRow(); y <= _s.endRow(); y++) {
                currPoint = _pg.getPoint(x, y);
                currPoint.weight(1.0);
            }
        }
    }

    public static <T extends Iterable<GridPoint>> void weightFilter(double _low, double _high, T _l) {

        // Sets all weights outside the threshold to zero.
        // Where:
        // _low -> floor of threshold
        // _high -> ceiling of threshold

        for (GridPoint currPoint : _l) {
            if (currPoint.weight() < _low || currPoint.weight() > _high) {
                currPoint.weight(0);
            }
        }
    }

    public static void gridSelectWeightFilter(double _low, double _high, Selection _s, PointGrid _pg) {

        // Sets all weights outside the threshold to zero (within selection).
        // Where:
        // _low -> floor of threshold
        // _high -> ceiling of threshold
        // _s -> Selection to limit weight application

        GridPoint currPoint;

        for (int x = _s.startCol(); x <= _s.endCol(); x++) {
            for (int y = _s.startRow(); y <= _s.endRow(); y++) {
                currPoint = _pg.getPoint(x, y);
                if (currPoint.weight() < _low || currPoint.weight() > _high) {
                    currPoint.weight(1.0);
                }
            }
        }
    }

    public static <T extends Iterable<GridPoint>> T binaryWeight(double threshold, T l) {

        // Sets all weights in collection l to either 1.0 or 0.0.
        // If above threshold -> 1.0

        for (GridPoint point : l) {
            if (point.weight() > threshold) point.weight(1.0);
            else point.weight(0.0);
        }

        return l;

    }
}
