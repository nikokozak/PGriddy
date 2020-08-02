import java.util.*;

public class Getters {

    public static GridPoint getGridPoint(int _col, int _row, PointGrid _pg) {

        // Fetches a POINT from a POINT_GRID
        // Where:
        // _col -> column index of desired point
        // _row -> row index of desired point

        _col = Math.floorMod(_col, _pg.xPoints());
        _row = Math.floorMod(_row, _pg.yPoints());

        return _pg.points().get(_col).get(_row);

    }

    public static GridPoint getGridPointSafe(int _col, int _row, PointGrid _pg) {

        // Fetches a POINT from a POINT_GRID, throwing an exception if point is out of bounds.
        // Where:
        // _col -> column index of desired point
        // _row -> row index of desired point

        if (_col > _pg.xPoints() - 1 || _row > _pg.yPoints() - 1 || _col < 0 || _row < 0) {
            throw new java.lang.RuntimeException("Unsafe Point");
        } else {
            return _pg.points().get(_col).get(_row);
        }

    }

    public static <T extends Iterable<GridPoint>> PointList getPointsByWeight(double _floor, double _ceil, T _points) {

        // Returns a Point_List of all points in a Point_Grid within a given weight threshold [_floor, _ceil)
        // Where:
        // _floor -> threshold floor (inclusive)
        // _ceil -> threshold ceiling (exclusive)

        PointList result = new PointList();
        for (GridPoint currPoint : _points) {
            if (currPoint.weight() >= _floor && currPoint.weight() < _ceil) {
                result.add(currPoint);
            }
        }
        return result;

    }

    public static GridPoint getListPoint(int _index, PointList _pl) {

        // Fetches a Grid_Point from a Point_List, returning NULL if point doesn't exist.

        if (!_pl.isEmpty()) return _pl.points.get(_index);
        else return null;

    }

    public static PointList getListPointRange(int _start, int _end, PointList _pl) {

        // Returns a new Point_List with points in a given range [_start, _end);

        if (!_pl.isEmpty() && (_start < _end) && _start >= 0 && _end < _pl.points.size()) {
            PointList result = new PointList(_end - _start);

            for (int i = _start; i < _end; i++) {
                result.add(_pl.points.get(i));
            }

            return result;

        }

        return null;

    }

    public static PointList getGridColumn(int _index, PointGrid _pg) {

        // Fetches a column of POINTs from a POINT_GRID
        // Where:
        // _pg -> POINT_GRID to fetch from (POINT_GRID)
        // _index -> column to grab

        return new PointList(_pg.points().get(_index));

    }

    public static PointList getGridRow(int _index, PointGrid _pg) {

        // Fetches a row of POINTs from a POINT_GRID
        // Where:
        // _pg -> POINT_GRID to fetch from (POINT_GRID)
        // _index -> row to grab

        PointList result = new PointList(_pg.yPoints());

        for (int i = 0; i < _pg.points().size(); i++) {
            result.add(_pg.points().get(i).get(_index));
        }

        return result;
    }

    public static GridPoint getGridPointMirror(int _col, int _row, PointGrid _pg){

        // Fetches a vertically and horizontally symmetrical POINT based on a source POINT and POINT_GRID
        // Where:
        // _col -> column index of source point
        // _row -> row index of source point
        // _pg -> POINT_GRID to fetch from

        int grid_width = _pg.xPoints() - 1;
        int grid_height = _pg.yPoints() - 1;
        int opposite_x = grid_width - _col;
        int opposite_y = grid_height - _row;

        return getGridPoint(opposite_x, opposite_y, _pg);

    }

    public static GridPoint getGridPointMirrorY(int _col, int _row, PointGrid _pg) {

        // Fetches a vertically symmetrical POINT based on a source POINT and POINT_GRID
        // Where:
        // _col -> column index of source point
        // _row -> row index of source point
        // _pg -> POINT_GRID to fetch from

        int grid_height = _pg.yPoints() - 1;
        int opposite_y = grid_height - _row;

        return getGridPoint(_col, opposite_y, _pg);

    }

