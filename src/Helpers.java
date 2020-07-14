import processing.core.PApplet;

import java.util.ArrayList;

import static java.lang.Math.sin;

// TODO: Fix likely horrible precision errors here.

public class Helpers {

    public static int weightToRGB(double _in) {

        // Maps alpha values (1.00 - 0.00) to std RGB vals (255-0)
        // Where:
        // _in -> alpha value to map
        // _out -> resulting RGB scale value

        float f_in = (float)_in;
        return (int) PApplet.map(f_in, 0, 1, 0, 255);

    }

    public static Tuple2<Integer, Integer> plotCircle(int _x, int _centerX, int _centerY, float _r) {

        // Returns a 2-value Tuple containing positive and negative Y values of a desired circle.
        // NOTE: Avoid use if possible (function uses sqrt).
        // Where:
        // _x -> x value to apply f(x)
        // _centerX, _centerY -> center of desired circle
        // _r -> radius of desired circle

        int pos_y = plotCircleTop(_x, _centerX, _centerY, _r);
        int neg_y = -pos_y + (_centerY * 2);

        return new Tuple2<Integer, Integer>(
                pos_y,
                neg_y
        );
    }

    public static int plotCircleTop(int _x, int _centerX, int _centerY, float _r) {

        // Returns the positive Y value corresponding to a given X of a desired circle.
        // NOTE: Avoid use if possible (function uses sqrt).
        // Where:
        // _x -> x value to apply f(x)
        // _centerX, _centerY -> center of desired circle
        // _r -> radius of desired circle

        return (int) PApplet.sqrt(PApplet.sq(_r) - PApplet.sq(_x-_centerX)) + _centerY;

    }

    public static int plotCircleBottom(int _x, int _centerX, int _centerY, float _r) {

        // Returns the negative Y value corresponding to a given X of a desired circle.
        // NOTE: Avoid use if possible (function uses sqrt).
        // Where:
        // _x -> x value to apply f(x)
        // _centerX, _centerY -> center of desired circle
        // _r -> radius of desired circle

        return -(int) PApplet.sqrt(PApplet.sq(_r) - PApplet.sq(_x-_centerX)) + _centerY;

    }

    public static boolean checkBounds(int _col0, int _row0, int _col1, int _row1, Point_Grid _pg) {

        // Checks whether the given row and column values exceed the number of columns and rows in a POINT_GRID
        // Returns True if it does not
        // Where:
        // _col0, _row0 -> col and row values
        // _col1, _row1 -> second col and row values
        // _pg -> Point_Grid against which to check

        return checkRowBounds(_row0, _pg) && checkRowBounds(_row1, _pg) && checkColBounds(_col0, _pg) && checkColBounds(_col1, _pg);
    }

    public static boolean checkBounds(int _col, int _row, Point_Grid _pg) {

        // Checks whether the given row and column values exceed the number of columns and rows in a POINT_GRID
        // Returns True if it does not
        // Where:
        // _col0, _row0 -> col and row values
        // _pg -> Point_Grid against which to check

        return checkColBounds(_col, _pg) && checkRowBounds(_row, _pg);

    }

    public static boolean checkRowBounds(int _row, Point_Grid _pg) {

        // Checks whether the given row exceeds the bounds of the given POINT_GRID
        // Returns True if it does not
        // Where:
        // _row -> row value to check
        // _pg -> Point_Grid against which to check

        return _row > -1 && _row < _pg.y;

    }

    public static boolean checkColBounds(int _col, Point_Grid _pg) {

        // Checks whether the given column exceeds the bounds of the given POINT_GRID
        // Returns True if it does not
        // Where:
        // _col -> col value to check
        // _pg -> Point_Grid against which to check

        return _col > -1 && _col < _pg.x;
    }

    public static double clamp(double val, double min, double max) {


        // Returns a value clamped to a given range.
        // Where:
        // val -> value to clamp
        // min, max -> clamp range

        return Math.max(min, Math.min(max, val));

    }

    public static int clamp(int val, int min, int max) {

        // Returns a value clamped to a given range.
        // Where:
        // val -> value to clamp
        // min, max -> clamp range

        return Math.max(min, Math.min(max, val));
    }

    public static float easeInOutCubic (float _x, float _begin, float _change, float _duration) {

        // Maps an x value to a y value using an in-out-cubic easing function
        // Adapted from Robert Penner's Easing Functions
        // Where:
        // _x -> x value to map
        // _begin -> beginning value
        // _change -> change in value
        // _duration -> duration or extent of function

        if ((_x/=_duration/2) < 1) return _change/2*_x*_x*_x + _begin;

        return _change/2*((_x-=2)*_x*_x + 2) + _begin;

    }

