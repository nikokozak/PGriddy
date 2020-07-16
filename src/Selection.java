
public class Selection {

// a SELECTION represents a portion of a POINT_GRID that can be passed into
// certain functions to limit their effect to specific areas.
// startCol, startRow -> top-left corner of selection rectangle
// startRow, endRow -> bottom-right corner of selection rectangle

    public int startCol, endCol;
    public int startRow, endRow;

    public Selection (int _col0, int _row0, int _col1, int _row1, Point_Grid _pg) {

        if ( Helpers.checkBounds(_col0, _row0, _col1, _row1, _pg) && _col0 <= _col1 && _row0 <= _row1 ) {
            startCol = _col0;
            startRow = _row0;
            endCol = _col1;
            endRow = _row1;
        } else {
            throw new java.lang.RuntimeException("Selection exceeds given Point_Grid bounds, or col/row inputs are wrong.");
        }

    }

}