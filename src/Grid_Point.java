
public class Grid_Point extends Point {

    // a GRID_POINT is a data structure
    // GRID_POINT ( x: INT, y: INT, gridIndexX: INT, gridIndexY: INT )
    // interpretation: a GRID_POINT represents a POINT acquired from a (belonging to) a GRID
    // gX -> if created as part of a POINT_GRID, COL index of said POINT_GRID
    // gY -> if created as part of a POINT_GRID, ROW index of said POINT_GRID
    // weight -> associated weight of point.

    public final int gX, gY;

    public Grid_Point (int _x, int _y, int _ix, int _iy) {
        super(_x, _y);
        this.gX = _ix;
        this.gY = _iy;
        this.weight = 1;
    }

    public Grid_Point (int _x, int _y, int _gx, int _gy, double _weight) {
        super(_x, _y);
        this.gX = _gx;
        this.gY = _gy;
        this.weight = _weight;
    }

    public Grid_Point (Grid_Point _p) {
        super(_p);
        this.gX = _p.gX;
        this.gY = _p.gY;
    }

}