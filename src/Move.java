import java.util.ArrayList;

public class Move {

    public static Point_Grid grid(int _x, int _y, Point_Grid _pg) {
        // Adds x and y quantities to Point positions.

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                currPoint.y += _y;
                currPoint.x += _x;
            }
        }

        return _pg;

    }

    public static Point_Grid grid(int _x, int _y, Selection _s, Point_Grid _pg) {

        // Adds x and y quantities to Point positions within a given selection.

        Grid_Point currPoint;

        for (int x = _s.startCol; x <= _s.endCol; x++) {
            for (int y = _s.startRow; y <= _s.endRow; y++) {
                currPoint = _pg.points.get(x).get(y);
                currPoint.x += _x;
                currPoint.y += _y;
            }
        }

        return _pg;

    }

    public static Point_List list(int _x, int _y, Point_List _pl) {
        // Adds x and y quantities to Point positions.

        for (Grid_Point currPoint : _pl.points) {
            currPoint.x += _x;
            currPoint.y += _y;
        }

        return _pl;
    }

    public static Point_Grid grid_mult(int _x, int _y, Point_Grid _pg) {

        // Multiplies Point positions by _x, _y.

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                currPoint.y *= _y;
                currPoint.x *= _x;
            }
        }

        return _pg;

    }

    public static Point_Grid grid_mult(int _x, int _y, Selection _s, Point_Grid _pg) {

        // Multiplies x and y positions by _x and _y within a given selection.

        Grid_Point currPoint;

        for (int x = _s.startCol; x <= _s.endCol; x++) {
            for (int y = _s.startRow; y <= _s.endRow; y++) {
                currPoint = _pg.points.get(x).get(y);
                currPoint.x *= _x;
                currPoint.y *= _y;
            }
        }

        return _pg;

    }

    public static Point_List list_mult(int _x, int _y, Point_List _pl) {

        // Multiplies Point positions by _x, _y.

        for (Grid_Point currPoint : _pl.points) {
            currPoint.x *= _x;
            currPoint.y *= _y;
        }

        return _pl;

    }

    public static Point_Grid grid_to(int _x, int _y, Point_Grid _pg) {

        // Moves the entire Grid to a new x, y center.
        // RESETS ALL PAREMETERS PERTAINING TO ORIGIN, ETC.

        Point newCenter = new Point(_x, _y);
        _pg.c = newCenter;

        _pg.xOrigin = (int)newCenter.x - ((_pg.x/2)*_pg.sX);
        _pg.yOrigin = (int)newCenter.y - ((_pg.y/2)*_pg.sY);

        for (int i = 0; i < _pg.x; i += 1) {
            int xPos = _pg.xOrigin + (i * _pg.sX);
            for (int j = 0; j < _pg.y; j += 1) {
                int yPos = _pg.yOrigin + (j * _pg.sY);
                _pg.points.get(i).get(j).x = xPos;
                _pg.points.get(i).get(j).y = yPos;
            }
        }

        return _pg;
    }

    public static Point_Grid grid_to(int _x, int _y, Selection _s, Point_Grid _pg) {

        // Moves a grid selection to a new x, y center. Translation is based on selection center.
        // DOES NOT AFFECT GRID CENTER ATTRIBUTE.

        int x_translate = _x - _s.center_x();
        int y_translate = _y - _s.center_y();

        for (int x = _s.startCol; x < _s.endCol; x++)  {
            for (int y = _s.startRow; y < _s.endRow; y++) {
                _pg.points.get(x).get(y).x += x_translate;
                _pg.points.get(x).get(y).y += y_translate;
            }
        }

        return _pg;
    }

    public static Point_List list_to(int _x, int _y, Point_List _pl) {

        // Moves the entire Point List to a new x, y position.
        // Translation is based on the first point in the list.

        float x_translation = _pl.get(0).x - _x;
        float y_translation = _pl.get(0).y - _y;

        for (Grid_Point currPoint : _pl.points) {
            currPoint.x += x_translation;
            currPoint.y += y_translation;
        }

        return _pl;

    }

    public static Point_Grid grid_reset(Point_Grid _pg) {

        // Resets all Points to their original positions.

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                currPoint.x = currPoint.oX;
                currPoint.y = currPoint.oY;
            }
        }

        return _pg;
    }

    public static Point_Grid grid_reset(Selection _s, Point_Grid _pg) {

        // Resets all Grid_Points within a selection to their original positions.

        Grid_Point currPoint;

        for (int x = _s.startCol; x < _s.endCol; x++) {
            for (int y = _s.startRow; y < _s.endRow; y++) {
                currPoint = _pg.points.get(x).get(y);
                currPoint.x = currPoint.oX;
                currPoint.y = currPoint.oY;
            }
        }

        return _pg;

    }

    public static Point_List list_reset(Point_List _pl) {

       // Resets all Points to their original positions.

       for (Grid_Point currPoint : _pl.points) {
           currPoint.x = currPoint.oX;
           currPoint.y = currPoint.oY;
       }

       return _pl;

    }

}
