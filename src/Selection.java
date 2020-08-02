
public class Selection {

// a SELECTION represents a portion of a POINT_GRID that can be passed into
// certain functions to limit their effect to specific areas.
// startCol, startRow -> top-left corner of selection rectangle
// startRow, endRow -> bottom-right corner of selection rectangle

    private int startCol;
    private int endCol;
    private int startRow;
    private int endRow;
    private int center_x;
    private int center_y;

    public Selection (int _col0, int _row0, int _col1, int _row1, PointGrid _pg) {

        if ( Helpers.checkBounds(_col0, _row0, _col1, _row1, _pg) && _col0 <= _col1 && _row0 <= _row1 ) {
            startCol(_col0);
            startRow(_row0);
            endCol(_col1);
            endRow(_row1);
            center_x((_col1 + _col0)/2);
            center_y((_row1 + _row0)/2);
        } else {
            throw new java.lang.RuntimeException("Selection exceeds given Point_Grid bounds, or col/row inputs are wrong.");
        }

    }

    public int center_x(){
        return center_x;
    }

    public int center_y(){
        return center_y;
    }

    public int startCol() {
        return startCol;
    }

    public void startCol(int startCol) {
        this.startCol = startCol;
    }

    public int endCol() {
        return endCol;
    }

    public void endCol(int endCol) {
        this.endCol = endCol;
    }

    public int startRow() {
        return startRow;
    }

    public void startRow(int startRow) {
        this.startRow = startRow;
    }

    public int endRow() {
        return endRow;
    }

    public void endRow(int endRow) {
        this.endRow = endRow;
    }

    public void center_x(int center_x) {
        this.center_x = center_x;
    }

    public void center_y(int center_y) {
        this.center_y = center_y;
    }
}