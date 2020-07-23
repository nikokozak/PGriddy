import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Text {

    public static Point_List get_sentence(String _sentence, int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        _sentence = _sentence.toLowerCase();
        char[] characters = _sentence.toCharArray();
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();
        int spacing = size_calc(_size) + 1;
        int currX = _xOrigin;

        for (char c : characters) {
            result.addAll(get_letter(c, currX, _yOrigin, _size, _pg));
            currX += spacing;
        }

        return new Point_List(result);

    }

    private static LinkedHashSet<Grid_Point> get_letter(char _char, int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a point collection corresponding to the character in question.
        // Where:
        // _char -> character to return in point form
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        LinkedHashSet<Grid_Point> result;

        switch(_char) {
            case 'a' -> result = A(_xOrigin, _yOrigin, _size, _pg);
            case 'b' -> result = B(_xOrigin, _yOrigin, _size, _pg);
            case 'c' -> result = C(_xOrigin, _yOrigin, _size, _pg);
            case 'd' -> result = D(_xOrigin, _yOrigin, _size, _pg);
            case 'e' -> result = E(_xOrigin, _yOrigin, _size, _pg);
            case 'f' -> result = F(_xOrigin, _yOrigin, _size, _pg);
            case 'g' -> result = G(_xOrigin, _yOrigin, _size, _pg);
            case 'h' -> result = H(_xOrigin, _yOrigin, _size, _pg);
            case 'i' -> result = I(_xOrigin, _yOrigin, _size, _pg);
            case 'j' -> result = J(_xOrigin, _yOrigin, _size, _pg);
            case 'k' -> result = K(_xOrigin, _yOrigin, _size, _pg);
            case 'l' -> result = L(_xOrigin, _yOrigin, _size, _pg);
            case 'm' -> result = M(_xOrigin, _yOrigin, _size, _pg);
            case 'n' -> result = N(_xOrigin, _yOrigin, _size, _pg);
            case 'o' -> result = O(_xOrigin, _yOrigin, _size, _pg);
            case 'p' -> result = P(_xOrigin, _yOrigin, _size, _pg);
            case 'q' -> result = Q(_xOrigin, _yOrigin, _size, _pg);
            case 'r' -> result = R(_xOrigin, _yOrigin, _size, _pg);
            case 's' -> result = S(_xOrigin, _yOrigin, _size, _pg);
            case 't' -> result = T(_xOrigin, _yOrigin, _size, _pg);
            case 'u' -> result = U(_xOrigin, _yOrigin, _size, _pg);
            case 'v' -> result = V(_xOrigin, _yOrigin, _size, _pg);
            case 'w' -> result = W(_xOrigin, _yOrigin, _size, _pg);
            case 'x' -> result = X(_xOrigin, _yOrigin, _size, _pg);
            case 'y' -> result = Y(_xOrigin, _yOrigin, _size, _pg);
            case 'z' -> result = Z(_xOrigin, _yOrigin, _size, _pg);
            default -> result = null;
        }

        return result;
    }

    public static LinkedHashSet<Grid_Point> A(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "A".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + mid - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, i));
        }

        return result;

    }

    public static LinkedHashSet<Grid_Point> B(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "B".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + mid - 1));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + _size - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
        }
        for (int i = _yOrigin + 1; i < _yOrigin + mid - 1; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, i));
        }
        for (int i = _yOrigin + mid; i < _yOrigin + _size - 1; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, i));
        }
        return result;

    }

    public static LinkedHashSet<Grid_Point> C(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "C".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin + 1; i < _xOrigin + _size; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + _size - 1));
        }
        for (int j = _yOrigin + 1; j < _yOrigin + _size - 1; j++) {
            if (j < _pg.y) result.add(_pg.get_point(_xOrigin, j));
        }

        return result;

    }

    public static LinkedHashSet<Grid_Point> D(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "D".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + _size - 1));
        }
        for (int j = _yOrigin; j < _yOrigin + _size; j++) {
            if (j < _pg.y) result.add(_pg.get_point(_xOrigin, j));
        }
        for (int j = _yOrigin + 1; j < _yOrigin + _size - 1; j++) {
            if (j < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, j));
        }
        return result;

    }

    public static LinkedHashSet<Grid_Point> E(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "E".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + mid - 1));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + _size - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
        }
        return result;

    }

    public static LinkedHashSet<Grid_Point> F(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "F".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + mid - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
        }
        return result;

    }

    public static LinkedHashSet<Grid_Point> G(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "G".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + _size - 1));
        }
        for (int i = _xOrigin + mid - 1; i < _xOrigin + _size; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + mid - 1));
        }
        for (int i = _yOrigin + 1; i < _yOrigin + _size - 1; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
        }
        for (int i = _yOrigin + mid - 1; i < _yOrigin + _size; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, i));
        }
        return result;

    }

    public static LinkedHashSet<Grid_Point> H(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "H".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + mid - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, i));
        }

        return result;

    }

    public static LinkedHashSet<Grid_Point> I(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "I".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + _size - 1));
        }
        for (int j = _yOrigin; j < _yOrigin + _size; j++) {
            if (j < _pg.y) result.add(_pg.get_point(_xOrigin + mid - 1, j));
        }

        return result;

    }

    public static LinkedHashSet<Grid_Point> J(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "J".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
        }
        for (int i = _xOrigin; i < _xOrigin + mid; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + _size - 1));
        }
        for (int j = _yOrigin; j < _yOrigin + _size; j++) {
            if (j < _pg.y) result.add(_pg.get_point(_xOrigin + mid, j));
        }
        for (int j = _yOrigin + mid; j < _yOrigin + _size; j++) {
            if (j < _pg.y) result.add(_pg.get_point(_xOrigin, j));
        }

        return result;

    }

    public static LinkedHashSet<Grid_Point> K(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "K".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        int diag_factor = 0;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        Point_List line = _pg.get_line(_xOrigin, _yOrigin + mid - 1, _xOrigin + _size - 1, _yOrigin);
        result.addAll(line.points);
        line = _pg.get_line(_xOrigin, _yOrigin + mid - 1, _xOrigin + _size - 1, _yOrigin + _size - 1);
        result.addAll(line.points);

        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
        }

        return result;

    }

    public static LinkedHashSet<Grid_Point> L(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "L".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + _size - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
        }
        return result;

    }

    public static LinkedHashSet<Grid_Point> M(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "M".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        Point_List line = _pg.get_line(_xOrigin, _yOrigin, _xOrigin + mid - 1, _yOrigin + mid - 1);
        result.addAll(line.points);
        line = _pg.get_line(_xOrigin + _size - 1, _yOrigin, _xOrigin + mid - 1, _yOrigin + mid - 1);
        result.addAll(line.points);

        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, i));
        }

        return result;

    }

    public static LinkedHashSet<Grid_Point> N(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "N".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        Point_List line = _pg.get_line(_xOrigin, _yOrigin, _xOrigin + _size - 1, _yOrigin + _size - 1);
        result.addAll(line.points);

        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, i));
        }

        return result;

    }

    public static LinkedHashSet<Grid_Point> O(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "O".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin + 1; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + _size - 1));
        }
        for (int j = _yOrigin + 1; j < _yOrigin + _size - 1; j++) {
            if (j < _pg.y) result.add(_pg.get_point(_xOrigin, j));
            if (j < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, j));
        }
        return result;

    }

    public static LinkedHashSet<Grid_Point> P(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "P".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + mid - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
        }
        for (int i = _yOrigin + 1; i < _yOrigin + mid - 1; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, i));
        }
        return result;

    }

    public static LinkedHashSet<Grid_Point> Q(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "Q".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        Point_List line = _pg.get_line(_xOrigin + mid - 1, _yOrigin + _size - 1, _xOrigin + _size - 1, _yOrigin + mid);
        result.addAll(line.points);

        for (int i = _xOrigin + 1; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
        }
        for (int i = _xOrigin + 1; i < _xOrigin + mid - 1; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + _size - 1));
        }
        for (int j = _yOrigin + 1; j < _yOrigin + _size - 1; j++) {
            if (j < _pg.y) result.add(_pg.get_point(_xOrigin, j));
        }
        for (int j = _yOrigin + 1; j < _yOrigin + mid; j++) {
            if (j < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, j));
        }
        for (int j = _yOrigin + mid; j < _yOrigin + _size ; j++) {
            if (j < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, j));
        }

        return result;

    }

    public static LinkedHashSet<Grid_Point> R(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "R".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        Point_List line = _pg.get_line(_xOrigin, _yOrigin + mid - 1, _xOrigin + _size - 1, _yOrigin + _size - 1);
        result.addAll(line.points);

        for (int i = _xOrigin; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + mid - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
        }
        for (int i = _yOrigin + 1; i < _yOrigin + mid - 1; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, i));
        }
        return result;

    }

    public static LinkedHashSet<Grid_Point> S(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "S".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin + 1; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + mid - 1));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + _size - 1));
        }
        for (int i = _yOrigin + 1; i < _yOrigin + mid - 1; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
        }
        for (int i = _yOrigin + mid; i < _yOrigin + _size - 1; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, i));
        }
        return result;

    }

    public static LinkedHashSet<Grid_Point> T(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "T".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
        }
        for (int j = _yOrigin; j < _yOrigin + _size; j++) {
            if (j < _pg.y) result.add(_pg.get_point(mid - 1, j));
        }

        return result;

    }

    public static LinkedHashSet<Grid_Point> U(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "U".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin + 1; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + _size - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size - 1; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, i));
        }

        return result;

    }

    public static LinkedHashSet<Grid_Point> V(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "V".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        Point_List line = _pg.get_line(_xOrigin, _yOrigin, _xOrigin + mid - 1, _yOrigin + _size - 1);
        result.addAll(line.points);
        line = _pg.get_line(_xOrigin + _size - 1, _yOrigin, _xOrigin + mid - 1, _yOrigin + _size - 1);
        result.addAll(line.points);

        return result;

    }

    public static LinkedHashSet<Grid_Point> W(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "W".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;

        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        Point_List line = _pg.get_line(_xOrigin, _yOrigin + _size - 1, _xOrigin + mid - 1, _yOrigin + mid - 1);
        result.addAll(line.points);
        line = _pg.get_line(_xOrigin + _size - 1, _yOrigin + _size - 1, _xOrigin + mid - 1, _yOrigin + mid - 1);
        result.addAll(line.points);

        for (int i = _yOrigin; i < _yOrigin + _size - 1; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin, i));
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin + _size - 1, i));
        }

        return result;

    }

    public static LinkedHashSet<Grid_Point> X(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "X".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        Point_List line = _pg.get_line(_xOrigin, _yOrigin, _xOrigin + _size - 1, _yOrigin + _size - 1);
        result.addAll(line.points);
        line = _pg.get_line(_xOrigin + _size - 1, _yOrigin, _xOrigin, _yOrigin + _size - 1);
        result.addAll(line.points);

        return result;

    }

    public static LinkedHashSet<Grid_Point> Y(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "Y".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        Point_List line = _pg.get_line(_xOrigin, _yOrigin, _xOrigin + mid - 1, _yOrigin + mid - 1);
        result.addAll(line.points);
        line = _pg.get_line(_xOrigin + _size - 1, _yOrigin, _xOrigin + mid - 1, _yOrigin + mid - 1);
        result.addAll(line.points);

        for (int i = _yOrigin + mid; i < _yOrigin + _size - 1; i++) {
            if (i < _pg.y) result.add(_pg.get_point(_xOrigin + mid - 1, i));
        }

        return result;

    }

    public static LinkedHashSet<Grid_Point> Z(int _xOrigin, int _yOrigin, int _size, Point_Grid _pg) {

        // Returns a collection of points conforming to the shape "Z".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = size_calc(_size);
        LinkedHashSet<Grid_Point> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin));
            if (i < _pg.x) result.add(_pg.get_point(i, _yOrigin + _size - 1));
        }

        Point_List line = _pg.get_line(_xOrigin, _yOrigin + _size - 1, _xOrigin + _size - 1, _yOrigin);
        result.addAll(line.points);

        return result;

    }

    private static int size_calc(int _size) {
        // Returns a size starting at 5 and progressing according to odd numbers.

        return 5 + 2*(_size - 1);

    }

}