    public static GridPoint getGridPointMirrorX(int _col, int _row, PointGrid _pg) {

        // Fetches a horizontally symmetrical POINT based on a source POINT and POINT_GRID
        // Where:
        // _col -> column index of source point
        // _row -> row index of source point
        // _pg -> POINT_GRID to fetch from

        int grid_width = _pg.xPoints() - 1;
        int opposite_x = grid_width - _col;

        return getGridPoint(opposite_x, _row, _pg);

    }

    public static PointList getGridLine(int _col0, int _row0, int _col1, int _row1, PointGrid _pg) {

        // fetches points on grid according to line given by (_col0, _row0), (_col1, _row1)
        // uses modified rasterizing algorithm by Alois Zingl (http://members.chello.at/~easyfilter/Bresenham.pdf)
        // Where:
        // _col0, _row0 -> start point of line (by col and row index of POINT_GRID)
        // _col1, _row1 -> end point of line (by col and row index of POINT_GRID)
        // _pg -> POINT_GRID to sample from

        PointList result = new PointList();
        int dx = Math.abs(_col1 - _col0);
        int dy = -Math.abs(_row1 - _row0);
        int sx = _col0 < _col1 ? 1 : -1;
        int sy = _row0 < _row1 ? 1 : -1;
        int err = dx + dy, e2;

        while (true) {
            if (Helpers.checkBounds(_col0, _row0, _col1, _row1, _pg)) { // Make sure we're not out of bounds.
                result.add(getGridPoint(_col0, _row0, _pg));
            }
            e2 = 2 * err;
            if (e2 >= dy) {
                if (_col0 == _col1) break;
                err += dy; _col0 += sx;
            }
            if (e2 <= dx) {
                if (_row0 == _row1) break;
                err += dx; _row0 += sy;
            }
        }

        return result;

    }

    public static PointList getGridLineNoOp(int _col0, int _row0, int _col1, int _row1, PointGrid _pg) {

        // fetches points on grid according to line given by (_col0, _row0), (_col1, _row1)
        // instead of an optimized algorithm, uses a non-optimized slope-intercept based method.
        // Where:
        // _col0, _row0 -> start point of line (by col and row index of POINT_GRID)
        // _col1, _row1 -> end point of line (by col and row index of POINT_GRID)
        // _pg -> POINT_GRID to sample from

        PointList result = new PointList();

        boolean dir = _col0 < _col1;
        int start_x = dir ? _col0 : _col1;
        int start_y = dir ? _row0 : _row1;
        int end_x = dir ? _col1 : _col0;
        int end_y = dir ? _row1 : _row0;
        float slope = (float)(end_y - start_y) / (float)(end_x - start_x);
        float offset = start_y - slope*start_x;
        float y;

        while (start_x++ != end_x) {
            y = slope*start_x + offset;
            result.add(_pg.points().get(start_x).get((int)y));
        }

        return result;

    }

    public static PointList getGridCircle(int _col, int _row, int _rad, PointGrid _pg) {

        // fetches points on grid according to circle with center (_col, _row) and radius (_rad)
        // uses modified rasterizing algorithm by Alois Zingl (http://members.chello.at/~easyfilter/Bresenham.pdf)
        // Where:
        // _col, _row -> center of circle
        // _rad -> radius of circle
        // _pg -> POINT_GRID to sample from

        PointList result = new PointList();

        int x = -_rad;
        int y = 0;
        int err = 2-2*_rad;

        while (x < 0) {
            if (_col-x < _pg.xPoints() && _col-x > -1 && _row+y < _pg.yPoints() && _row+y > -1) { // Same as with line (out of bounds checks).
                result.add(getGridPoint(_col-x, _row+y, _pg));
            }
            if (_col-y > -1 && _col-y < _pg.xPoints() && _row-x < _pg.yPoints() && _row-x > -1) {
                result.add(getGridPoint(_col-y, _row-x, _pg));
            }
            if (_col+x > -1 && _col+x < _pg.xPoints() && _row-y > -1 && _row-y < _pg.yPoints()) {
                result.add(getGridPoint(_col+x, _row-y, _pg));
            }
            if (_col+y < _pg.xPoints() && _col+y > -1 && _row+x > -1 && _row+x < _pg.yPoints()) {
                result.add(getGridPoint(_col+y, _row+x, _pg));
            }
            _rad = err;
            if (_rad <= y) {
                y += 1;
                err += y*2+1;
            }
            if (_rad > x || err > y) {
                x += 1;
                err += x*2+1;
            }
        }

        return result;

    }