    public static ArrayList<ArrayList<Grid_Point>> clonePoints(Point_Grid _pg) {

        // Deep clones points from a given Point_Grid into a new ArrayList.
        // Used to avoid shallow copies.
        // Where:
        // _pg -> Point_Grid to copy from

        ArrayList<ArrayList<Grid_Point>> parent = new ArrayList<ArrayList<Grid_Point>>(_pg.y);
        Grid_Point currPoint;

        for (int x = 0; x < _pg.x; x++) {
            parent.add(new ArrayList<Grid_Point>(_pg.y));
            for (int y = 0; y < _pg.y; y++) {
                currPoint = _pg.points.get(x).get(y);
                parent.get(x).add(new Grid_Point(currPoint));
            }
        }

        return parent;

    }

    public static ArrayList<Grid_Point> clonePoints(ArrayList<Grid_Point> _list) {

        // Deep clones Grid_Points from one ArrayList to another.
        // Used to get around some issues regarding unwanted object references.
        // Where:
        // _list -> ArrayList<Grid_Point> to copy

        ArrayList<Grid_Point> result = new ArrayList<Grid_Point>(_list.size());
        for (Grid_Point currPoint : _list) {
            result.add(new Grid_Point(currPoint));
        }
        return result;
    }

    public static Point_Grid addGridWeights(Point_Grid _pg1, Point_Grid _pg2) {

        // Adds point weights, returns a new Point_Grid
        // Where:
        // _pg1 -> First Point_Grid to add
        // _pg2 -> Second Point_Grid to add

        int maxCol = Math.max(_pg1.x, _pg2.x);
        int maxRow = Math.max(_pg1.y, _pg2.y);
        int maxSpacingX = Math.max(_pg1.sX, _pg2.sY);
        int maxSpacingY = Math.max(_pg1.sY, _pg2.sY);
        float maxXCenter = Math.max(_pg1.c.x, _pg2.c.x);
        float maxYCenter = Math.max(_pg1.c.y, _pg2.c.y);

        Point_Grid result = new Point_Grid(maxCol, maxRow, new Point(maxXCenter, maxYCenter), maxSpacingX, maxSpacingY);
        result = new Point_Grid(result, false);

        for (ArrayList<Grid_Point> columns : _pg1.points) {
            for (Grid_Point currPoint : columns) {
                result.points.get(currPoint.gX).get(currPoint.gY).weight = clamp(result.points.get(currPoint.gX).get(currPoint.gY).weight + currPoint.weight, 0, 1);
            }
        }

        for (ArrayList<Grid_Point> columns : _pg2.points) {
            for (Grid_Point currPoint : columns) {
                result.points.get(currPoint.gX).get(currPoint.gY).weight = clamp(result.points.get(currPoint.gX).get(currPoint.gY).weight + currPoint.weight, 0, 1);
            }
        }

        return result;

    }

    public static Point_Grid subtractGridWeights(Point_Grid _pg1, Point_Grid _pg2) {

        // Subtracts point weights, returns a new Point_Grid
        // Where:
        // _pg1 -> Point_Grid to subtract from
        // _pg2 -> Point_Grid to subtract with

        Grid_Point currPoint;

        Point_Grid result = new Point_Grid(_pg1);

        for (ArrayList<Grid_Point> grid_points : _pg2.points) {
            for (Grid_Point grid_point : grid_points) {
                currPoint = grid_point;
                if (checkColBounds(currPoint.gX, _pg1) && checkRowBounds(currPoint.gY, _pg1)) {
                    result.points.get(currPoint.gX).get(currPoint.gY).weight = clamp(result.points.get(currPoint.gX).get(currPoint.gY).weight - currPoint.weight, 0, 1);
                }
            }
        }

        return result;

    }

    public static int rgbToLuma(int _r, int _g, int _b) {

        // Approximate Luma value from RGB values, rough approximation (takes values of 0-255 and returns same range).
        // Where:
        // _r -> red channel
        // _g -> green channel
        // _b -> blue channel

        return (_r+_r+_r+_b+_g+_g+_g+_g)>>3;

    }

    public static double sinMap(double _x, double _freq, double _shift) {

        // A sin function, returns a value between 1 and -1.
        // Where:
        // _x -> input value to map
        // _amp -> amplitude of function
        // _freq -> frequency of function
        // _shift -> x-axis shift of function

        return sin((float)(_freq*_x - _freq*_shift));

    }
}
