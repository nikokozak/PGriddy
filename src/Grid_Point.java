import java.util.Objects;
import java.util.Optional;

public class Grid_Point extends Point {

    // a GRID_POINT is a data structure
    // GRID_POINT ( x: INT, y: INT, gridIndexX: INT, gridIndexY: INT )
    // interpretation: a GRID_POINT represents a POINT acquired from a (belonging to) a GRID
    // gX -> if created as part of a POINT_GRID, COL index of said POINT_GRID
    // gY -> if created as part of a POINT_GRID, ROW index of said POINT_GRID
    // weight -> associated weight of point.

    public final int gridIndexX, gridIndexY;
    private PointGrid parentGrid;

    public Grid_Point (int _x, int _y, int _gx, int _gy) {
        super(_x, _y);
        this.gridIndexX = _gx;
        this.gridIndexY = _gy;
    }

    public Grid_Point (int _x, int _y, int _gx, int _gy, PointGrid parent) {
        this(_x, _y, _gx, _gy);
        this.parentGrid = parent;
    }

    public Grid_Point (int _x, int _y, int _gx, int _gy, double _weight) {
        this(_x, _y, _gx, _gy);
        this.weight = _weight;
    }

    public Grid_Point (int _x, int _y, int _gx, int _gy, double _weight, PointGrid parent) {
        this(_x, _y, _gx, _gy, _weight);
        this.parentGrid = parent;
    }

    public Grid_Point (int _x, int _y, int _gx, int _gy, int _color, double _weight) {
        this(_x, _y, _gx, _gy, _weight);
        this.col = _color;
    }

    public Grid_Point (int _x, int _y, int _gx, int _gy, int _color, double _weight, PointGrid parent) {
        this(_x, _y, _gx, _gy, _color, _weight);
        this.parentGrid = parent;
    }

    public Grid_Point (Grid_Point _p) {
        super(_p);
        this.gridIndexX = _p.gridIndexX;
        this.gridIndexY = _p.gridIndexY;
        this.weight = _p.weight;
        this.parentGrid = _p.parentGrid;
    }

    public Optional<PointGrid> getParentGrid() {
        return Optional.ofNullable(this.parentGrid);
    }

    public void setParentGrid(PointGrid parentGrid) {
        this.parentGrid = parentGrid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid_Point that = (Grid_Point) o;
        return gridIndexX == that.gridIndexX &&
                gridIndexY == that.gridIndexY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gridIndexX, gridIndexY);
    }
}