    public static PointList getGridCircleFill(int _col, int _row, int _rad, PointGrid _pg) {

        // fetches points on grid according to circle with center (_col, _row) and radius (_rad).
        // fetches all points inside said circle as well.
        // uses modified rasterizing algorithm for outline, by Alois Zingl (http://members.chello.at/~easyfilter/Bresenham.pdf)
        // Where:
        // _col, _row -> center of circle
        // _rad -> radius of circle
        // _pg -> POINT_GRID to sample from

        PointList result = getGridCircle(_col, _row, _rad, _pg);

        result.addAll(gridFillBounds(_col, _row, result, _pg));

        return result;

    }

    public static PointList getGridPolyline(PointList _pl, boolean _closed, PointGrid _pg) {

        // fetches points on grid according to coordinates of polygon passed in as Point List _pl
        // uses modified rasterizing algorithm by Alois Zingl (http://members.chello.at/~easyfilter/Bresenham.pdf)
        // Where:
        // _pl -> Point_List of endpoints to build Polygon outline
        // _pg -> POINT_GRID to sample from

        if (_pl.isEmpty()) return null;

        PointList result = new PointList();
        Iterator<GridPoint> iter = _pl.iterator();
        GridPoint firstPoint = iter.next();
        GridPoint currPoint = firstPoint;
        GridPoint nextPoint;

        while(iter.hasNext()) {
            nextPoint = iter.next();
            result.addAll(getGridLine(currPoint.gridIndexX(), currPoint.gridIndexY(), nextPoint.gridIndexX(), nextPoint.gridIndexY(), _pg));
            currPoint = nextPoint;
        }

        if (_closed) result.addAll(getGridLine(currPoint.gridIndexX(), currPoint.gridIndexY(), firstPoint.gridIndexX(), firstPoint.gridIndexY(), _pg));

        return result;

    }

    public static PointList getGridPolylineFill(PointList _pl, PointGrid _pg) {

        // fetches all points within and including polyline _pl in a given grid.
        // if open, the polyline is closed before processing it.
        // Where:
        // _pl -> polyline to sample
        // _pg -> point grid to sample

       PointList result = getGridPolyline(_pl, true, _pg);
       Tuple2<Integer, Integer> centroid = polygonCentroid(_pl);
       assert result != null;
       result.addAll(gridFillBounds(centroid.a, centroid.b, result, _pg));

       return result;

    }

