import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Point_Grid implements Iterable<Grid_Point>{

    // a POINT_GRID is a data structure
    // a POINT_GRID contains a 2D collection of POINTs
    // NOTE: 2D ArrayList is used to store points as opposed to Processing Core's flat array preference: performance difference was negligible vs code clarity gained.
    // Where:
    // x -> Number of POINTs in X axis (INT)
    // y -> Number of POINTs in Y axis (INT)
    // c -> Global center of GRID (POINT)
    // sX -> Spacing between POINTs in X axis (INT)
    // sY -> Spacing between POINTs in Y axis (INT)

    // * =========== PROPERTIES ============== * //

    public final int x, y; // Number of Grid_Points in x-dir and y-dir (cols, rows).
    public final int sX, sY; // Spacing between points in x and y axes.

    public int xOrigin, yOrigin; // Define the top-left X and Y pixel-coordinate values for the grid system.
    public Point c; // CenterPoint of Grid (x, y pixel-coordinate values)
    public ArrayList<ArrayList<Grid_Point>> points; // Points held by grid.


    // * =========== CONSTRUCTORS ============== * //

    public Point_Grid(Point _c, int _x, int _y, int _sX, int _sY) {
        this.x = _x;
        this.y = _y;
        this.c = _c;
        this.sX = _sX;
        this.sY = _sY;

        this.xOrigin = (int)(_c.xPos - ((_x/2)*_sX));
        this.yOrigin = (int)_c.yPos - ((_y/2)*_sY);

        this.points = new ArrayList<ArrayList<Grid_Point>>();

        for (int i = 0; i < _x; i += 1) {
            int xPos = this.xOrigin + (i * _sX);
            this.points.add(new ArrayList<Grid_Point>());
            for (int j = 0; j < _y; j += 1) {
                int yPos = this.yOrigin + (j * _sY);
                this.points.get(i).add(new Grid_Point(xPos, yPos, i, j, this));
            }
        }
    }

    public Point_Grid (Point_Grid _pg) {
        this.x = _pg.x; this.y = _pg.y;
        this.c = new Point(_pg.c);
        this.sX = _pg.sX; this.sY = _pg.sY;

        this.xOrigin = _pg.xOrigin;
        this.yOrigin = _pg.yOrigin;

        this.points = Helpers.cloneGridPoints(_pg);
    }

    public Point_Grid (Point_Grid _pg, ArrayList<ArrayList<Grid_Point>> _al) {
        this.c = new Point(_pg.c);
        this.sX = _pg.sX; this.sY = _pg.sY;

        this.xOrigin = _pg.xOrigin;
        this.yOrigin = _pg.yOrigin;

        this.points = new ArrayList<ArrayList<Grid_Point>>(_al);

        this.x = points.size(); this.y = points.get(0).size();
    }

    // * =========== ITERATOR ============== * //

    @Override
    public Iterator<Grid_Point> iterator() {
        return new Grid_Iterator(this.points);
    }

    // * =========== DRAWING TOOLS ============== * //

    public void draw() {

        Draw.draw(this);

    }

    public void draw(int _type) {

        Draw.draw(_type, this);

    }

    public void draw(int _type, boolean _weight) {

        Draw.draw(_type, _weight, this);

    }

    public void draw(int _type, float _size, boolean _weight) {

        Draw.draw(_type, _size, _weight, this);

    }

    // * =========== TRANSLATION TOOLS ============== * //

    public Point_Grid move(int _x, int _y) {

        return Move.move(_x, _y, this);

    }

    public Point_Grid move(int _x, int _y, Selection _s) {

        return Move.grid_selection_move(_x, _y, _s, this);

    }

    public Point_Grid move_mult(int _x, int _y) {

       return Move.multiply_posns(_x, _y, this);

    }

    public Point_Grid move_mult(int _x, int _y, Selection _s) {

        return Move.grid_selection_multiply_posns(_x, _y, _s, this);

    }

    public Point_Grid move_to(int _x, int _y) {

        return Move.grid_to(_x, _y, this);

    }

    public Point_Grid move_to(int _x, int _y, Selection _s) {

        return Move.grid_selection_to(_x, _y, _s, this);

    }

    public Point_Grid move_reset() {

       return Move.reset_posns(this);

    }

    public Point_Grid move_reset(Selection _s) {

        return Move.grid_selection_reset_posns(_s, this);

    }

    // * =========== GETTERS ============== * //

    public Grid_Point get_point(int _col, int _row) {

        return Getters.get_grid_point(_col, _row, this);

    }

    public Grid_Point get_point_safe(int _col, int _row) {

        return Getters.get_grid_point_safe(_col, _row, this);

    }

    public Grid_Point get_point_mirror(int _col, int _row) {

        return Getters.get_grid_point_mirror(_col, _row, this);

    }

    public Grid_Point get_point_mirror_x(int _col, int _row) {

        return Getters.get_grid_point_mirror_x(_col, _row, this);

    }

    public Grid_Point get_point_mirror_y(int _col, int _row) {

        return Getters.get_grid_point_mirror_y(_col, _row, this);

    }

    public Point_List get_points_by_weight(double _floor, double _ceil) {

       return Getters.get_points_by_weight(_floor, _ceil, this);

    }

    public Point_List get_column(int _x) {

        return Getters.get_grid_column(_x, this);

    }

    public Point_List get_row(int _y) {

        return Getters.get_grid_row(_y, this);

    }

    public Point_List get_line(int _col0, int _row0, int _col1, int _row1) {

        return Getters.get_grid_line(_col0, _row0, _col1, _row1, this);

    }

    public Point_List get_circle(int _col0, int _row0, int _rad) {

        return Getters.get_grid_circle(_col0, _row0, _rad, this);

    }

    public Point_List get_circle_fill(int _col, int _row, int _rad) {

        return Getters.get_grid_circle_fill(_col, _row, _rad, this);

    }

    public Point_List getLine_No_Op(int _col0, int _row0, int _col1, int _row1) {

        return Getters.get_grid_line_no_op(_col0, _row0, _col1, _row1, this);

    }

    public Point_List get_polyline(Point_List _pl, boolean _closed) {

        return Getters.get_grid_polyline(_pl, _closed, this);

    }

    public Point_List get_polyline_fill(Point_List _pl) {

        return Getters.get_grid_polyline_fill(_pl, this);

    }

    public Point_List get_pattern(int _col, int _row, List<Integer> _dlist, int _reps, boolean _overflow) {

        return Getters.get_grid_pattern(_col, _row, _dlist, _reps, _overflow, this);

    }

    public Point_List get_every(int _x, int _y) {

        return Getters.get_grid_every(_x, _y, this);

    }

    public Point_List get_region(int _x1, int _y1, int _x2, int _y2) {

        return Getters.get_grid_region(_x1, _y1, _x2, _y2, this);

    }

    public Point_List get_text(String _sentence, int _xOrigin, int _yOrigin, int _size) {

        return Text.get_sentence(_sentence, _xOrigin, _yOrigin, _size, this);

    }

    // * =========== UNIVERSAL APPLICATORS ============== * //

    public void color(int _col) {

        //Applicators.grid_color(_col, this);
        Applicators.color(_col, this);

    }

    public void color(int _col, Selection _s) {

        Applicators.grid_select_color(_col, _s, this);

    }

    public void weight(double _weight) {

        Applicators.weight(_weight, this);

    }

    public void weight(double _weight, Selection _s) {

        Applicators.grid_select_weight(_weight, _s, this);

    }

    public void weight_add(double _to_add) {

        Applicators.weight_add(_to_add, this);

    }

    public void weight_add(double _to_add, Selection _s) {

        Applicators.grid_select_weight_add(_to_add, _s, this);

    }

    public void weight_multiply(double _factor) {

        Applicators.weight_multiply(_factor, this);

    }


    public void weight_multiply(double _factor, Selection _s) {

        Applicators.grid_select_weight_multiply(_factor, _s, this);

    }

    public void weight_reset() {

        Applicators.weight_reset(this);

    }

    public void weight_reset(Selection _s) {

        Applicators.grid_select_weight_reset(_s, this);

    }

    public void filter(double _low, double _high) {

        Applicators.weight_filter(_low, _high, this);

    }

    public void filter(double _low, double _high, Selection _s) {

        Applicators.grid_select_weight_filter(_low, _high, _s, this);

    }

    // * =========== GRADIENT APPLICATORS ============== * //


    public Point_Grid radGradient(Gradient grad) {

        grad.applyWeightsToPoints(this);
        return this;

    }

    /// * =========== NOISE APPLICATORS ============== * ///

    public Point_Grid applyNoise(Noise noise) {

        noise.applyWeightToPoints(this);
        return this;

    }

    /// * =========== IMAGE APPLICATORS ============== * ///

    public void image(PImage _img, String _mode, int _shift_x, int _shift_y, boolean _subtract, boolean _blend, double _opacity) {
        // TODO: Add Scaling

        Image.image(_img, _mode, _shift_x, _shift_y, _subtract, _blend, _opacity, this);

    }


    // * =========== PRIVATE HELPERS ============== * //

    public int check_quad(Grid_Point grid_point) {

        // Returns the number corresponding to the quadrant a given Grid_Point is in.
        // 1 -> TR, 2 -> TL, 3 -> BL, 4 -> BR
        // Where:
        // _gp -> Grid_Point to test

        int x_mid = this.x / 2;
        int y_mid = this.y / 2;

        if (grid_point.gridIndexX > x_mid && grid_point.gridIndexX < y_mid) return 1;
        else if (grid_point.gridIndexX < x_mid && grid_point.gridIndexY < y_mid) return 2;
        else if (grid_point.gridIndexX < x_mid && grid_point.gridIndexY > y_mid) return 3;
        else return 4;

    }

    public double grid_approx_dist(Grid_Point grid_point_1, Grid_Point grid_point_2) {

        // Returns a non-sqrt based distance between two points in Grid.
        // Use when a precise distance is not necessary.
        // Distance is calculated using grid indices and not actual x y values.
        // Where:
        // _pg1 -> First point
        // _pg2 -> Second point

        return Math.pow(grid_point_1.gridIndexX - grid_point_2.gridIndexX, 2) + Math.pow(grid_point_1.gridIndexY - grid_point_2.gridIndexY, 2);

    }

    public double grid_exact_dist(Grid_Point grid_point_1, Grid_Point grid_point_2) {

        // Returns the sqrt based distance between two points in Grid.
        // Avoid using if possible.
        // Distance is calculated using grid indices and not actual x y values.
        // Where:
        // _pg1 -> First point
        // _pg2 -> Second point

        return Math.sqrt(Math.pow(grid_point_1.gridIndexX - grid_point_2.gridIndexX, 2) + Math.pow(grid_point_1.gridIndexY - grid_point_2.gridIndexY, 2));

    }

    private int szdudzik_hash(int a, int b) {

        // Returns a unique value for any two integers a, b >= 0

        return a >= b ? a * a + a + b : a + b * b;

    }

}