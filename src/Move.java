public class Move {

    public static <T extends Iterable<GridPoint>> T move(int _x, int _y, T _l) {

        // Adds x and y quantities to Point positions.

        for (GridPoint currPoint : _l) {
            currPoint.xPos += _x;
            currPoint.yPos += _y;
        }

        return _l;

    }

    public static PointGrid gridSelectionMove(int _x, int _y, Selection _s, PointGrid _pg) {

        // Adds x and y quantities to Point positions within a given selection.

        GridPoint currPoint;

        for (int x = _s.startCol(); x <= _s.endCol(); x++) {
            for (int y = _s.startRow(); y <= _s.endRow(); y++) {
                currPoint = _pg.points().get(x).get(y);
                currPoint.xPos += _x;
                currPoint.yPos += _y;
            }
        }

        return _pg;

    }

    public static <T extends Iterable<GridPoint>> T multiplyPosns(int _x, int _y, T _l) {

        // Multiplies Point positions by _x, _y.

        for (GridPoint currPoint : _l) {
            currPoint.yPos *= _y;
            currPoint.xPos *= _x;
        }

        return _l;

    }

    public static PointGrid gridSelectionMultiplyPosns(int _x, int _y, Selection _s, PointGrid _pg) {

        // Multiplies x and y positions by _x and _y within a given selection.

        GridPoint currPoint;

        for (int x = _s.startCol(); x <= _s.endCol(); x++) {
            for (int y = _s.startRow(); y <= _s.endRow(); y++) {
                currPoint = _pg.points().get(x).get(y);
                currPoint.xPos *= _x;
                currPoint.yPos *= _y;
            }
        }

        return _pg;

    }

    public static PointGrid gridTo(int _x, int _y, PointGrid _pg) {

        // Moves the entire Grid to a new x, y center.
        // RESETS ALL PAREMETERS PERTAINING TO ORIGIN, ETC.

        Point newCenter = new Point(_x, _y);
        _pg.centerPoint(newCenter);

        _pg.xOrigin((int)newCenter.xPos - ((_pg.xPoints() /2)* _pg.spacingX()));
        _pg.yOrigin((int)newCenter.yPos - ((_pg.yPoints() /2)* _pg.spacingY()));

        for (int i = 0; i < _pg.xPoints(); i += 1) {
            int xPos = _pg.xOrigin() + (i * _pg.spacingX());
            for (int j = 0; j < _pg.yPoints(); j += 1) {
                int yPos = _pg.yOrigin() + (j * _pg.spacingY());
                _pg.points().get(i).get(j).xPos = xPos;
                _pg.points().get(i).get(j).yPos = yPos;
            }
        }

        return _pg;
    }

    public static PointGrid gridSelectionTo(int _x, int _y, Selection _s, PointGrid _pg) {

        // Moves a grid selection to a new x, y center. Translation is based on selection center.
        // DOES NOT AFFECT GRID CENTER ATTRIBUTE.

        int x_translate = _x - _s.center_x();
        int y_translate = _y - _s.center_y();

        for (int x = _s.startCol(); x < _s.endCol(); x++)  {
            for (int y = _s.startRow(); y < _s.endRow(); y++) {
                _pg.points().get(x).get(y).xPos += x_translate;
                _pg.points().get(x).get(y).yPos += y_translate;
            }
        }

        return _pg;
    }

    public static PointList listTo(int _x, int _y, PointList _pl) {

        // Moves the entire Point List to a new x, y position.
        // Translation is based on the first point in the list.

        float x_translation = _pl.get(0).xPos - _x;
        float y_translation = _pl.get(0).yPos - _y;

        for (GridPoint currPoint : _pl.points()) {
            currPoint.xPos += x_translation;
            currPoint.yPos += y_translation;
        }

        return _pl;

    }

    public static <T extends Iterable<GridPoint>> T resetPosns(T _l) {

        // Resets all Points to their original positions.

        for (GridPoint currPoint : _l) {
            currPoint.xPos = currPoint.originalXPos;
            currPoint.yPos = currPoint.originalYPos;
        }

        return _l;

    }

    public static PointGrid gridSelectionResetPosns(Selection _s, PointGrid _pg) {

        // Resets all Grid_Points within a selection to their original positions.

        GridPoint currPoint;

        for (int x = _s.startCol(); x < _s.endCol(); x++) {
            for (int y = _s.startRow(); y < _s.endRow(); y++) {
                currPoint = _pg.points().get(x).get(y);
                currPoint.xPos = currPoint.originalXPos;
                currPoint.yPos = currPoint.originalYPos;
            }
        }

        return _pg;

    }

}