    public static PointList getGridPattern(int _col, int _row, List<Integer> _dlist, int _reps, boolean _overflow, PointGrid _pg) {

        // fetches points according to a list of directions (explained below) for a certain number of iterations
        // Where:
        // _col, _row -> origin of pattern
        // _dlist -> list of steps to take: 0: TOP, 1: TOP-RIGHT, 2: RIGHT, 3: BOTTOM-RIGHT, 4: BOTTOM, etc.
        // _reps -> number of steps to take (from 0, where none are taken, to ...)
        // _overflow -> allow for pattern to wrap around edges (if a similar point is found, pattern will break regardless of reps)
        // _pg -> point grid to sample from

        PointList result = new PointList(); // Consider just checking ArrayList for duplicates

        GridPoint currentPoint = new GridPoint(getGridPoint(_col, _row, _pg));
        int step = 0;
        int pointer = 0;
        int mod = _dlist.size();

        while (step < _reps) {
            if (result.get(0) == currentPoint || currentPoint == null) break;
            result.add(new GridPoint(currentPoint));

            pointer = step % mod;
            //print(_dlist.get(pointer)); -> debug

            switch (_dlist.get(pointer)) {
                case 0 -> currentPoint = _overflow ? getGridPoint(currentPoint.gridIndexX(), currentPoint.gridIndexY() - 1, _pg) : getGridPointSafe(currentPoint.gridIndexX(), currentPoint.gridIndexY() - 1, _pg);
                case 1 -> currentPoint = _overflow ? getGridPoint(currentPoint.gridIndexX() + 1, currentPoint.gridIndexY() - 1, _pg) : getGridPointSafe(currentPoint.gridIndexX() + 1, currentPoint.gridIndexY() - 1, _pg);
                case 2 -> currentPoint = _overflow ? getGridPoint(currentPoint.gridIndexX() + 1, currentPoint.gridIndexY(), _pg) : getGridPointSafe(currentPoint.gridIndexX() + 1, currentPoint.gridIndexY(), _pg);
                case 3 -> currentPoint = _overflow ? getGridPoint(currentPoint.gridIndexX() + 1, currentPoint.gridIndexY() + 1, _pg) : getGridPointSafe(currentPoint.gridIndexX() + 1, currentPoint.gridIndexY() + 1, _pg);
                case 4 -> currentPoint = _overflow ? getGridPoint(currentPoint.gridIndexX(), currentPoint.gridIndexY() + 1, _pg) : getGridPointSafe(currentPoint.gridIndexX(), currentPoint.gridIndexY() + 1, _pg);
                case 5 -> currentPoint = _overflow ? getGridPoint(currentPoint.gridIndexX() - 1, currentPoint.gridIndexY() + 1, _pg) : getGridPointSafe(currentPoint.gridIndexX() - 1, currentPoint.gridIndexY() + 1, _pg);
                case 6 -> currentPoint = _overflow ? getGridPoint(currentPoint.gridIndexX() - 1, currentPoint.gridIndexY(), _pg) : getGridPointSafe(currentPoint.gridIndexX() - 1, currentPoint.gridIndexY(), _pg);
                case 7 -> currentPoint = _overflow ? getGridPoint(currentPoint.gridIndexX() - 1, currentPoint.gridIndexY() - 1, _pg) : getGridPointSafe(currentPoint.gridIndexX() - 1, currentPoint.gridIndexY() - 1, _pg);
            }

            step += 1;

        }

        return result;

    }

    public static PointList getGridEvery(int _x, int _y, PointGrid _pg) {

        // fetches points in a grid, by sampling every _x column and _y row.
        // Where:
        // _x -> how many columns to skip
        // _y -> how many rows to skip
        // _pg -> point grid to sample

        PointList result = new PointList();

        for (int x = 0; x < _pg.xPoints(); x += _x) {
            for (int y = 0; y < _pg.yPoints(); y += _y) {
                result.add(_pg.points().get(x).get(y));
            }
        }

        return result;

    }

    public static PointList getListEveryOther(int _x, PointList _pl) {

        // fetches points in a point_list, skipping _x points per sample.
        // Where:
        // _x -> how many points to skip per sample.
        // _pl -> point list to sample

        PointList result = new PointList();

        for (int x = 0; x < _pl.size(); x += _x) {
            result.add(_pl.get(x));
        }

        return result;

    }

    public static PointList getGridRegion(int _x0, int _y0, int _x1, int _y1, PointGrid _pg) {

        // fetches points within a given Point_Grid region
        // Where:
        // _x0, _y0 -> top left corner of region (inclusive)
        // _x1, _y1 -> bottom right corner of region (inclusive)

        PointList result = new PointList();

        for (int x = _x0; x <= _x1; x++) {
            for (int y = _y0; y <= _y1; y++) {
                result.add(_pg.getPoint(x, y));
            }
        }

        return result;

    }

    //** ==================== PRIVATE HELPERS ====================== **//

