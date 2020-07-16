import processing.core.PApplet;
import processing.core.PImage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

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
        // Draws points to screen as a simple Processing point (no fill, weight).

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                Core.processing.point(currPoint.x, currPoint.y);
            }
        }
    }

    public void draw(int type) {
        // Draws points to screen as either a Processing point, circle, or rect (size 3 for circle and rect).
        // No fill, or weight set.
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect

        if (type > 2 || type < 0) type = 0;

        Core.processing.rectMode(3); // Set rectMode to CENTER;

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                switch (type) {
                    case 0 -> Core.processing.point(currPoint.x, currPoint.y);
                    case 1 -> Core.processing.circle(currPoint.x, currPoint.y, 3);
                    case 2 -> Core.processing.rect(currPoint.x, currPoint.y, 3, 3);
                }
            }
        }
    }

    public void draw(int type, boolean weight) {
        // Draws points to screen as either a Processing point, circle, or rect (size 3 for circle and rect).
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect
        // weight -> whether to set alpha as weight.
        Core.processing.rectMode(3); // Set rectMode to CENTER;

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                if (weight) Core.processing.fill(currPoint.col, Helpers.weightToRGB(currPoint.weight));
                else Core.processing.fill(currPoint.col);
                switch (type) {
                    case 0 -> Core.processing.point(currPoint.x, currPoint.y);
                    case 1 -> Core.processing.circle(currPoint.x, currPoint.y, 3);
                    case 2 -> Core.processing.rect(currPoint.x, currPoint.y, 3, 3);
                }
            }
        }
    }

    public void draw(int type, float size, boolean weight) {
        // Draws points to screen as either a Processing point, circle, or rect.
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect
        // size -> size of rect or circle.
        // weight -> whether to set alpha as weight.
        Core.processing.rectMode(3);

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                if (weight) Core.processing.fill(currPoint.col, Helpers.weightToRGB(currPoint.weight));
                else Core.processing.fill(currPoint.col);
                switch (type) {
                    case 0 -> Core.processing.point(currPoint.x, currPoint.y);
                    case 1 -> Core.processing.circle(currPoint.x, currPoint.y, size);
                    case 2 -> Core.processing.rect(currPoint.x, currPoint.y, size, size);
                }
            }
        }
    }


    // * =========== TRANSLATION TOOLS ============== * //

    public void move(int _x, int _y) {
        // Adds x and y quantities to Point positions.

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                currPoint.y += _y;
                currPoint.x += _x;
            }
        }
    }

    public void move_to(int _x, int _y) {
        // Moves the entire Grid to a new x, y center.
        // RESETS ALL PAREMETERS PERTAINING TO ORIGIN, ETC.
        Point newCenter = new Point(_x, _y);
        this.c = newCenter;

        this.xOrigin = (int)newCenter.x - ((this.x/2)*this.sX);
        this.yOrigin = (int)newCenter.y - ((this.y/2)*this.sY);

        for (int i = 0; i < this.x; i += 1) {
            int xPos = this.xOrigin + (i * this.sX);
            for (int j = 0; j < this.y; j += 1) {
                int yPos = this.yOrigin + (j * this.sY);
                this.points.get(i).get(j).x = xPos;
                this.points.get(i).get(j).y = yPos;
            }
        }
    }

    public void move_reset() {
        // Resets all Points to their original positions.

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                currPoint.x = currPoint.oX;
                currPoint.y = currPoint.oY;
            }
        }
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


    public void radGradient(int _col, int _row, double _rad, double _init_weight, boolean _inverse, boolean _blend, double _opacity) {

        // Modifies weights of Grid_Points, adding to a cubic radial gradient
        // Distances from user-define centerpoint are calculated using an approximation (non-sqrt)
        // Where:
        // _col, _row -> centerpoint of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _init_weight -> initial weight value for gradient
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _opacity -> opacity of gradient

        double MAX = 1, MIN = 0;
        double dist;
        double rad = Math.pow(_rad, 2);

        Grid_Point center_point = Getters.getPoint(_col, _row, this);
        switch(check_quad(center_point)) {
            case 1 -> MAX = grid_approx_dist(center_point, Getters.getPoint(0, this.y - 1, this));
            case 2 -> MAX = grid_approx_dist(center_point, Getters.getPoint(this.x - 1, this.y - 1, this));
            case 3 -> MAX = grid_approx_dist(center_point, Getters.getPoint(this.x - 1, 0, this));
            case 4 -> MAX = grid_approx_dist(center_point, Getters.getPoint(0, 0, this));
        }

        rad = rad - MAX;
        double currWeight;

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                dist = grid_approx_dist(currPoint, center_point);
                if (_inverse) currWeight = Helpers.map(dist, MIN, MAX + rad, 0, _init_weight) * _opacity;
                else currWeight = Helpers.map(dist, MAX + rad, MIN, 0, _init_weight) * _opacity;
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + currWeight, 0, 1);
                else currPoint.weight = currWeight;
            }
        }

    }

    public void radGradient_slow(int _col, int _row, double _rad, double _init_weight, boolean _inverse, boolean _blend, double _opacity) {

        // Modifies weights of Grid_Points, adding to a linear radial gradient
        // Distances from user-define centerpoint are calculated using a^2 + b^2 = c^2
        // Therefore, sqrt() is used extensively - this function should be avoided at all costs if possible.
        // This version is approx. 4x slower than radGradient();
        // Where:
        // _col, _row -> centerpoint of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _init_weight -> initial weight value for gradient
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _opacity -> opacity of gradient

        double MAX = 1, MIN = 0;
        double dist;

        Grid_Point center_point = Getters.getPoint(_col, _row, this);
        switch(check_quad(center_point)) {
            case 1 -> MAX = grid_exact_dist(center_point, Getters.getPoint(0, this.y - 1, this));
            case 2 -> MAX = grid_exact_dist(center_point, Getters.getPoint(this.x - 1, this.y - 1, this));
            case 3 -> MAX = grid_exact_dist(center_point, Getters.getPoint(this.x - 1, 0, this));
            case 4 -> MAX = grid_exact_dist(center_point, Getters.getPoint(0, 0, this));
        }

        _rad = _rad - MAX;
        double currWeight;

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                dist = grid_exact_dist(currPoint, center_point);
                if (_inverse) currWeight = Helpers.map(dist, MIN, MAX + _rad, 0, _init_weight) * _opacity;
                else currWeight = Helpers.map(dist, MAX + _rad, MIN, 0, _init_weight) * _opacity;
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + currWeight, 0, 1);
                else currPoint.weight = currWeight;
            }
        }

    }

    public void radGradient_ease(int _col, int _row, double _rad, double _init_weight, double _feather, boolean _inverse, boolean _blend, double _opacity) {

        // Modifies weights of Grid_Points according to a in-out easing function.
        // Distances from user-define centerpoint are calculated using an approximation (non-sqrt)
        // Where:
        // _col, _row -> centerpoint of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _init_weight -> initial weight value for gradient
        // _feather -> how "hard" the gradient edge is (period of easing func)
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _opacity -> opacity of gradient

        double MAX = 1, MIN = 0;
        double dist;
        double rad = Math.pow(_rad, 2);

        Grid_Point center_point = Getters.getPoint(_col, _row, this);
        switch(check_quad(center_point)) {
            case 1 -> MAX = grid_approx_dist(center_point, Getters.getPoint(0, this.y - 1, this));
            case 2 -> MAX = grid_approx_dist(center_point, Getters.getPoint(this.x - 1, this.y - 1, this));
            case 3 -> MAX = grid_approx_dist(center_point, Getters.getPoint(this.x - 1, 0, this));
            case 4 -> MAX = grid_approx_dist(center_point, Getters.getPoint(0, 0, this));
        }

        rad = rad - MAX;
        double currWeight;

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                dist = grid_approx_dist(currPoint, center_point);
                if (_inverse) currWeight = Helpers.easeInOutCubic(Helpers.map(dist, MIN, MAX + rad, 0, _init_weight), _init_weight, _init_weight,  _feather) * _opacity;
                else currWeight = Helpers.easeInOutCubic(Helpers.map(dist, MAX + rad, MIN, 0, _init_weight), 0, _init_weight,  _feather) * _opacity;
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + currWeight, 0, 1);
                else currPoint.weight = currWeight;
            }
        }

    }

    public void radGradient_slow_ease(int _col, int _row, double _rad, double _init_weight, double _feather, boolean _inverse, boolean _blend, double _opacity) {

        // Modifies weights of Grid_Points according to a in-out easing function.
        // Distances from user-define centerpoint are calculated using a^2 + b^2 = c^2
        // Therefore, sqrt() is used extensively - this function should be avoided at all costs if possible.
        // This version is approx. 4x slower than radGradient();
        // Where:
        // _col, _row -> centerpoint of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _init_weight -> initial weight value for gradient
        // _feather -> how "hard" the gradient edge is (period of easing func)
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _opacity -> opacity of gradient

        double MAX = 1, MIN = 0;
        double dist;

        Grid_Point center_point = Getters.getPoint(_col, _row, this);
        switch(check_quad(center_point)) {
            case 1 -> MAX = grid_exact_dist(center_point, Getters.getPoint(0, this.y - 1, this));
            case 2 -> MAX = grid_exact_dist(center_point, Getters.getPoint(this.x - 1, this.y - 1, this));
            case 3 -> MAX = grid_exact_dist(center_point, Getters.getPoint(this.x - 1, 0, this));
            case 4 -> MAX = grid_exact_dist(center_point, Getters.getPoint(0, 0, this));
        }

        _rad = _rad - MAX;
        double currWeight;

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                dist = grid_exact_dist(currPoint, center_point);
                if (_inverse) currWeight = Helpers.easeInOutCubic(Helpers.map(dist, MIN, MAX + _rad, 0, _init_weight), _init_weight, _init_weight,  _feather) * _opacity;
                else currWeight = Helpers.easeInOutCubic(Helpers.map(dist, MAX + _rad, MIN, 0, _init_weight), 0, _init_weight,  _feather) * _opacity;
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + currWeight, 0, 1);
                else currPoint.weight = currWeight;
            }
        }

    }

    public void sinGradient(int _col, int _row, double _rad, double _min_weight, double _max_weight, double _frequency, double _shift, boolean _inverse, boolean _blend, double _opacity) {

        // Modifies weights of Grid_Points according to a sin function.
        // Distances from user-define centerpoint are calculated using an approximation (non-sqrt)
        // Where:
        // _col, _row -> centerpoint of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _min_weight -> lower bound for applied weight
        // _max_weight -> upper bound for applied weight
        // _frequency -> frequency of sin function
        // _shift -> shift of sin function
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _opacity -> opacity of gradient

        double MAX = 1, MIN = 0;
        double dist;
        double rad = Math.pow(_rad, 2);

        Grid_Point center_point = Getters.getPoint(_col, _row, this);
        switch(check_quad(center_point)) {
            case 1 -> MAX = grid_approx_dist(center_point, Getters.getPoint(0, this.y - 1, this));
            case 2 -> MAX = grid_approx_dist(center_point, Getters.getPoint(this.x - 1, this.y - 1, this));
            case 3 -> MAX = grid_approx_dist(center_point, Getters.getPoint(this.x - 1, 0, this));
            case 4 -> MAX = grid_approx_dist(center_point, Getters.getPoint(0, 0, this));
        }

        rad = rad - MAX;
        double currWeight;

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                dist = grid_approx_dist(currPoint, center_point);
                if (_inverse) currWeight = Helpers.map(Helpers.sinMap(Helpers.map(dist, MIN, MAX + rad, 0, 2*Math.PI), _frequency, _shift,  _max_weight), 1, -1, _min_weight, 1) * _opacity;
                else currWeight = Helpers.map(Helpers.sinMap(Helpers.map(dist, MIN, MAX, 0, 2*Math.PI), _frequency,  _shift,  _max_weight), -1, 1, _min_weight, 1) * _opacity;
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + currWeight, 0, 1);
                else currPoint.weight = currWeight;
            }
        }
    }

    public void sinGradient_slow(int _col, int _row, double _rad, double _min_weight, double _max_weight, double _frequency, double _shift, boolean _inverse, boolean _blend, double _opacity) {

        // Modifies weights of Grid_Points according to a sin function.
        // Distances from user-define centerpoint are calculated using pythagorean distance formula. Thus sqrt() is employed.
        // Avoid this function when possible - it is approx. 4 x slower than sinGradient()
        // Where:
        // _col, _row -> centerpoint of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _min_weight -> lower bound for applied weight
        // _max_weight -> upper bound for applied weight
        // _frequency -> frequency of sin function
        // _shift -> shift of sin function
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _opacity -> opacity of gradient

        double MAX = 1, MIN = 0;
        double dist;

        Grid_Point center_point = Getters.getPoint(_col, _row, this);
        switch(check_quad(center_point)) {
            case 1 -> MAX = grid_exact_dist(center_point, Getters.getPoint(0, this.y - 1, this));
            case 2 -> MAX = grid_exact_dist(center_point, Getters.getPoint(this.x - 1, this.y - 1, this));
            case 3 -> MAX = grid_exact_dist(center_point, Getters.getPoint(this.x - 1, 0, this));
            case 4 -> MAX = grid_exact_dist(center_point, Getters.getPoint(0, 0, this));
        }

        _rad = _rad - MAX;
        double currWeight;

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                dist = grid_exact_dist(currPoint, center_point);
                if (_inverse) currWeight = Helpers.map(Helpers.sinMap(Helpers.map(dist, MIN, MAX + _rad, 0, 2*Math.PI), _frequency, _shift,  _max_weight), 1, -1, _min_weight, 1) * _opacity;
                else currWeight = Helpers.map(Helpers.sinMap(Helpers.map(dist, MIN, MAX, 0, 2*Math.PI), _frequency,  _shift,  _max_weight), -1, 1, _min_weight, 1) * _opacity;
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + currWeight, 0, 1);
                else currPoint.weight = currWeight;
            }
        }
    }

    public void perlin(double _min, double _max, float _time, boolean _blend, double _opacity) {

        // Apply weights to points based on Perlin Noise.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _time -> Time (Z-axis) factor for animating Perlin (takes values from 0.0 - 1.0);
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                if (_blend) currPoint.weight = Helpers.clamp(
                        currPoint.weight + Helpers.map(Core.processing.noise(currPoint.x, currPoint.y, _time), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
                else currPoint.weight = Helpers.map(Core.processing.noise(currPoint.x, currPoint.y, _time), 0, 1, _min, _max) * _opacity; // Call Perlin ~
            }
        }

    }

    public void random(double _min, double _max, boolean _blend, double _opacity) {

        // Apply weights to points randomly.
        // Where:
        // _min -> Min weight threshold
        // _max -> Max weight threshold
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + Helpers.map(Core.processing.random(0, 1), 0.0, 1.0, _min, _max) * _opacity, 0, 1);
                else currPoint.weight = Helpers.map(Core.processing.random(0, 1), 0, 1, _min, _max) * _opacity;
            }
        }

    }

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

    public void clover_2D(double _min, double _max, boolean _blend, double _opacity) {

        // Apply weights to points based on 2D clover noise implementation.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        CloverNoise.Noise2D noise2D = new CloverNoise.Noise2D();

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                if (_blend) currPoint.weight = Helpers.clamp(
                        currPoint.weight + Helpers.map(noise2D.noise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
                else currPoint.weight = Helpers.map(noise2D.noise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity;
            }
        }

    }

    public void clover_fractal(double _min, double _max, int _iterations, boolean _blend, double _opacity) {

        // Apply weights to points based on 2D fractal clover noise implementation.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        CloverNoise.Noise2D noise2D = new CloverNoise.Noise2D();

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                if (_blend) currPoint.weight = Helpers.clamp(
                        currPoint.weight + Helpers.map(noise2D.fractalNoise(currPoint.x, currPoint.y, _iterations), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
                else currPoint.weight = Helpers.map(noise2D.fractalNoise(currPoint.x, currPoint.y, _iterations), 0, 1, _min, _max) * _opacity;
            }
        }
    }

    public void clover_frost(double _min, double _max, boolean _blend, double _opacity) {

        // Apply weights to points based on 2D frost clover noise implementation.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        CloverNoise.Noise2D noise2D = new CloverNoise.Noise2D();

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                if (_blend) currPoint.weight = Helpers.clamp(
                        currPoint.weight + Helpers.map(noise2D.frostNoise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
                else currPoint.weight = Helpers.map(noise2D.frostNoise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity;
            }
        }
    }

    public void clover_marble(double _min, double _max, boolean _blend, double _opacity) {

        // Apply weights to points based on 2D marble clover noise implementation.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        CloverNoise.Noise2D noise2D = new CloverNoise.Noise2D();

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                if (_blend) currPoint.weight = Helpers.clamp(
                        currPoint.weight + Helpers.map(noise2D.marbleNoise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
                else currPoint.weight = Helpers.map(noise2D.marbleNoise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity;
            }
        }
    }

    public void clover_curl(double _min, double _max, double _mix, boolean _blend, double _opacity) {

        // Apply weights to points based on 2D curl clover noise implementation.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _mix -> Curl is a Vector2, _mix determines bias when reducing to one double for weight.
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        CloverNoise.Noise2D noise2D = new CloverNoise.Noise2D();
        double xVal, yVal, weight;

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                CloverNoise.Vector2 curl = noise2D.curlNoise(currPoint.x, currPoint.y);
                xVal = curl.getX(); yVal = curl.getY();
                weight = mix(xVal, yVal, _mix);
                if (_blend) currPoint.weight = Helpers.clamp(
                        currPoint.weight + Helpers.map(weight, 0, 1, _min, _max) * _opacity, 0.0, 1.0);
                else currPoint.weight = Helpers.map(noise2D.marbleNoise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity;
            }
        }
    }

    public void clover_curlFractal(double _min, double _max, int _iterations, double _mix, boolean _blend, double _opacity) {

        // Apply weights to points based on 2D curl fractal clover noise implementation.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _iterations -> How many iterations the noise generator goes through.
        // _mix -> Curl is a Vector2, _mix determines bias when reducing to one double for weight.
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        CloverNoise.Noise2D noise2D = new CloverNoise.Noise2D();
        double xVal, yVal, weight;

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                CloverNoise.Vector2 curl = noise2D.fractalCurlNoise(currPoint.x, currPoint.y, _iterations);
                xVal = curl.getX(); yVal = curl.getY();
                weight = mix(xVal, yVal, _mix);
                if (_blend) currPoint.weight = Helpers.clamp(
                        currPoint.weight + Helpers.map(weight, 0, 1, _min, _max) * _opacity, 0.0, 1.0);
                else currPoint.weight = Helpers.map(noise2D.marbleNoise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity;
            }
        }
    }

    // * =========== PRIVATE HELPERS ============== * //

    private int check_quad(Grid_Point _pg) {

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

    private double grid_approx_dist(Grid_Point _pg1, Grid_Point _pg2) {

        // Returns a non-sqrt based distance between two points in Grid.
        // Use when a precise distance is not necessary.
        // Distance is calculated using grid indices and not actual x y values.
        // Where:
        // _pg1 -> First point
        // _pg2 -> Second point

        return Math.pow(_pg1.gX - _pg2.gX, 2) + Math.pow(_pg1.gY - _pg2.gY, 2);

    }

    private double grid_exact_dist(Grid_Point _pg1, Grid_Point _pg2) {

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

    private double mix(double a, double b, double bias) {

        // Mixes two values according to a bias.
        // Where:
        // a, b -> Numbers to mix
        // bias -> 1: a bias, 0: b bias

        bias = Helpers.clamp(bias, 0, 1);

        return (a * bias) + (b * (1 - bias));

    }


}