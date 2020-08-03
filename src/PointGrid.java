import processing.core.PImage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PointGrid implements Iterable<GridPoint>{

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

    private final int xPoints;
    private final int yPoints; // Number of Grid_Points in x-dir and y-dir (cols, rows).
    private final int spacingX;
    private final int spacingY; // Spacing between points in x and y axes.

    private int xOrigin;
    private int yOrigin; // Define the top-left X and Y pixel-coordinate values for the grid system.
    private Point centerPoint; // CenterPoint of Grid (x, y pixel-coordinate values)
    private ArrayList<ArrayList<GridPoint>> points; // Points held by grid.

    // * =========== CONSTRUCTORS ============== * //

    public PointGrid(Point centerPoint, int xPoints, int yPoints, int spacingX, int spacingY) {
        this.xPoints = xPoints;
        this.yPoints = yPoints;
        this.centerPoint(centerPoint);
        this.spacingX = spacingX;
        this.spacingY = spacingY;

        this.xOrigin((int)(centerPoint.xPos() - ((xPoints/2)*spacingX)));
        this.yOrigin((int)centerPoint.yPos() - ((yPoints/2)*spacingY));

        this.points(new ArrayList<ArrayList<GridPoint>>());

        for (int i = 0; i < xPoints; i += 1) {
            int xPos = this.xOrigin() + (i * spacingX);
            this.points().add(new ArrayList<GridPoint>());
            for (int j = 0; j < yPoints; j += 1) {
                int yPos = this.yOrigin() + (j * spacingY);
                this.points().get(i).add(new GridPoint(xPos, yPos, i, j, this));
            }
        }
    }

    public PointGrid(int centerX, int centerY, int xPoints, int yPoints, int spacingX, int spacingY) {
        this(new Point(centerX, centerY), xPoints, yPoints, spacingX, spacingY);
    }

    public PointGrid(PointGrid _pg) {
        this.xPoints = _pg.xPoints(); this.yPoints = _pg.yPoints();
        this.centerPoint(new Point(_pg.centerPoint()));
        this.spacingX = _pg.spacingX(); this.spacingY = _pg.spacingY();

        this.xOrigin(_pg.xOrigin());
        this.yOrigin(_pg.yOrigin());

        this.points(Helpers.cloneGridPoints(_pg));
    }

    public PointGrid(PointGrid _pg, ArrayList<ArrayList<GridPoint>> _al) {

        this.centerPoint(new Point(_pg.centerPoint()));
        this.spacingX = _pg.spacingX(); this.spacingY = _pg.spacingY();

        this.xOrigin(_pg.xOrigin());
        this.yOrigin(_pg.yOrigin());

        this.points(new ArrayList<ArrayList<GridPoint>>(_al));

        this.xPoints = points().size(); this.yPoints = points().get(0).size();
    }

    // * =========== ITERATOR ============== * //

    @Override
    public Iterator<GridPoint> iterator() {
        return new GridIterator(this.points());
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

    public PointGrid move(int _x, int _y) {

        return Move.move(_x, _y, this);

    }

    public PointGrid move(int _x, int _y, Selection _s) {

        return Move.gridSelectionMove(_x, _y, _s, this);

    }

    public PointGrid moveMult(int _x, int _y) {

       return Move.multiplyPosns(_x, _y, this);

    }

    public PointGrid moveMult(int _x, int _y, Selection _s) {

        return Move.gridSelectionMultiplyPosns(_x, _y, _s, this);

    }

    public PointGrid moveTo(int _x, int _y) {

        return Move.gridTo(_x, _y, this);

    }

    public PointGrid moveTo(int _x, int _y, Selection _s) {

        return Move.gridSelectionTo(_x, _y, _s, this);

    }

    public PointGrid moveReset() {

       return Move.resetPosns(this);

    }

    public PointGrid moveReset(Selection _s) {

        return Move.gridSelectionResetPosns(_s, this);

    }

    // * =========== GETTERS ============== * //

    public GridPoint getPoint(int _col, int _row) {

        return Getters.getGridPoint(_col, _row, this);

    }

    public GridPoint getPointSafe(int _col, int _row) {

        return Getters.getGridPointSafe(_col, _row, this);

    }

    public GridPoint getPointMirror(int _col, int _row) {

        return Getters.getGridPointMirror(_col, _row, this);

    }

    public GridPoint getPointMirrorX(int _col, int _row) {

        return Getters.getGridPointMirrorX(_col, _row, this);

    }

    public GridPoint getPointMirrorY(int _col, int _row) {

        return Getters.getGridPointMirrorY(_col, _row, this);

    }

    public PointList getPointsByWeight(double _floor, double _ceil) {

       return Getters.getPointsByWeight(_floor, _ceil, this);

    }

    public PointList getColumn(int _x) {

        return Getters.getGridColumn(_x, this);

    }

    public PointList getRow(int _y) {

        return Getters.getGridRow(_y, this);

    }

    public PointList getLine(int _col0, int _row0, int _col1, int _row1) {

        return Getters.getGridLine(_col0, _row0, _col1, _row1, this);

    }

    public PointList getLineNoOp(int _col0, int _row0, int _col1, int _row1) {

        return Getters.getGridLineNoOp(_col0, _row0, _col1, _row1, this);

    }

    public PointList getCircle(int _col0, int _row0, int _rad) {

        return Getters.getGridCircle(_col0, _row0, _rad, this);

    }

    public PointList getCircleFill(int _col, int _row, int _rad) {

        return Getters.getGridCircleFill(_col, _row, _rad, this);

    }

    public PointList getPolyline(PointList _pl, boolean _closed) {

        return Getters.getGridPolyline(_pl, _closed, this);

    }

    public PointList getPolylineFill(PointList _pl) {

        return Getters.getGridPolylineFill(_pl, this);

    }

    public PointList getPattern(int _col, int _row, List<Integer> _dlist, int _reps, boolean _overflow) {

        return Getters.getGridPattern(_col, _row, _dlist, _reps, _overflow, this);

    }

    public PointList getEvery(int _x, int _y) {

        return Getters.getGridEvery(_x, _y, this);

    }

    public PointList getRegion(int _x1, int _y1, int _x2, int _y2) {

        return Getters.getGridRegion(_x1, _y1, _x2, _y2, this);

    }

    public ArrayList<String> getRowsAsBinary(double threshold)  {

        return Getters.getRowsAsBinary(threshold, this);

    }

    public ArrayList<String> getColsAsBinary(double threshold) {

        return Getters.getColsAsBinary(threshold, this);

    }

    public PointList getText(String _sentence, int _xOrigin, int _yOrigin, int _size) {

        return Text.getSentence(_sentence, _xOrigin, _yOrigin, _size, this);

    }

    // * =========== UNIVERSAL APPLICATORS ============== * //

    public void color(int _col) {

        Applicators.color(Core.processing.color(_col), this);

    }

    public void color(int _r, int _g, int _b) {

        Applicators.color(Core.processing.color(_r, _g, _b), this);

    }


    public void color(int _r, int _g, int _b, int _a) {

        Applicators.color(Core.processing.color(_r, _g, _b, _a), this);

    }

    public void color(int _col, Selection _s) {

        Applicators.gridSelectColor(Core.processing.color(_col), _s, this);

    }

    public void color(int _r, int _g, int _b, Selection _s) {

        Applicators.gridSelectColor(Core.processing.color(_r, _g, _b), _s, this);

    }

    public void color(int _r, int _g, int _b, int _a, Selection _s) {

        Applicators.gridSelectColor(Core.processing.color(_r, _g, _b, _a), _s, this);

    }

    public void weight(double _weight) {

        Applicators.weight(_weight, this);

    }

    public void weight(double _weight, Selection _s) {

        Applicators.gridSelectWeight(_weight, _s, this);

    }

    public void weightAdd(double _to_add) {

        Applicators.weightAdd(_to_add, this);

    }

    public void weightAdd(double _to_add, Selection _s) {

        Applicators.gridSelectWeightAdd(_to_add, _s, this);

    }

    public void weightMultiply(double _factor) {

        Applicators.weightMultiply(_factor, this);

    }


    public void weightMultiply(double _factor, Selection _s) {

        Applicators.gridSelectWeightMultiply(_factor, _s, this);

    }

    public void weightReset() {

        Applicators.weightReset(this);

    }

    public void weightReset(Selection _s) {

        Applicators.gridSelectWeightReset(_s, this);

    }

    public void binaryWeight(double threshold) {

        Applicators.binaryWeight(threshold, this);

    }

    public void filter(double _low, double _high) {

        Applicators.weightFilter(_low, _high, this);

    }

    public void filter(double _low, double _high, Selection _s) {

        Applicators.gridSelectWeightFilter(_low, _high, _s, this);

    }

    // * =========== GRADIENT APPLICATORS ============== * //


    public PointGrid applyGradient(Gradient grad) {

        grad.applyWeightsToPoints(this);
        return this;

    }

    /// * =========== NOISE APPLICATORS ============== * ///

    public PointGrid applyNoise(Noise noise) {

        noise.applyWeightToPoints(this);
        return this;

    }

    /// * =========== IMAGE APPLICATORS ============== * ///

    public void image(PImage _img, String _mode, int _shift_x, int _shift_y, boolean _subtract, boolean _blend, double _opacity) {
        // TODO: Add Scaling

        Image.image(_img, _mode, _shift_x, _shift_y, _subtract, _blend, _opacity, this);

    }


    // * =========== PRIVATE HELPERS ============== * //

    public int checkQuad(GridPoint grid_point) {

        // Returns the number corresponding to the quadrant a given Grid_Point is in.
        // 1 -> TR, 2 -> TL, 3 -> BL, 4 -> BR
        // Where:
        // _gp -> Grid_Point to test

        int x_mid = this.xPoints() / 2;
        int y_mid = this.yPoints() / 2;

        if (grid_point.gridIndexX() > x_mid && grid_point.gridIndexX() < y_mid) return 1;
        else if (grid_point.gridIndexX() < x_mid && grid_point.gridIndexY() < y_mid) return 2;
        else if (grid_point.gridIndexX() < x_mid && grid_point.gridIndexY() > y_mid) return 3;
        else return 4;

    }

    public double gridApproxDist(GridPoint grid_point_1, GridPoint grid_point_2) {

        // Returns a non-sqrt based distance between two points in Grid.
        // Use when a precise distance is not necessary.
        // Distance is calculated using grid indices and not actual x y values.
        // Where:
        // _pg1 -> First point
        // _pg2 -> Second point

        return Math.pow(grid_point_1.gridIndexX() - grid_point_2.gridIndexX(), 2) + Math.pow(grid_point_1.gridIndexY() - grid_point_2.gridIndexY(), 2);

    }

    public double gridExactDist(GridPoint grid_point_1, GridPoint grid_point_2) {

        // Returns the sqrt based distance between two points in Grid.
        // Avoid using if possible.
        // Distance is calculated using grid indices and not actual x y values.
        // Where:
        // _pg1 -> First point
        // _pg2 -> Second point

        return Math.sqrt(Math.pow(grid_point_1.gridIndexX() - grid_point_2.gridIndexX(), 2) + Math.pow(grid_point_1.gridIndexY() - grid_point_2.gridIndexY(), 2));

    }

    private int szdudzikHash(int a, int b) {

        // Returns a unique value for any two integers a, b >= 0

        return a >= b ? a * a + a + b : a + b * b;

    }

    public int xPoints() {
        return xPoints;
    }

    public int yPoints() {
        return yPoints;
    }

    public int spacingX() {
        return spacingX;
    }

    public int spacingY() {
        return spacingY;
    }

    public int xOrigin() {
        return xOrigin;
    }

    public void xOrigin(int xOrigin) {
        this.xOrigin = xOrigin;
    }

    public int yOrigin() {
        return yOrigin;
    }

    public void yOrigin(int yOrigin) {
        this.yOrigin = yOrigin;
    }

    public Point centerPoint() {
        return centerPoint;
    }

    public void centerPoint(Point centerPoint) {
        this.centerPoint = centerPoint;
    }

    public ArrayList<ArrayList<GridPoint>> points() {
        return points;
    }

    public void points(ArrayList<ArrayList<GridPoint>> points) {
        this.points = points;
    }
}