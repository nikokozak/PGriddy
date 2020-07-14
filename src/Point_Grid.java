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

    public void weight_reset() {
        // Sets all Points to weight 1.0.

        for (ArrayList<Grid_Point> column : this.points) {
            for (Grid_Point currPoint : column) {
                currPoint.weight = 1.0;
            }
        }
    }


    // * =========== GRADIENT APPLICATORS ============== * //

    public void applyLinRadGradient(int _col, int _row, int _rad, double _init_weight, boolean _inverse, boolean _blend, double _opacity) {
        // TODO : TEST

        // Modifies weights of Grid_Points in a given Point_Grid according to a radial gradient, returns a new Point_Grid
        // uses modified rasterizing algorithm by Alois Zingl (http://members.chello.at/~easyfilter/Bresenham.pdf)
        // Where:
        // _col, _row -> origin of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _init_weight -> initial weight value for gradient
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew

        int curr_rad = 0; int inner_rad = 0;
        double decay = _init_weight / _rad;
        double curr_weight = _inverse ? Helpers.clamp(1 - _init_weight, 0.0, 1.0) : _init_weight;

        if (Helpers.checkBounds(_col, _row, this)) {
            if (_blend) this.points.get(_col).get(_row).weight = Helpers.clamp(this.points.get(_col).get(_row).weight + curr_weight * _opacity, 0.0, 1.0);  // Set first point (algo skips it)
            else this.points.get(_col).get(_row).weight = curr_weight * _opacity;
        }

        while (curr_rad <= _rad) {
            int x = -curr_rad;
            int y = 0;
            int err = 2-2*curr_rad;
            PApplet.print("Curr Rad: ", curr_rad, " Curr _rad: ", _rad);

            while (x < 0) {
                if (Helpers.checkBounds(_col - x, _row + y, this)) {
                    if (_blend) {
                        this.points.get(_col - x).get(_row + y).weight = Helpers.clamp(this.points.get(_col - x).get(_row + y).weight + curr_weight*_opacity, 0.0, 1.0);
                    }
                    else this.points.get(_col - x).get(_row + y).weight = curr_weight*_opacity;
                }
                if (Helpers.checkBounds(_col - y, _row - x, this)) {
                    if (_blend) {
                        this.points.get(_col-y).get(_row-x).weight = Helpers.clamp(this.points.get(_col-y).get(_row-x).weight + curr_weight*_opacity, 0.0, 1.0);
                    }
                    else this.points.get(_col-y).get(_row-x).weight = curr_weight*_opacity;
                }
                if (Helpers.checkBounds(_col + x, _row - y, this)) {
                    if (_blend) {
                        this.points.get(_col+x).get(_row-y).weight = Helpers.clamp(this.points.get(_col+x).get(_row-y).weight + curr_weight*_opacity, 0.0, 1.0);
                    }
                    else this.points.get(_col+x).get(_row-y).weight = curr_weight*_opacity;
                }
                if (Helpers.checkBounds(_col + y, _row + x, this)) {
                    if (_blend) {
                        this.points.get(_col+y).get(_row+x).weight = Helpers.clamp(this.points.get(_col+y).get(_row+x).weight + curr_weight*_opacity, 0.0, 1.0);
                    }
                    else this.points.get(_col+y).get(_row+x).weight = curr_weight*_opacity;
                }
                inner_rad = err;
                if (inner_rad <= 0) {
                    y += 1;
                    err += 2*y + 1;
                }
                if (inner_rad > 0) {
                    x += 1;
                    err += 2*x + 1;
                }
            }

            curr_rad += 1;
            curr_weight = Helpers.clamp(_inverse ? curr_weight + decay : curr_weight - decay, 0.0, 1.0);

        }
    }

    public void applyLinRadGradient_Slow (int _col, int _row, int _r, double _init_decay, double _sample_rate, boolean _inverse, boolean _blend, double _opacity) {
        // TODO: TEST

        // Modifies weights of Grid_Points in Point_Grid according to a radial gradient, returns a new Point_Grid
        // NOTE: This looks nicer, but is far more computationally expensive than applyRadialGradient given that it uses sqrt()
        // in the underlying circle-plotting algo. Avoid using if possible.
        // Where:
        // _col, _row -> origin of gradient
        // _r -> radius of gradient (i.e. extent of gradient effect)
        // _init_decay -> initial weight value of gradient
        // _sample_rate -> radius increment per cycle (think of this as sampling density, if there are empty points in gradient then reduce this number, NOT TOO FAR THOUGH).
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _opacity -> opacity of gradient applied to Grid.

        float curr_rad = 0;
        int init_x = _col - _r;
        int fin_x = _col + _r;
        int curr_x = init_x;
        double curr_weight = _init_decay;
        double decay_factor = _r / _sample_rate;
        double decay = _init_decay / decay_factor;
        Tuple2<Integer, Integer> yVal;

        while (curr_rad <= _r) {

            while (curr_x <= fin_x) {

                yVal = Helpers.plotCircle(curr_x, _col, _row, curr_rad);

                if (Helpers.checkBounds(curr_x, yVal.a, this)) {
                    if (_blend) this.points.get(curr_x).get(yVal.a).weight = Helpers.clamp(this.points.get(curr_x).get(yVal.a).weight + curr_weight * _opacity, 0.0, 1.0);
                    else this.points.get(curr_x).get(yVal.a).weight = curr_weight * _opacity;
                }
                if (Helpers.checkBounds(curr_x, yVal.b, this)) {
                    if (_blend) this.points.get(curr_x).get(yVal.b).weight = Helpers.clamp(this.points.get(curr_x).get(yVal.b).weight + curr_weight * _opacity, 0.0, 1.0);
                    else this.points.get(curr_x).get(yVal.b).weight = curr_weight * _opacity;
                }

                curr_x++;

            }

            curr_rad += _sample_rate;
            curr_x = init_x;
            curr_weight = Helpers.clamp(_inverse ? curr_weight + decay : curr_weight - decay, 0.0, 1.0);

        }

    }

    public void applySmoothRadGradient(int _col, int _row, int _rad, double _init_weight, boolean _inverse, boolean _blend, double _opacity) {
        // TODO: TEST

        // Modifies weights of Grid_Points in a given Point_Grid according to a radial gradient, using an in-out-easing function. Returns a new Point_Grid
        // uses modified rasterizing algorithm by Alois Zingl (http://members.chello.at/~easyfilter/Bresenham.pdf)
        // Where:
        // _col, _row -> origin of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _init_weight -> initial weight value for gradient
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _pg -> Point_Grid to affect

        int curr_rad = 0; int inner_rad = 0;
        double curr_weight = _init_weight;

        if (Helpers.checkBounds(_col, _row, this)) {
            this.points.get(_col).get(_row).weight = curr_weight * _opacity;  // Set first point (algo skips it)
        }

        while (curr_rad <= _rad) {
            int x = -curr_rad;
            int y = 0;
            int err = 2-2*curr_rad;

            while (x < 0) {
                if (Helpers.checkBounds(_col - x, _row + y, this)) {
                    if (_blend) this.points.get(_col-x).get(_row+y).weight = Helpers.clamp(this.points.get(_col-x).get(_row+y).weight + curr_weight * _opacity, 0.0, 1.0);
                    else this.points.get(_col-x).get(_row+y).weight = curr_weight * _opacity;
                }
                if (Helpers.checkBounds(_col - y, _row - x, this)) {
                    if (_blend) this.points.get(_col-y).get(_row-x).weight = Helpers.clamp(this.points.get(_col-y).get(_row-x).weight + curr_weight * _opacity, 0.0, 1.0);
                    else this.points.get(_col-y).get(_row-x).weight = curr_weight * _opacity;
                }
                if (Helpers.checkBounds(_col + x, _row - y, this)) {
                    if (_blend) this.points.get(_col+x).get(_row-y).weight = Helpers.clamp(this.points.get(_col+x).get(_row-y).weight + curr_weight * _opacity, 0.0, 1.0);
                    else this.points.get(_col+x).get(_row-y).weight = curr_weight * _opacity;
                }
                if (Helpers.checkBounds(_col + y, _row + x, this)) {
                    if (_blend) this.points.get(_col+y).get(_row+x).weight = Helpers.clamp(this.points.get(_col+y).get(_row+x).weight + curr_weight * _opacity, 0.0, 1.0);
                    else this.points.get(_col+y).get(_row+x).weight = curr_weight * _opacity;
                }
                inner_rad = err;
                if (inner_rad <= 0) {
                    y += 1;
                    err += 2*y + 1;
                }
                if (inner_rad > 0) {
                    x += 1;
                    err += 2*x + 1;
                }
            }

            curr_rad += 1;
            curr_weight = _inverse ? Helpers.easeInOutCubic((float)curr_rad, 0, (float)_init_weight, (float)_rad) : Helpers.easeInOutCubic((float)curr_rad, (float)_init_weight, -(float)_init_weight, (float)_rad);
            curr_weight = Helpers.clamp(curr_weight, 0.0, 1.0);

        }

    }

    public void applySmoothRadGradient_Slow(int _col, int _row, int _r, double _init_weight, double _sample_rate, boolean _inverse, boolean _blend, double _opacity) {
        // TODO: TEST

        // Modifies weights of Grid_Points in a given Point_Grid according to a radial gradient, using an in-out easing function, returns a new Point_Grid
        // NOTE: This looks nicer, but is far more computationally expensive than applyRadialGradient given that it uses sqrt()
        // in the underlying circle-plotting algo. Avoid using if possible.
        // Where
        // _col, _row -> origin of gradient
        // _r -> radius of gradient (i.e. extent of gradient effect)
        // _init_weight -> initial weight value of gradient
        // _sample_rate -> radius increment per cycle (think of this as sampling density, if there are empty points in gradient then reduce this number, NOT TOO FAR THOUGH).
        // _inverse -> whether to invert the gradient
        // _blend -> whether to allow blending with previous weights (otherwise gradient overrides previous weights)
        // _pg -> Point_Grid to affect.

        float curr_rad = 0;
        int init_x = _col - _r;
        int fin_x = _col + _r;
        int curr_x = init_x;
        double curr_weight = _init_weight;
        Tuple2<Integer, Integer> yVal;

        while (curr_rad <= _r) {

            while (curr_x <= fin_x) {

                yVal = Helpers.plotCircle(curr_x, _col, _row, curr_rad);

                if (Helpers.checkBounds(curr_x, yVal.a, this)) {
                    if (_blend) this.points.get(curr_x).get(yVal.a).weight = Helpers.clamp(this.points.get(curr_x).get(yVal.a).weight + curr_weight * _opacity, 0.0, 1.0);
                    else this.points.get(curr_x).get(yVal.a).weight = curr_weight * _opacity;
                }
                if (Helpers.checkBounds(curr_x, yVal.b, this)) {
                    if (_blend) this.points.get(curr_x).get(yVal.b).weight = Helpers.clamp(this.points.get(curr_x).get(yVal.b).weight + curr_weight * _opacity, 0.0, 1.0);
                    else this.points.get(curr_x).get(yVal.b).weight = curr_weight * _opacity;
                }

                curr_x++;

            }

            curr_rad += _sample_rate;
            curr_x = init_x;
            curr_weight = _inverse ? Helpers.easeInOutCubic((float)curr_rad, 0, (float)_init_weight, (float)_r) : Helpers.easeInOutCubic((float)curr_rad, (float)_init_weight, -(float)_init_weight, (float)_r);
            curr_weight = Helpers.clamp(curr_weight, 0.0, 1.0);

        }

    }

    public void applySinRadGradient(int _col, int _row, int _rad, double _init_weight, double _freq, double _shift, boolean _inverse, boolean _blend, double _opacity) {
        // TODO : TEST

        // Modifies weights of Grid_Points in a given Point_Grid according to a radial gradient, using an in-out-easing function. Returns a new Point_Grid
        // uses modified rasterizing algorithm by Alois Zingl (http://members.chello.at/~easyfilter/Bresenham.pdf)
        // Where:
        // _col, _row -> origin of gradient
        // _rad -> radius of gradient (i.e. extent of gradient effect)
        // _init_weight -> initial weight value for gradient
        // _inverse -> whether to invert the gradient
        // _blend -> whether to add the gradient onto the previous Point_Grid or start anew
        // _pg -> Point_Grid to affect

        int curr_rad = 0; int inner_rad = 0;
        double curr_weight = _init_weight;

        while (curr_rad <= _rad) {
            int x = -curr_rad;
            int y = 0;
            int err = 2-2*curr_rad;

            while (x < 0) {
                if (Helpers.checkBounds(_col - x, _row + y, this)) {
                    if (_blend) this.points.get(_col-x).get(_row+y).weight = Helpers.clamp(this.points.get(_col-x).get(_row+y).weight + curr_weight * _opacity, 0.0, 1.0);
                    else this.points.get(_col-x).get(_row+y).weight = curr_weight * _opacity;
                }
                if (Helpers.checkBounds(_col - y, _row - x, this)) {
                    if (_blend) this.points.get(_col-y).get(_row-x).weight = Helpers.clamp(this.points.get(_col-y).get(_row-x).weight + curr_weight * _opacity, 0.0, 1.0);
                    else this.points.get(_col-y).get(_row-x).weight = curr_weight * _opacity;
                }
                if (Helpers.checkBounds(_col + x, _row - y, this)) {
                    if (_blend) this.points.get(_col+x).get(_row-y).weight = Helpers.clamp(this.points.get(_col+x).get(_row-y).weight + curr_weight * _opacity, 0.0, 1.0);
                    else this.points.get(_col+x).get(_row-y).weight = curr_weight * _opacity;
                }
                if (Helpers.checkBounds(_col + y, _row + x, this)) {
                    if (_blend) this.points.get(_col+y).get(_row+x).weight = Helpers.clamp(this.points.get(_col+y).get(_row+x).weight + curr_weight * _opacity, 0.0, 1.0);
                    else this.points.get(_col+y).get(_row+x).weight = curr_weight * _opacity;
                }
                inner_rad = err;
                if (inner_rad <= 0) {
                    y += 1;
                    err += 2*y + 1;
                }
                if (inner_rad > 0) {
                    x += 1;
                    err += 2*x + 1;
                }
            }

            curr_rad += 1;
            _shift = _inverse ? _shift + (Math.PI) : _shift;
            curr_weight = Helpers.sinMap((double)curr_rad, _freq, _shift);
            curr_weight = Helpers.clamp(curr_weight, 0.0, 1.0);
        }

    }

    public void applySinRadGradient_Slow(int _col, int _row, int _r, double _init_weight, double _sample_rate, double _freq, double _shift, boolean _inverse, boolean _blend, double _opacity) {
        // TODO : TEST

        // Modifies weights of Grid_Points in a given Point_Grid according to a radial gradient, using an in-out easing function, returns a new Point_Grid
        // NOTE: This looks nicer, but is far more computationally expensive than applyRadialGradient given that it uses sqrt()
        // in the underlying circle-plotting algo. Avoid using if possible.
        // Where:
        // _col, _row -> origin of gradient
        // _r -> radius of gradient (i.e. extent of gradient effect)
        // _init_weight -> initial weight value of gradient
        // _sample_rate -> radius increment per cycle (think of this as sampling density, if there are empty points in gradient then reduce this number, NOT TOO FAR THOUGH).
        // _inverse -> whether to invert the gradient
        // _blend -> whether to allow blending with previous weights (otherwise gradient overrides previous weights)
        // _pg -> Point_Grid to affect.

        float curr_rad = 0;
        int init_x = _col - _r;
        int fin_x = _col + _r;
        int curr_x = init_x;
        double curr_weight = _init_weight;
        Tuple2<Integer, Integer> yVal;

        while (curr_rad <= _r) {

            while (curr_x <= fin_x) {

                yVal = Helpers.plotCircle(curr_x, _col, _row, curr_rad);

                if (Helpers.checkColBounds(curr_x, this) && Helpers.checkRowBounds(yVal.a, this)) {
                    if (_blend) this.points.get(curr_x).get(yVal.a).weight = Helpers.clamp(this.points.get(curr_x).get(yVal.a).weight + curr_weight * _opacity, 0.0, 1.0);
                    else this.points.get(curr_x).get(yVal.a).weight = curr_weight * _opacity;
                }
                if (Helpers.checkColBounds(curr_x, this) && Helpers.checkRowBounds(yVal.b, this)) {
                    if (_blend) this.points.get(curr_x).get(yVal.b).weight = Helpers.clamp(this.points.get(curr_x).get(yVal.b).weight + curr_weight * _opacity, 0.0, 1.0);
                    else this.points.get(curr_x).get(yVal.b).weight = curr_weight * _opacity;
                }

                curr_x++;

            }

            curr_rad += _sample_rate;
            curr_x = init_x;
            _shift = _inverse ? _shift + Math.PI : _shift;
            curr_weight = Helpers.sinMap((double)curr_rad, _freq, _shift);
            curr_weight = Helpers.clamp(curr_weight, 0.0, 1.0);

        }

    }

    public void applyPerlin(float _min, float _max, float _time, boolean _blend, double _opacity) {
        // TODO : TEST

        // Apply weights to point in Point_Grid based on Perlin Noise.
        // Perlin positions are taken from Grid_Points in Grid.
        // Where:
        // _min -> Min weight threshold
        // _max -> Max weight threshold
        // _time -> Time (Z-axis) factor for animating Perlin (takes values from 0.0 - 1.0);
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _pg -> Point_Grid to sample from

        for (ArrayList<Grid_Point> columns : this.points) {
            for (Grid_Point currPoint : columns) {
                if (_blend) this.points.get(currPoint.gX).get(currPoint.gY).weight = Helpers.clamp(
                        this.points.get(currPoint.gX).get(currPoint.gY).weight + PApplet.map(Core.processing.noise(currPoint.x, currPoint.y, _time), 0, 1, _min, _max) * _opacity, 0.0, 1.0); // Call Perlin ~
                else this.points.get(currPoint.gX).get(currPoint.gY).weight = PApplet.map(Core.processing.noise(currPoint.x, currPoint.y, _time), 0, 1, _min, _max) * _opacity; // Call Perlin ~
            }
        }

    }

    public void applyRandom(boolean _blend, double _opacity) {
        // TODO : TEST

        // Apply weights to point in Point_Grid based on Perlin Noise.
        // Perlin positions are taken from Grid_Points in Grid.
        // Where:
        // _pg -> Point_Grid to sample from
        // _min -> Min weight threshold
        // _max -> Max weight threshold
        // _time -> Time (Z-axis) factor for animating Perlin (takes values from 0.0 - 1.0);

        for (ArrayList<Grid_Point> columns : this.points) {
            for (Grid_Point currPoint : columns) {
                if (_blend) this.points.get(currPoint.gX).get(currPoint.gY).weight = Helpers.clamp(this.points.get(currPoint.gX).get(currPoint.gY).weight + Core.processing.random(0, 1) * _opacity, 0.0, 1.0);
                else this.points.get(currPoint.gX).get(currPoint.gY).weight = Core.processing.random(0, 1) * _opacity;
            }
        }

    }

    public void applyImage(PImage _img, String _mode, boolean _blend, double _opacity) {
        // TODO: TEST
        // TODO: ADD OFFSETS (TO MOVE IMAGE)

        // Loads an image and applies weights to Grid_Points in Point_Grid
        // based on R, G, B, L (lightness) values or combinations thereof.
        // Where:
        // _file -> filename of image to load
        // _scale -> scale the image to encompass full grid or load image at center of grid (no scale applied)
        // _mode -> any of the following: "r", "g", "b", "l" (luma)
        // _pg -> Point_Grid to apply to.

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

        for (int x_g = 0; x_g < this.x; x_g++) {
            x = sample_padding_X + (x_g * this.sX);
            for (int y_g = 0; y_g < this.y; y_g++) {
                y = sample_padding_Y + (y_g * this.sY);
                currPixel = new_img.pixels[y*new_img.width+x];
                currPoint = this.points.get(x_g).get(y_g);
                r = (currPixel >> 16) & 0xFF;
                g = (currPixel >> 8) & 0xFF;
                b = currPixel & 0xFF;
                switch (_mode) {
                    case ("r") -> {
                        if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + PApplet.map((float) r, 0, 255, 0, 1) * _opacity, 0.0, 1.0);
                        else currPoint.weight = PApplet.map((float) r, 0, 255, 0, 1) * _opacity;
                    }
                    case ("g") -> {
                        if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + PApplet.map((float) g, 0, 255, 0, 1) * _opacity, 0.0, 1.0);
                        else currPoint.weight = PApplet.map((float) g, 0, 255, 0, 1) * _opacity;
                    }
                    case ("b") -> {
                        if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + PApplet.map((float) b, 0, 255, 0, 1) * _opacity, 0.0, 1.0);
                        else currPoint.weight = PApplet.map((float) b, 0, 255, 0, 1) * _opacity;
                    }
                    case ("l") -> {
                        if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + PApplet.map(Helpers.rgbToLuma(r, g, b), 0, 255, 0, 1) * _opacity, 0.0, 1.0);
                        currPoint.weight = PApplet.map(Helpers.rgbToLuma(r, g, b), 0, 255, 0, 1) * _opacity;
                    }

                }
            }
        }

    }

}