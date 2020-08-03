public class Applicators {

    public static <T extends Iterable<GridPoint>> void color(int col, T l) {

        /**
         * Sets all Points in the Grid to {@code col}.
         * Where:
         * @param col a Processing color().
         * @param l PointGrid or PointList.
         * @return void
         */

        for (GridPoint currPoint : l) {
            currPoint.col(col);
        }
    }

    public static void gridSelectColor(int col, Selection s, PointGrid pg) {

        /**
         * Sets all Points in a given region of a Grid to _col.
         * @param col a Processing color().
         * @param s a Selection to limit color application.
         * @param pg a PointGrid.
         * @return void
         */

        GridPoint currPoint;

        for (int x = s.startCol(); x <= s.endCol(); x++) {
            for (int y = s.startRow(); y <= s.endRow(); y++) {
                currPoint = pg.getPoint(x, y);
                currPoint.col(col);
            }
        }
    }

    public static <T extends Iterable<GridPoint>> void weight(double weight, T l) {

        /**
         * Sets all points to a given {@code weight}.
         * @param weight weight to set [0.0 - 1.0].
         * @param l a PointGrid or PointList.
         * @return void
         */

        weight = Helpers.clamp(weight, 0.0, 1.0);

        for (GridPoint currPoint : l) {
            currPoint.weight(weight);
        }

    }

    public static void gridSelectWeight(double weight, Selection s, PointGrid pg) {

        /**
         * Sets all GridPoints in a given region of a PointGrid to {@code weight}.
         * @param weight weight to set [0.0-1.0]
         * @param s Selection to limit weight application
         * @param pg a PointGrid.
         * @return void
         */

        GridPoint currPoint;

        for (int x = s.startCol(); x <= s.endCol(); x++) {
            for (int y = s.startRow(); y <= s.endRow(); y++) {
                currPoint = pg.getPoint(x, y);
                currPoint.weight(weight);
            }
        }
    }

    public static <T extends Iterable<GridPoint>> void weightAdd(double toAdd, T l) {

        /**
         * Adds a given weight to all points equally.
         * @param toAdd weight to add [0.0-1.0], addition clamped.
         * @param l a PointGrid or PointList.
         * @return void
         */

        toAdd = Helpers.clamp(toAdd, 0.0, 1.0);

            for (GridPoint currPoint : l) {
                currPoint.weight(Helpers.clamp(currPoint.weight() + toAdd, 0, 1));
            }
    }

    public static void gridSelectWeightAdd(double _to_add, Selection _s, PointGrid _pg) {

        /**
         * Adds a given weight to all points equally (within Selection).
         * @param weight weight to set [0.0-1.0]
         * @param s Selection to limit weight application.
         * @param pg a PointGrid.
         * @return void
         */

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

        /**
         * Multiplies the weights of all points by a given number.
         * @param factor factor by which to multiply weights (result clamped).
         * @param l a PointGrid or PointList.
         * @return void
         */

        for (GridPoint currPoint : _l) {
            currPoint.weight(Helpers.clamp(currPoint.weight() * _factor, 0, 1));
        }
    }

    public static void gridSelectWeightMultiply(double factor, Selection s, PointGrid pg) {

        /**
         * Multiplies the weights of all points by a given number (within Selection).
         * @param factor factor by which to multiply weights (result capped).
         * @param s Selection to limit factor application.
         * @param pg a PointGrid.
         * @return void
         */

        GridPoint currPoint;

        for (int x = s.startCol(); x <= s.endCol(); x++) {
            for (int y = s.startRow(); y <= s.endRow(); y++) {
                currPoint = pg.getPoint(x, y);
                currPoint.weight(Helpers.clamp(currPoint.weight() * factor, 0, 1));
            }
        }
    }

    public static <T extends Iterable<GridPoint>> void weightReset(T l) {

        /**
         * Sets all points to weight 1.0.
         * @param l a PointGrid or PointList
         * @return void
         */

        for (GridPoint currPoint : l) {
            currPoint.weight(1.0);
        }
    }


    public static void gridSelectWeightReset(Selection s, PointGrid pg) {

        /**
         * Sets all points to weight 1.0 (within Selection).
         * @param s Selection to limit reset.
         * @param pg a PointGrid.
         * @return void
         */

        GridPoint currPoint;

        for (int x = s.startCol(); x <= s.endCol(); x++) {
            for (int y = s.startRow(); y <= s.endRow(); y++) {
                currPoint = pg.getPoint(x, y);
                currPoint.weight(1.0);
            }
        }
    }

    public static <T extends Iterable<GridPoint>> void weightFilter(double low, double high, T l) {

        /**
         * Sets all weights outside the provided range to 0.0.
         * @param low range floor.
         * @param high range ceiling.
         * @param l a PointGrid or PointList.
         * @return void
         */

        for (GridPoint currPoint : l) {
            if (currPoint.weight() < low || currPoint.weight() > high) {
                currPoint.weight(0);
            }
        }
    }

    public static void gridSelectWeightFilter(double low, double high, Selection s, PointGrid pg) {

        /**
         * Sets all weights outside the provided range to 0.0 (within Selection).
         * @param low range floor.
         * @param high range ceiling.
         * @param s Selection to limit effect of function.
         * @param pg a PointGrid.
         * @return void
         */

        GridPoint currPoint;

        for (int x = s.startCol(); x <= s.endCol(); x++) {
            for (int y = s.startRow(); y <= s.endRow(); y++) {
                currPoint = pg.getPoint(x, y);
                if (currPoint.weight() < low || currPoint.weight() > high) {
                    currPoint.weight(1.0);
                }
            }
        }
    }

    public static <T extends Iterable<GridPoint>> T binaryWeight(double threshold, T l) {

        /**
         * Sets all weights to either 1.0 or 0.0.
         * @param threshold above this weight, GridPoint {@code weight} is set to 1.0.
         * @param l a PointList or PointGrid.
         * @return void
         */

        for (GridPoint point : l) {
            if (point.weight() > threshold) point.weight(1.0);
            else point.weight(0.0);
        }

        return l;

    }
}
