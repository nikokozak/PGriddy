import processing.core.PApplet;
import processing.core.PImage;

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

        this.points = Helpers.clonePoints(_pg);
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

        this.points = Helpers.clonePoints(_pg);

        for (int i = 0; i < x; i += 1) {
            for (int j = 0; j < y; j += 1) {
                this.points.get(i).get(j).weight = 0;
            }
        }
    }


    // * =========== DRAWING TOOLS ============== * //

    public void draw() {

        Draw.grid(this);

    }

    public void draw(int _type) {

        Draw.grid(_type, this);

    }

    public void draw(int _type, boolean _weight) {

        Draw.grid(_type, _weight, this);

    }

    public void draw(int _type, float _size, boolean _weight) {

        Draw.grid(_type, _size, _weight, this);

    }

    // * =========== TRANSLATION TOOLS ============== * //

    public Point_Grid move(int _x, int _y) {

        return Move.grid(_x, _y, this);

    }

    public Point_Grid move_to(int _x, int _y) {

        return Move.grid_to(_x, _y, this);

    }

    public Point_Grid move_reset() {

       return Move.grid_reset(this);

    }

    // * =========== UNIVERSAL APPLICATORS ============== * //

    public void color(int _col) {

        // Sets all Points in the Grid to _col.
        // Where:
        // _col -> Processing color()

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                currPoint.col = _col;
            }
        }
    }

    public void weight(double _weight) {

        // Sets all Points to a given weight.
        // Where:
        // _weight -> weight to set. DOUBLE[0.0-1.0]

        _weight = Helpers.clamp(_weight, 0.0, 1.0);

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                currPoint.weight = _weight;
            }
        }
    }

    public void weight_add(double _to_add) {

       // Adds a given weight to all points equally.
       // Where:
       // _to_add -> weight to add. DOUBLE[0.0-1.0]

       _to_add = Helpers.clamp(_to_add, 0.0, 1.0);

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                currPoint.weight = Helpers.clamp(currPoint.weight + _to_add, 0, 1);
            }
        }
    }

    public void weight_multiply(double _factor) {

       // Multiplies the weights of all points by a given number.
       // Where:
       // _factor -> factor by which to multiply

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                currPoint.weight = Helpers.clamp(currPoint.weight * _factor, 0, 1);
            }
        }
    }

    public void weight_reset() {

        // Sets all Points to weight 1.0.

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                currPoint.weight = 1.0;
            }
        }
    }

    public void filter(double _low, double _high) {

        // Sets all weights outside the threshold to zero.
        // Where:
        // _low -> floor of threshold
        // _high -> ceiling of threshold

        for (ArrayList<Grid_Point> column : this.points)  {
            for (Grid_Point currPoint : column) {
                if (currPoint.weight < _low || currPoint.weight > _high) {
                   currPoint.weight = 0;
                }
            }
        }
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

    public Point_Grid perlin(double _min, double _max, float _time, boolean _blend, double _opacity) {

        return Noise.apply_perlin(_min, _max, _time, _blend, _opacity, this);

    }

    public Point_Grid random(double _min, double _max, boolean _blend, double _opacity) {

        return Noise.apply_random(_min, _max, _blend, _opacity, this);

    }

    public Point_Grid clover_2D(double _min, double _max, boolean _blend, double _opacity) {

        return Noise.apply_clover_2D(_min, _max, _blend, _opacity, this);

    }

    public Point_Grid clover_fractal(double _min, double _max, int _iterations, boolean _blend, double _opacity) {

        return Noise.apply_clover_fractal(_min, _max, _iterations, _blend, _opacity, this);

    }

    public Point_Grid clover_frost(double _min, double _max, boolean _blend, double _opacity) {

        return Noise.apply_clover_frost(_min, _max, _blend, _opacity, this);

    }

    public Point_Grid clover_marble(double _min, double _max, boolean _blend, double _opacity) {

        return Noise.apply_clover_marble(_min, _max, _blend, _opacity, this);

    }

    public Point_Grid clover_curl(double _min, double _max, double _mix, boolean _blend, double _opacity) {

        return Noise.apply_clover_curl(_min, _max, _mix, _blend, _opacity, this);

    }

    public Point_Grid clover_curlFractal(double _min, double _max, int _iterations, double _mix, boolean _blend, double _opacity) {

        return Noise.apply_clover_curlFractal(_min, _max, _iterations, _mix, _blend, _opacity, this);

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