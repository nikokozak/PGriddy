import static processing.core.PApplet.*;

public class Point {
// POINT ( x: FLOAT, y: FLOAT, gridIndexX: INT, gridIndexY: INT )
// interp: a POINT represents a point in cartesian coords (as distinct from a GRID_POINT, which has a grid index position).
// x -> window coordinate X of point.
// y -> window coordinate Y of point.
// weight -> associated weight of point.

    public float oX, oY, x, y;
    public int col;
    public double weight;

    public Point (float _x, float _y) {
        x = _x; y = _y;
        oX = _x; oY = _y;
        weight = 1;
        col = Core.processing.color(255);
    }
    public Point (Point _p) {
        x = _p.x; y = _p.y;
        oX = _p.oX; y = _p.oY;
        weight = _p.weight;
        col = _p.col;
    }
    public Point (Grid_Point _p) {
        x = _p.x; y = _p.y;
        oX = _p.oX; oY = _p.oY;
        weight = _p.weight;
        col = _p.col;
    }

}