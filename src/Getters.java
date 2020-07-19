import java.util.*;

public class Getters {

    public static Grid_Point get_grid_point(int _col, int _row, Point_Grid _pg) {

        // Fetches a POINT from a POINT_GRID
        // Where:
        // _col -> column index of desired point
        // _row -> row index of desired point

        _col = Math.floorMod(_col, _pg.x);
        _row = Math.floorMod(_row, _pg.y);

        return _pg.points.get(_col).get(_row);
    }

    public static Grid_Point get_grid_point_safe(int _col, int _row, Point_Grid _pg) {

        // Fetches a POINT from a POINT_GRID, throwing an exception if point is out of bounds.
        // Where:
        // _col -> column index of desired point
        // _row -> row index of desired point

        if (_col > _pg.x - 1 || _row > _pg.y - 1 || _col < 0 || _row < 0) {
            throw new java.lang.RuntimeException("Unsafe Point");
        } else {
            return _pg.points.get(_col).get(_row);
        }

    }

    public static Point_List get_grid_points_by_weight(int _floor, int _ceil, Point_Grid _pg) {

        // Returns a Point_List of all points in a Point_Grid within a given weight threshold [_floor, _ceil)
        // Where:
        // _floor -> threshold floor (inclusive)
        // _ceil -> threshold ceiling (exclusive)

        Point_List result = new Point_List();

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                if (currPoint.weight >= _floor && currPoint.weight < _ceil) {
                    result.add(currPoint);
                }
            }
        }

        return result;

    }

    public static Grid_Point get_list_point(int _index, Point_List _pl) {

        // Fetches a Grid_Point from a Point_List, returning NULL if point doesn't exist.

        if (!_pl.is_empty()) return _pl.points.get(_index);
        else return null;

    }

    public static Point_List get_list_point_range(int _start, int _end, Point_List _pl) {

        // Returns a new Point_List with points in a given range [_start, _end);

        if (!_pl.is_empty() && (_start < _end) && _start >= 0 && _end < _pl.points.size()) {
            Point_List result = new Point_List(_end - _start);

            for (int i = _start; i < _end; i++) {
                result.add(_pl.points.get(i));
            }

            return result;

        }

        return null;

    }

    public static Point_List get_grid_column(int _index, Point_Grid _pg) {

        // Fetches a column of POINTs from a POINT_GRID
        // Where:
        // _pg -> POINT_GRID to fetch from (POINT_GRID)
        // _index -> column to grab

        Point_List result = new Point_List(_pg.points.get(_index));

        return result;

    }

    public static Point_List get_grid_row(int _index, Point_Grid _pg) {

        // Fetches a row of POINTs from a POINT_GRID
        // Where:
        // _pg -> POINT_GRID to fetch from (POINT_GRID)
        // _index -> row to grab

        Point_List result = new Point_List(_pg.y);

        for (int i = 0; i < _pg.points.size(); i++) {
            result.add(_pg.points.get(i).get(_index));
        }

        return result;
    }

    public static Grid_Point get_grid_point_mirror(int _col, int _row, Point_Grid _pg){

        // Fetches a vertically and horizontally symmetrical POINT based on a source POINT and POINT_GRID
        // Where:
        // _col -> column index of source point
        // _row -> row index of source point
        // _pg -> POINT_GRID to fetch from

        int grid_width = _pg.x - 1;
        int grid_height = _pg.y - 1;
        int opposite_x = grid_width - _col;
        int opposite_y = grid_height - _row;

        return get_grid_point(opposite_x, opposite_y, _pg);

    }

    public static Grid_Point get_grid_point_mirror_y(int _col, int _row, Point_Grid _pg) {

        // Fetches a vertically symmetrical POINT based on a source POINT and POINT_GRID
        // Where:
        // _col -> column index of source point
        // _row -> row index of source point
        // _pg -> POINT_GRID to fetch from

        int grid_height = _pg.y - 1;
        int opposite_y = grid_height - _row;

        return get_grid_point(_col, opposite_y, _pg);

    }

    public static Grid_Point get_grid_point_mirror_x(int _col, int _row, Point_Grid _pg) {

        // Fetches a horizontally symmetrical POINT based on a source POINT and POINT_GRID
        // Where:
        // _col -> column index of source point
        // _row -> row index of source point
        // _pg -> POINT_GRID to fetch from

        int grid_width = _pg.x - 1;
        int opposite_x = grid_width - _col;

        return get_grid_point(opposite_x, _row, _pg);

    }

    public static Point_List get_line(int _col0, int _row0, int _col1, int _row1, Point_Grid _pg) {

        // fetches points on grid according to line given by (_col0, _row0), (_col1, _row1)
        // uses modified rasterizing algorithm by Alois Zingl (http://members.chello.at/~easyfilter/Bresenham.pdf)
        // Where:
        // _col0, _row0 -> start point of line (by col and row index of POINT_GRID)
        // _col1, _row1 -> end point of line (by col and row index of POINT_GRID)
        // _pg -> POINT_GRID to sample from

        Point_List result = new Point_List();
        int dx = Math.abs(_col1 - _col0);
        int dy = -Math.abs(_row1 - _row0);
        int sx = _col0 < _col1 ? 1 : -1;
        int sy = _row0 < _row1 ? 1 : -1;
        int err = dx + dy, e2;

        while (true) {
            if (Helpers.checkBounds(_col0, _row0, _col1, _row1, _pg)) { // Make sure we're not out of bounds.
                result.add(get_grid_point(_col0, _row0, _pg));
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

    public static Point_List get_line_no_op(int _col0, int _row0, int _col1, int _row1, Point_Grid _pg) {

        // fetches points on grid according to line given by (_col0, _row0), (_col1, _row1)
        // instead of an optimized algorithm, uses a non-optimized slope-intercept based method.
        // Where:
        // _col0, _row0 -> start point of line (by col and row index of POINT_GRID)
        // _col1, _row1 -> end point of line (by col and row index of POINT_GRID)
        // _pg -> POINT_GRID to sample from

        Point_List result = new Point_List();

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
            result.add(_pg.points.get(start_x).get((int)y));
        }

        return result;

    }

    public static Point_List get_circle(int _col, int _row, int _rad, Point_Grid _pg) {

        // fetches points on grid according to circle with center (_col, _row) and radius (_rad)
        // uses modified rasterizing algorithm by Alois Zingl (http://members.chello.at/~easyfilter/Bresenham.pdf)
        // Where:
        // _col, _row -> center of circle
        // _rad -> radius of circle
        // _pg -> POINT_GRID to sample from

        Point_List result = new Point_List();

        int x = -_rad;
        int y = 0;
        int err = 2-2*_rad;

        while (x < 0) {
            if (_col-x < _pg.x && _col-x > -1 && _row+y < _pg.y && _row+y > -1) { // Same as with line (out of bounds checks).
                result.add(get_grid_point(_col-x, _row+y, _pg));
            }
            if (_col-y > -1 && _col-y < _pg.x && _row-x < _pg.y && _row-x > -1) {
                result.add(get_grid_point(_col-y, _row-x, _pg));
            }
            if (_col+x > -1 && _col+x < _pg.x && _row-y > -1 && _row-y < _pg.y) {
                result.add(get_grid_point(_col+x, _row-y, _pg));
            }
            if (_col+y < _pg.x && _col+y > -1 && _row+x > -1 && _row+x < _pg.y) {
                result.add(get_grid_point(_col+y, _row+x, _pg));
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

    public static Point_List get_polyline(Point_List _pl, boolean _closed, Point_Grid _pg) {

        // fetches points on grid according to coordinates of polygon passed in as Point List _pl
        // uses modified rasterizing algorithm by Alois Zingl (http://members.chello.at/~easyfilter/Bresenham.pdf)
        // Where:
        // _pl -> Point_List of endpoints to build Polygon outline
        // _pg -> POINT_GRID to sample from

        if (_pl.is_empty()) return null;

        Point_List result = new Point_List();
        Iterator<Grid_Point> iter = _pl.iterator();
        Grid_Point firstPoint = iter.next();
        Grid_Point currPoint = firstPoint;
        Grid_Point nextPoint;

        while(iter.hasNext()) {
            nextPoint = iter.next();
            result.add_all(get_line(currPoint.gX, currPoint.gY, nextPoint.gX, nextPoint.gY, _pg));
            currPoint = nextPoint;
        }

        if (_closed) result.add_all(get_line(currPoint.gX, currPoint.gY, firstPoint.gX, firstPoint.gY, _pg));

        return result;

    }

    public static Point_List get_pattern(int _col, int _row, List<Integer> _dlist, int _reps, boolean _overflow, Point_Grid _pg) {

        // fetches points according to a list of directions (explained below) for a certain number of iterations
        // Where:
        // _col, _row -> origin of pattern
        // _dlist -> list of steps to take: 0: TOP, 1: TOP-RIGHT, 2: RIGHT, 3: BOTTOM-RIGHT, 4: BOTTOM, etc.
        // _reps -> number of steps to take (from 0, where none are taken, to ...)
        // _overflow -> allow for pattern to wrap around edges (if a similar point is found, pattern will break regardless of reps)
        // _pg -> point grid to sample from

        Point_List result = new Point_List(); // Consider just checking ArrayList for duplicates

        Grid_Point currentPoint = new Grid_Point(get_grid_point(_col, _row, _pg));
        int step = 0;
        int pointer = 0;
        int mod = _dlist.size();

        while (step < _reps) {
            if (result.get(0) == currentPoint || currentPoint == null) break;
            result.add(new Grid_Point(currentPoint));

            pointer = step % mod;
            //print(_dlist.get(pointer)); -> debug

            switch (_dlist.get(pointer)) {
                case 0 -> currentPoint = _overflow ? get_grid_point(currentPoint.gX, currentPoint.gY - 1, _pg) : get_grid_point_safe(currentPoint.gX, currentPoint.gY - 1, _pg);
                case 1 -> currentPoint = _overflow ? get_grid_point(currentPoint.gX + 1, currentPoint.gY - 1, _pg) : get_grid_point_safe(currentPoint.gX + 1, currentPoint.gY - 1, _pg);
                case 2 -> currentPoint = _overflow ? get_grid_point(currentPoint.gX + 1, currentPoint.gY, _pg) : get_grid_point_safe(currentPoint.gX + 1, currentPoint.gY, _pg);
                case 3 -> currentPoint = _overflow ? get_grid_point(currentPoint.gX + 1, currentPoint.gY + 1, _pg) : get_grid_point_safe(currentPoint.gX + 1, currentPoint.gY + 1, _pg);
                case 4 -> currentPoint = _overflow ? get_grid_point(currentPoint.gX, currentPoint.gY + 1, _pg) : get_grid_point_safe(currentPoint.gX, currentPoint.gY + 1, _pg);
                case 5 -> currentPoint = _overflow ? get_grid_point(currentPoint.gX - 1, currentPoint.gY + 1, _pg) : get_grid_point_safe(currentPoint.gX - 1, currentPoint.gY + 1, _pg);
                case 6 -> currentPoint = _overflow ? get_grid_point(currentPoint.gX - 1, currentPoint.gY, _pg) : get_grid_point_safe(currentPoint.gX - 1, currentPoint.gY, _pg);
                case 7 -> currentPoint = _overflow ? get_grid_point(currentPoint.gX - 1, currentPoint.gY - 1, _pg) : get_grid_point_safe(currentPoint.gX - 1, currentPoint.gY - 1, _pg);
            }

            step += 1;

        }

        return result;

    }

}
