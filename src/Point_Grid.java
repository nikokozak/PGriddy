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

    public final int x, y, sX, sY;
    public int xOrigin, yOrigin; // Define the lowest X and Y coordinates for the grid system.
    public Point c;
    public ArrayList<ArrayList<Grid_Point>> points;


    // * =========== CONSTRUCTORS ============== * //

    public Point_Grid(int _x, int _y, Point _c, int _sX, int _sY) {
        this.x = _x;
        this.y = _y;
        this.c = _c;
        this.sX = _sX;
        this.sY = _sY;

        this.xOrigin = (int)_c.x - ((_x/2)*_sX);
        this.yOrigin = (int)_c.y - ((_y/2)*_sY);

        this.points = new ArrayList<ArrayList<Grid_Point>>();

        for (int i = 0; i < _x; i += 1) {
            int xPos = this.xOrigin + (i * _sX);
            this.points.add(new ArrayList<Grid_Point>());
            for (int j = 0; j < _y; j += 1) {
                int yPos = this.yOrigin + (j * _sY);
                this.points.get(i).add(new Grid_Point(xPos, yPos, i, j));
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

    public Point_Grid (Point_Grid _pg, boolean _zeroWeight) { // Token override to create grid with zero weights.
        this.x = _pg.x; this.y = _pg.y;
        this.c = new Point(_pg.c);
        this.sX = _pg.sX; this.sY = _pg.sY;

        this.xOrigin = _pg.xOrigin;
        this.yOrigin = _pg.yOrigin;

        this.points = Helpers.cloneGridPoints(_pg);

        for (int i = 0; i < x; i += 1) {
            for (int j = 0; j < y; j += 1) {
                this.points.get(i).get(j).weight = 0;
            }
        }
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

        return Move.grid_select_move(_x, _y, _s, this);

    }

    public Point_Grid move_mult(int _x, int _y) {

       return Move.mult(_x, _y, this);

    }

    public Point_Grid move_mult(int _x, int _y, Selection _s) {

        return Move.grid_select_mult(_x, _y, _s, this);

    }

    public Point_Grid move_to(int _x, int _y) {

        return Move.grid_to(_x, _y, this);

    }

    public Point_Grid move_to(int _x, int _y, Selection _s) {

        return Move.grid_select_to(_x, _y, _s, this);

    }

    public Point_Grid move_reset() {

       return Move.reset(this);

    }

    public Point_Grid move_reset(Selection _s) {

        return Move.grid_select_reset(_s, this);

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

    public Point_List get_points_by_weight(int _floor, int _ceil) {

       return Getters.get_grid_points_by_weight(_floor, _ceil, this);

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


    public Point_Grid radGradient(int _col, int _row, double _rad, double _init_weight, boolean _inverse, boolean _blend, double _opacity) {

       return Gradients.apply_radGradient(_col, _row, _rad, _init_weight, _inverse, _blend, _opacity, this);

    }

    public Point_Grid radGradient_slow(int _col, int _row, double _rad, double _init_weight, boolean _inverse, boolean _blend, double _opacity) {

        return Gradients.apply_radGradient_slow(_col, _row, _rad, _init_weight, _inverse, _blend, _opacity, this);

    }

    public Point_Grid radGradient_ease(int _col, int _row, double _rad, double _init_weight, double _feather, boolean _inverse, boolean _blend, double _opacity) {

        return Gradients.apply_radGradient_ease(_col, _row, _rad, _init_weight, _feather, _inverse, _blend, _opacity, this);

    }

    public Point_Grid radGradient_slow_ease(int _col, int _row, double _rad, double _init_weight, double _feather, boolean _inverse, boolean _blend, double _opacity) {

        return Gradients.apply_radGradient_slow_ease(_col, _row, _rad, _init_weight, _feather, _inverse, _blend, _opacity, this);

    }

    public Point_Grid sinGradient(int _col, int _row, double _rad, double _min_weight, double _max_weight, double _frequency, double _shift, boolean _inverse, boolean _blend, double _opacity) {

        return Gradients.apply_sinGradient(_col, _row, _rad, _min_weight, _max_weight, _frequency, _shift, _inverse, _blend, _opacity, this);

    }

    public Point_Grid sinGradient_slow(int _col, int _row, double _rad, double _min_weight, double _max_weight, double _frequency, double _shift, boolean _inverse, boolean _blend, double _opacity) {

        return Gradients.apply_sinGradient_slow(_col, _row, _rad, _min_weight, _max_weight, _frequency, _shift, _inverse, _blend, _opacity, this);

    }

    /// * =========== NOISE APPLICATORS ============== * ///

    public Point_Grid noise_perlin(double _min, double _max, float _time, boolean _blend, double _opacity) {

        return Noise.apply_perlin(_min, _max, _time, _blend, _opacity, this);

    }

    public Point_Grid noise_random(double _min, double _max, boolean _blend, double _opacity) {

        return Noise.apply_random(_min, _max, _blend, _opacity, this);

    }

    public Point_Grid noise_clover_2D(double _min, double _max, boolean _blend, double _opacity) {

        return Noise.apply_clover_2D(_min, _max, _blend, _opacity, this);

    }

    public Point_Grid noise_clover_fractal(double _min, double _max, int _iterations, boolean _blend, double _opacity) {

        return Noise.apply_clover_fractal(_min, _max, _iterations, _blend, _opacity, this);

    }

    public Point_Grid noise_clover_frost(double _min, double _max, boolean _blend, double _opacity) {

        return Noise.apply_clover_frost(_min, _max, _blend, _opacity, this);

    }

    public Point_Grid noise_clover_marble(double _min, double _max, boolean _blend, double _opacity) {

        return Noise.apply_clover_marble(_min, _max, _blend, _opacity, this);

    }

    public Point_Grid noise_clover_curl(double _min, double _max, double _mix, boolean _blend, double _opacity) {

        return Noise.apply_clover_curl(_min, _max, _mix, _blend, _opacity, this);

    }

    public Point_Grid noise_clover_curlFractal(double _min, double _max, int _iterations, double _mix, boolean _blend, double _opacity) {

        return Noise.apply_clover_curlFractal(_min, _max, _iterations, _mix, _blend, _opacity, this);

    }

    public Point_Grid noise_value(double _min, double _max, double _time, double _freq, boolean _blend, double _opacity) {

        return Noise.apply_value(_min, _max, _time, _freq, _blend, _opacity, this);

    }

    public Point_Grid noise_value_fractal(double _min, double _max, double _time, double _freq, int _octaves, double _lacunarity, double _gain, boolean _blend, double _opacity) {

        return Noise.apply_value_fractal(_min, _max, _time, _freq, _octaves, _lacunarity, _gain, _blend, _opacity, this);

    }

    public Point_Grid noise_simplex(double _min, double _max, double _time, double _freq, boolean _blend, double _opacity) {

        return Noise.apply_simplex(_min, _max, _time, _freq, _blend, _opacity, this);

    }

    public Point_Grid noise_simplex_fractal(double _min, double _max, double _time, double _freq, int _octaves, double _lacunarity, double _gain, boolean _blend, double _opacity) {

        return Noise.apply_simplex_fractal(_min, _max, _time, _freq, _octaves, _lacunarity, _gain, _blend, _opacity, this);

    }

    public Point_Grid noise_cellular(double _min, double _max, double _time, double _freq, boolean _blend, double _opacity) {

        return Noise.apply_cellular(_min, _max, _time, _freq, _blend, _opacity, this);

    }

    public Point_Grid noise_cubic(double _min, double _max, double _time, double _freq, boolean _blend, double _opacity) {

        return Noise.apply_cubic(_min, _max, _time, _freq, _blend, _opacity, this);

    }

    public Point_Grid noise_cubic_fractal(double _min, double _max, double _time, double _freq, int _octaves, double _lacunarity, double _gain, boolean _blend, double _opacity) {

        return Noise.apply_cubic_fractal(_min, _max, _time, _freq, _octaves, _lacunarity, _gain, _blend, _opacity, this);

    }

    public Point_Grid noise_perlin_fractal(double _min, double _max, double _time, double _freq, int _octaves, double _lacunarity, double _gain, boolean _blend, double _opacity) {

        return Noise.apply_perlin_fractal(_min, _max, _time, _freq, _octaves, _lacunarity, _gain, _blend, _opacity, this);

    }

    /// * =========== IMAGE APPLICATORS ============== * ///

    public void image(PImage _img, String _mode, int _shift_x, int _shift_y, boolean _subtract, boolean _blend, double _opacity) {
        // TODO: Add Scaling

        // Loads an image and applies weights to Grid_Points in Point_Grid
        // based on R, G, B, L (lightness) values or combinations thereof.
        // Where:
        // _img -> PImage to sample from
        // _mode -> any of the following: "r", "g", "b", "l" (luma)
        // _shift_x -> shift the image left (-) or right (+)
        // _shift_y -> shift the image top (+) or bottom (-)
        // _blend -> whether to blend the new weights onto previous weights
        // _subtract -> whether to subtract the new weights from previous weights (only works in blend mode)
        // _opacity -> opacity of new weights

        PImage new_img;

        int grid_pixel_width = this.x * this.sX;
        int grid_pixel_height = this.y * this.sY;

        int sample_padding_X = Math.abs((grid_pixel_width - _img.width)/2);
        int sample_padding_Y = Math.abs((grid_pixel_height - _img.height)/2);

        if (_img.width > grid_pixel_width || _img.height > grid_pixel_height) {
            new_img = _img.get(sample_padding_X, sample_padding_Y, grid_pixel_width, grid_pixel_height);
            new_img.loadPixels();
            sample_padding_X = 0;
            sample_padding_Y = 0;
        } else {
            new_img = _img;
            new_img.loadPixels();
        }

        Grid_Point currPoint;
        int x, y, r, g, b;
        int currPixel;
        double weight = 0;

        for (int x_g = 0; x_g < this.x; x_g++) {
            x = (sample_padding_X + _shift_x) + (x_g * this.sX);
            for (int y_g = 0; y_g < this.y; y_g++) {
                y = (sample_padding_Y + _shift_y) + (y_g * this.sY);
                if (y*new_img.width+x > new_img.pixels.length - 1 || y*new_img.width+x < 0) currPixel = Core.processing.color(0);
                else currPixel = new_img.pixels[y*new_img.width+x];
                currPoint = this.points.get(x_g).get(y_g);
                r = (currPixel >> 16) & 0xFF;
                g = (currPixel >> 8) & 0xFF;
                b = currPixel & 0xFF;
                switch (_mode) {
                    case ("r") -> weight = PApplet.map((float) r, 0, 255, 0, 1);
                    case ("g") -> weight = PApplet.map((float) g, 0, 255, 0, 1);
                    case ("b") -> weight = PApplet.map((float) b, 0, 255, 0, 1);
                    case ("l") -> weight = PApplet.map(Helpers.rgbToLuma(r, g, b), 0, 255, 0, 1);
                }
                if (_subtract && _blend) weight *= -1;
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + weight * _opacity, 0.0, 1.0);
                else currPoint.weight = weight * _opacity;

            }
        }

    }


    // * =========== PRIVATE HELPERS ============== * //

    public int check_quad(Grid_Point _pg) {

        // Returns the number corresponding to the quadrant a given Grid_Point is in.
        // 1 -> TR, 2 -> TL, 3 -> BL, 4 -> BR
        // Where:
        // _gp -> Grid_Point to test

        int x_mid = this.x / 2;
        int y_mid = this.y / 2;

        if (_pg.gX > x_mid && _pg.gX < y_mid) return 1;
        else if (_pg.gX < x_mid && _pg.gY < y_mid) return 2;
        else if (_pg.gX < x_mid && _pg.gY > y_mid) return 3;
        else return 4;

    }

    public double grid_approx_dist(Grid_Point _pg1, Grid_Point _pg2) {

        // Returns a non-sqrt based distance between two points in Grid.
        // Use when a precise distance is not necessary.
        // Distance is calculated using grid indices and not actual x y values.
        // Where:
        // _pg1 -> First point
        // _pg2 -> Second point

        return Math.pow(_pg1.gX - _pg2.gX, 2) + Math.pow(_pg1.gY - _pg2.gY, 2);

    }

    public double grid_exact_dist(Grid_Point _pg1, Grid_Point _pg2) {

        // Returns the sqrt based distance between two points in Grid.
        // Avoid using if possible.
        // Distance is calculated using grid indices and not actual x y values.
        // Where:
        // _pg1 -> First point
        // _pg2 -> Second point

        return Math.sqrt(Math.pow(_pg1.gX - _pg2.gX, 2) + Math.pow(_pg1.gY - _pg2.gY, 2));

    }

    private int szdudzik_hash(int a, int b) {

        // Returns a unique value for any two integers a, b >= 0

        return a >= b ? a * a + a + b : a + b * b;

    }

}