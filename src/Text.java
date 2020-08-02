import java.util.LinkedHashSet;

public class Text {

    public static PointList getSentence(String _sentence, int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        _sentence = _sentence.toLowerCase();
        char[] characters = _sentence.toCharArray();
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();
        int spacing = sizeCalc(_size) + 1;
        GridPoint safe_origin = _pg.getPoint(_xOrigin, _yOrigin); // wraps points around grid if they happen to overflow.
        int currX = safe_origin.gridIndexX();

        for (char c : characters) {
            result.addAll(getLetter(c, currX, safe_origin.gridIndexY(), _size, _pg));
            currX += spacing;
        }

        return new PointList(result);

    }

    private static LinkedHashSet<GridPoint> getLetter(char _char, int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a point collection corresponding to the character in question.
        // Where:
        // _char -> character to return in point form
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        LinkedHashSet<GridPoint> result;

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

    public static LinkedHashSet<GridPoint> A(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "A".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + mid - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, i));
        }

        return result;

    }

    public static LinkedHashSet<GridPoint> B(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "B".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + mid - 1));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + _size - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
        }
        for (int i = _yOrigin + 1; i < _yOrigin + mid - 1; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, i));
        }
        for (int i = _yOrigin + mid; i < _yOrigin + _size - 1; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, i));
        }
        return result;

    }

    public static LinkedHashSet<GridPoint> C(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "C".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin + 1; i < _xOrigin + _size; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + _size - 1));
        }
        for (int j = _yOrigin + 1; j < _yOrigin + _size - 1; j++) {
            if (j < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, j));
        }

        return result;

    }

    public static LinkedHashSet<GridPoint> D(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "D".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + _size - 1));
        }
        for (int j = _yOrigin; j < _yOrigin + _size; j++) {
            if (j < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, j));
        }
        for (int j = _yOrigin + 1; j < _yOrigin + _size - 1; j++) {
            if (j < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, j));
        }
        return result;

    }

    public static LinkedHashSet<GridPoint> E(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "E".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + mid - 1));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + _size - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
        }
        return result;

    }

    public static LinkedHashSet<GridPoint> F(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "F".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + mid - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
        }
        return result;

    }

    public static LinkedHashSet<GridPoint> G(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "G".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + _size - 1));
        }
        for (int i = _xOrigin + mid - 1; i < _xOrigin + _size; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + mid - 1));
        }
        for (int i = _yOrigin + 1; i < _yOrigin + _size - 1; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
        }
        for (int i = _yOrigin + mid - 1; i < _yOrigin + _size; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, i));
        }
        return result;

    }

    public static LinkedHashSet<GridPoint> H(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "H".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + mid - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, i));
        }

        return result;

    }

    public static LinkedHashSet<GridPoint> I(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "I".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + _size - 1));
        }
        for (int j = _yOrigin; j < _yOrigin + _size; j++) {
            if (j < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + mid - 1, j));
        }

        return result;

    }

    public static LinkedHashSet<GridPoint> J(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "J".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
        }
        for (int i = _xOrigin; i < _xOrigin + mid; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + _size - 1));
        }
        for (int j = _yOrigin; j < _yOrigin + _size; j++) {
            if (j < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + mid, j));
        }
        for (int j = _yOrigin + mid; j < _yOrigin + _size; j++) {
            if (j < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, j));
        }

        return result;

    }

    public static LinkedHashSet<GridPoint> K(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "K".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        int diag_factor = 0;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        PointList line = _pg.getLine(_xOrigin, _yOrigin + mid - 1, _xOrigin + _size - 1, _yOrigin);
        result.addAll(line.points());
        line = _pg.getLine(_xOrigin, _yOrigin + mid - 1, _xOrigin + _size - 1, _yOrigin + _size - 1);
        result.addAll(line.points());

        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
        }

        return result;

    }

    public static LinkedHashSet<GridPoint> L(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "L".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + _size - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
        }
        return result;

    }

    public static LinkedHashSet<GridPoint> M(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "M".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        PointList line = _pg.getLine(_xOrigin, _yOrigin, _xOrigin + mid - 1, _yOrigin + mid - 1);
        result.addAll(line.points());
        line = _pg.getLine(_xOrigin + _size - 1, _yOrigin, _xOrigin + mid - 1, _yOrigin + mid - 1);
        result.addAll(line.points());

        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, i));
        }

        return result;

    }

    public static LinkedHashSet<GridPoint> N(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "N".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        PointList line = _pg.getLine(_xOrigin, _yOrigin, _xOrigin + _size - 1, _yOrigin + _size - 1);
        result.addAll(line.points());

        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, i));
        }

        return result;

    }

    public static LinkedHashSet<GridPoint> O(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "O".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin + 1; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + _size - 1));
        }
        for (int j = _yOrigin + 1; j < _yOrigin + _size - 1; j++) {
            if (j < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, j));
            if (j < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, j));
        }
        return result;

    }

    public static LinkedHashSet<GridPoint> P(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "P".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + mid - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
        }
        for (int i = _yOrigin + 1; i < _yOrigin + mid - 1; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, i));
        }
        return result;

    }

    public static LinkedHashSet<GridPoint> Q(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "Q".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        PointList line = _pg.getLine(_xOrigin + mid - 1, _yOrigin + _size - 1, _xOrigin + _size - 1, _yOrigin + mid);
        result.addAll(line.points());

        for (int i = _xOrigin + 1; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
        }
        for (int i = _xOrigin + 1; i < _xOrigin + mid - 1; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + _size - 1));
        }
        for (int j = _yOrigin + 1; j < _yOrigin + _size - 1; j++) {
            if (j < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, j));
        }
        for (int j = _yOrigin + 1; j < _yOrigin + mid; j++) {
            if (j < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, j));
        }
        for (int j = _yOrigin + mid; j < _yOrigin + _size ; j++) {
            if (j < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, j));
        }

        return result;

    }

    public static LinkedHashSet<GridPoint> R(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "R".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        PointList line = _pg.getLine(_xOrigin, _yOrigin + mid - 1, _xOrigin + _size - 1, _yOrigin + _size - 1);
        result.addAll(line.points());

        for (int i = _xOrigin; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + mid - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
        }
        for (int i = _yOrigin + 1; i < _yOrigin + mid - 1; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, i));
        }
        return result;

    }

    public static LinkedHashSet<GridPoint> S(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "S".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin + 1; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + mid - 1));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + _size - 1));
        }
        for (int i = _yOrigin + 1; i < _yOrigin + mid - 1; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
        }
        for (int i = _yOrigin + mid; i < _yOrigin + _size - 1; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, i));
        }
        return result;

    }

    public static LinkedHashSet<GridPoint> T(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "T".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
        }
        for (int j = _yOrigin; j < _yOrigin + _size; j++) {
            if (j < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + mid - 1, j));
        }

        return result;

    }

    public static LinkedHashSet<GridPoint> U(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "U".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin + 1; i < _xOrigin + _size - 1; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + _size - 1));
        }
        for (int i = _yOrigin; i < _yOrigin + _size - 1; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, i));
        }

        return result;

    }

    public static LinkedHashSet<GridPoint> V(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "V".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        PointList line = _pg.getLine(_xOrigin, _yOrigin, _xOrigin + mid - 1, _yOrigin + _size - 1);
        result.addAll(line.points());
        line = _pg.getLine(_xOrigin + _size - 1, _yOrigin, _xOrigin + mid - 1, _yOrigin + _size - 1);
        result.addAll(line.points());

        return result;

    }

    public static LinkedHashSet<GridPoint> W(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "W".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;

        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        PointList line = _pg.getLine(_xOrigin, _yOrigin + _size - 1, _xOrigin + mid - 1, _yOrigin + mid - 1);
        result.addAll(line.points());
        line = _pg.getLine(_xOrigin + _size - 1, _yOrigin + _size - 1, _xOrigin + mid - 1, _yOrigin + mid - 1);
        result.addAll(line.points());

        for (int i = _yOrigin; i < _yOrigin + _size - 1; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin, i));
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + _size - 1, i));
        }

        return result;

    }

    public static LinkedHashSet<GridPoint> X(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "X".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        PointList line = _pg.getLine(_xOrigin, _yOrigin, _xOrigin + _size - 1, _yOrigin + _size - 1);
        result.addAll(line.points());
        line = _pg.getLine(_xOrigin + _size - 1, _yOrigin, _xOrigin, _yOrigin + _size - 1);
        result.addAll(line.points());

        return result;

    }

    public static LinkedHashSet<GridPoint> Y(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "Y".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        int mid = _size / 2 + 1;
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        PointList line = _pg.getLine(_xOrigin, _yOrigin, _xOrigin + mid - 1, _yOrigin + mid - 1);
        result.addAll(line.points());
        line = _pg.getLine(_xOrigin + _size - 1, _yOrigin, _xOrigin + mid - 1, _yOrigin + mid - 1);
        result.addAll(line.points());

        for (int i = _yOrigin + mid; i < _yOrigin + _size - 1; i++) {
            if (i < _pg.yPoints()) result.add(_pg.getPoint(_xOrigin + mid - 1, i));
        }

        return result;

    }

    public static LinkedHashSet<GridPoint> Z(int _xOrigin, int _yOrigin, int _size, PointGrid _pg) {

        // Returns a collection of points conforming to the shape "Z".
        // Minimum point length and minimum point height is 5.
        // Where:
        // _xOrigin -> top left coordinate of letter (x).
        // _yOrigin -> top left coordinate of letter (y).
        // _size -> smallest size is 1.
        // _pg -> Point_Grid to grab points from.

        _size = sizeCalc(_size);
        LinkedHashSet<GridPoint> result = new LinkedHashSet<>();

        for (int i = _xOrigin; i < _xOrigin + _size; i++) {
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin));
            if (i < _pg.xPoints()) result.add(_pg.getPoint(i, _yOrigin + _size - 1));
        }

        PointList line = _pg.getLine(_xOrigin, _yOrigin + _size - 1, _xOrigin + _size - 1, _yOrigin);
        result.addAll(line.points());

        return result;

    }

    private static int sizeCalc(int _size) {
        // Returns a size starting at 5 and progressing according to odd numbers.

        return 5 + 2*(_size - 1);

    }

}