    private static Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> getListMinMaxCoords(PointList _pl) {

        // finds the min/max gX and min/max gY coordinates in a Point_List.
        // returns ((x-min, x-max), (y-min, y-max))
        // Where:
        // _pl -> point list to sample

       Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>> result =
               new Tuple2<Tuple2<Integer, Integer>, Tuple2<Integer, Integer>>
                       (new Tuple2<Integer, Integer>(Integer.MAX_VALUE, 0), new Tuple2<Integer, Integer>(Integer.MAX_VALUE, 0));

       for (GridPoint currPoint : _pl.points) {
           if (currPoint.gridIndexX() < result.a.a) result.a.a = currPoint.gridIndexX();
           if (currPoint.gridIndexX() > result.a.b) result.a.b = currPoint.gridIndexX();
           if (currPoint.gridIndexY() < result.b.a) result.b.a = currPoint.gridIndexY();
           if (currPoint.gridIndexY() > result.b.b) result.b.b = currPoint.gridIndexX();
       }

       return result;
    }

    private static boolean pointInList(int _col, int _row, PointList _pl) {

        // checks whether a certain point exists in a list based on given x, y grid coordinates.
        // Where:
        // _col -> col index of desired point
        // _row -> row index of desired point
        // _pl -> point list to sample
        // TODO: implement sortx, sorty, sortglobal for Point_List (this is too naive for large lists)

        boolean found = false;

        for (GridPoint currPoint : _pl.points) {
            if (currPoint.gridIndexX() == _col && currPoint.gridIndexY() == _row) {
                found = true;
                break;
            }
        }

        return found;

    }

    private static void fillUtil(int _col, int _row, PointList _outline, PointGrid _pg, PointList _result) {

        // Recurs over points within _pl (must be closed) and adds them to _result
        // Where:
        // _col -> centerpoint x of fill operation
        // _row -> centerpoint y of fill operation
        // _outline -> Point_List representing closed bounds - points are added to this Point_List
        // _pg -> Point_Grid to draw points from
        // _result -> Point_List to add points to

        if (!Helpers.checkBounds(_col, _row, _pg)) return;
        if (pointInList(_col, _row, _outline)) return;
        if (pointInList(_col, _row, _result)) return;

        _result.add(getGridPoint(_col, _row, _pg));

        fillUtil(_col + 1, _row, _outline, _pg, _result);
        fillUtil(_col - 1, _row, _outline, _pg, _result);
        fillUtil(_col, _row + 1, _outline, _pg, _result);
        fillUtil(_col, _row - 1, _outline, _pg, _result);

    }

    private static PointList gridFillBounds(int _col, int _row, PointList _outline, PointGrid _pg) {

        // Returns points within the bounds of a Point_List (i.e. circle) (must be closed bounds)
        // Where:
        // _col -> centerpoint x of fill operation
        // _row -> centerpoint y of fill operation
        // _outline -> Point_List representing closed bounds - points are added to this Point_List
        // _pg -> Point_Grid to draw points from

        PointList result = new PointList();
        fillUtil(_col, _row, _outline, _pg, result);
        return result;

    }

    private static Tuple2<Integer, Integer> polygonCentroid(PointList _pl) {

       // Returns the centroid of a non-self intersecting Polygon (VERTICES MUST BE IN ORDER)
       // Where:
       // _pl -> polyline to sample

        Tuple2<Integer, Integer> result = new Tuple2<Integer, Integer>(0, 0);

        int n = _pl.size();
        double signedArea = 0;

        // For all vertices
        for (int i = 0; i < n; i++)
        {
            int x0 = _pl.get(i).gridIndexX(), y0 = _pl.get(i).gridIndexY();
            int x1 = _pl.get((i + 1) % n).gridIndexX(), y1 = _pl.get((i + 1) % n).gridIndexY();

            // Calculate value of A
            // using shoelace formula
            int A = (x0 * y1) - (x1 * y0);
            signedArea += A;

            // Calculating coordinates of
            // centroid of polygon
            result.a += (x0 + x1) * A;
            result.b += (y0 + y1) * A;
        }

        signedArea *= 0.5;
        result.a = (int)((result.a) / (6 * signedArea));
        result.b = (int)((result.b) / (6 * signedArea));

        Core.processing.print(result.b, " ", result.a, "\n");

        return result;

    }

}
