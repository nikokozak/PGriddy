import java.util.ArrayList;

public class Point_Grid {
// a POINT_GRID is a data structure
// a POINT_GRID contains a 2D collection of POINTs
// NOTE: 2D ArrayList is used to store points as opposed to Processing Core's flat array preference: performance difference was negligible vs code clarity gained.
// Where:
// x -> Number of POINTs in X axis (INT)
// y -> Number of POINTs in Y axis (INT)
// c -> Global center of GRID (POINT)
// sX -> Spacing between POINTs in X axis (INT)
// sY -> Spacing between POINTs in Y axis (INT)

    public final int x, y, sX, sY;
    public int xOrigin, yOrigin; // Define the lowest X and Y coordinates for the grid system.
    public final Point c;
    public ArrayList<ArrayList<Grid_Point>> points;

    public Point_Grid(int _x, int _y, Point _c, int _sX, int _sY) {
        x = _x;
        y = _y;
        c = _c;
        sX = _sX;
        sY = _sY;

        xOrigin = (int)_c.x - ((_x/2)*_sX);
        yOrigin = (int)_c.y - ((_y/2)*_sY);

        points = new ArrayList<ArrayList<Grid_Point>>();

        for (int i = 0; i < _x; i += 1) {
            int xPos = xOrigin + (i * _sX);
            points.add(new ArrayList<Grid_Point>());
            for (int j = 0; j < _y; j += 1) {
                int yPos = yOrigin + (j * _sY);
                points.get(i).add(new Grid_Point(xPos, yPos, i, j));
            }
        }
    }

    public Point_Grid (Point_Grid _pg) {
        x = _pg.x; y = _pg.y;
        c = new Point(_pg.c);
        sX = _pg.sX; sY = _pg.sY;

        xOrigin = _pg.xOrigin;
        yOrigin = _pg.yOrigin;

        points = Helpers.clonePoints(_pg);
    }

    public Point_Grid (Point_Grid _pg, ArrayList<ArrayList<Grid_Point>> _al) {
        c = new Point(_pg.c);
        sX = _pg.sX; sY = _pg.sY;

        xOrigin = _pg.xOrigin;
        yOrigin = _pg.yOrigin;

        points = new ArrayList<ArrayList<Grid_Point>>(_al);

        x = points.size(); y = points.get(0).size();
    }

    public Point_Grid (Point_Grid _pg, boolean _zeroWeight) { // Token override to create grid with zero weights.
        x = _pg.x; y = _pg.y;
        c = new Point(_pg.c);
        sX = _pg.sX; sY = _pg.sY;

        xOrigin = _pg.xOrigin;
        yOrigin = _pg.yOrigin;

        points = Helpers.clonePoints(_pg);

        for (int i = 0; i < x; i += 1) {
            for (int j = 0; j < y; j += 1) {
                points.get(i).get(j).weight = 0;
            }
        }
    }
}