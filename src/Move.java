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

    public static Point_Grid grid_reset( Point_Grid _pg) {

        // Resets all Points to their original positions.

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                currPoint.x = currPoint.oX;
                currPoint.y = currPoint.oY;
            }
        }

        return _pg;
    }

}
