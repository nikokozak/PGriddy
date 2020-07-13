
public class Grid_Point extends Point {
// a GRID_POINT is a data structure
// GRID_POINT ( x: INT, y: INT, gridIndexX: INT, gridIndexY: INT )
// interpretation: a GRID_POINT represents a POINT acquired from a (belonging to) a GRID
// x -> window coordinate X of point.
// y -> window coordinate Y of point.
// gridIndexX -> if created as part of a POINT_GRID, COL index of said POINT_GRID
// gridIndexY -> if created as part of a POINT_GRID, ROW index of said POINT_GRID
// weight -> associated weight of point.

    public final int gridIndexX, gridIndexY;
    public double weight;

    public Grid_Point (int _x, int _y, int _ix, int _iy) {
        super(_x, _y);
        gridIndexX = _ix;
        gridIndexY = _iy;
        weight = 1;
    }
    public Grid_Point (int _x, int _y, int _ix, int _iy, double _weight) {
        super(_x, _y);
        gridIndexX = _ix;
        gridIndexY = _iy;
        weight = _weight;
    }
    public Grid_Point (Grid_Point _p) {
        super(_p.x, _p.y);
        gridIndexX = _p.gridIndexX;
        gridIndexY = _p.gridIndexY;
        weight = _p.weight;
    }

}