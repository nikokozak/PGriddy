public class Point {

    // POINT ( x: FLOAT, y: FLOAT, gridIndexX: INT, gridIndexY: INT )
    // interp: a POINT represents a point in cartesian coords (as distinct from a GRID_POINT, which has a grid index position).
    // x -> current window coordinate X of point.[INT]
    // y -> current window coordinate Y of point.[INT]
    // oY -> original window coordinate X of point.[INT]
    // oX -> original window coordinate Y of point.[INT]
    // weight -> associated weight of point.[DOUBLE]
    // col -> color in Processing color() format.[INT]

    private float xPos, yPos;
    private final float originalXPos, originalYPos;
    private int col;
    private double weight;

    public float xPos() {
        return xPos;
    }

    public void xPos(float xPos) {
        this.xPos = xPos;
    }

    public float yPos() {
        return yPos;
    }

    public void yPos(float yPos) {
        this.yPos = yPos;
    }

    public float originalXPos() {
        return originalXPos;
    }

    public float originalYPos() {
        return originalYPos;
    }

    public int col() {
        return col;
    }

    public void col(int col) {
        this.col = col;
    }

    public double weight() {
        return weight;
    }

    public void weight(double weight) {
        this.weight = weight;
    }

    public Point (float _x, float _y) {
        this.xPos = _x; this.yPos = _y;
        this.originalXPos = _x; this.originalYPos = _y;
        this.weight = 1;
        this.col = Core.processing.color(255);
    }

    public Point (float _x, float _y, int _color) {
        this(_x, _y);
        this.col = _color;
    }

    public Point (float _x, float _y, double _weight) {
        this(_x, _y);
        this.weight = Helpers.clamp(_weight, 0.0, 1.0);
    }

    public Point (float _x, float _y, int _color, double _weight) {
        this(_x, _y, _color);
        this.weight = Helpers.clamp(_weight, 0.0, 1.0);
    }

    public Point (Point _p) {
        this.xPos = _p.xPos; this.yPos = _p.yPos;
        this.originalXPos = _p.originalXPos; this.originalYPos = _p.originalYPos;
        this.weight = _p.weight;
        this.col = _p.col;
    }

    public Point (GridPoint _p) {
        this.xPos = _p.xPos(); this.yPos = _p.yPos();
        this.originalXPos = _p.originalXPos(); this.originalYPos = _p.originalYPos();
        this.weight = _p.weight();
        this.col = _p.col();
    }

    public void draw() {
        // Draws point to screen as a simple Processing point (no fill, weight).

        Core.processing.point(xPos, yPos);
    }

    public void draw(int type) {
        // Draws a point to screen as either a Processing point, circle, or rect (size 3 for circle and rect).
        // No fill, or weight set.
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect
        switch (type) {
            case 0 -> Core.processing.point(xPos, yPos);
            case 1 -> Core.processing.circle(xPos, yPos, 3);
            case 2 -> Core.processing.rect(xPos, yPos, 3, 3);
        }
    }

    public void draw(int type, boolean weight) {
        // Draws a point to screen as either a Processing point, circle, or rect (size 3 for circle and rect).
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect
        // weight -> whether to set alpha as weight.
        if (weight) Core.processing.fill(this.col, Helpers.weightToRGB(this.weight));
        else Core.processing.fill(this.col);
        switch (type) {
            case 0 -> Core.processing.point(xPos, yPos);
            case 1 -> Core.processing.circle(xPos, yPos, 3);
            case 2 -> Core.processing.rect(xPos, yPos, 3, 3);
        }
    }

    public void draw(int type, float size, boolean weight) {
        // Draws a point to screen as either a Processing point, circle, or rect.
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect
        // size -> size of rect or circle.
        // weight -> whether to set alpha as weight.
        if (weight) Core.processing.fill(this.col, Helpers.weightToRGB(this.weight));
        else Core.processing.fill(this.col);
        switch (type) {
            case 0 -> Core.processing.point(xPos, yPos);
            case 1 -> Core.processing.circle(xPos, yPos, size);
            case 2 -> Core.processing.rect(xPos, yPos, size, size);
        }
    }

    public void move(int _x, int _y) {
        // Adds x and y quantities to Point position.
        this.yPos += _y;
        this.xPos += _x;
    }

    public void moveTo(int _x, int _y) {
        // Moves Point to x, y.
        this.yPos = _y;
        this.xPos = _x;
    }

    public void moveReset() {
       // Moves Point to original x, y position (position when it was created).
        this.yPos = this.originalYPos;
        this.xPos = this.originalXPos;
    }

    public int color() {

        return this.col;

    }

    public Point color(int _col) {

        this.col = Core.processing.color(_col);
        return this;

    }

    public Point color(int _r, int _g, int _b) {

        this.col = Core.processing.color(_r, _g, _b);
        return this;

    }

    public Point color(int _r, int _g, int _b, int _a) {

        this.col = Core.processing.color(_r, _g, _b, _a);
        return this;

    }

}