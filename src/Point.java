public class Point {

    // POINT ( x: FLOAT, y: FLOAT, gridIndexX: INT, gridIndexY: INT )
    // interp: a POINT represents a point in cartesian coords (as distinct from a GRID_POINT, which has a grid index position).
    // x -> current window coordinate X of point.[INT]
    // y -> current window coordinate Y of point.[INT]
    // oY -> original window coordinate X of point.[INT]
    // oX -> original window coordinate Y of point.[INT]
    // weight -> associated weight of point.[DOUBLE]
    // col -> color in Processing color() format.[INT]

    public float originalXPos, originalYPos, xPos, yPos;
    public int col;
    public double weight;

    public Point (float _x, float _y) {
        this.xPos = _x; this.yPos = _y;
        this.originalXPos = _x; this.originalYPos = _y;
        this.weight = 1;
        this.col = Core.processing.color(255);
    }

    public Point (float _x, float _y, int _color) {
        this.xPos = _x; this.yPos = _y;
        this.originalXPos = _x; this.originalYPos = _y;
        this.weight = 1;
        this.col = _color;
    }

    public Point (float _x, float _y, double _weight) {
        this.xPos = _x; this.yPos = _y;
        this.originalXPos = _x; this.originalYPos = _y;
        this.weight = Helpers.clamp(_weight, 0.0, 1.0);
        this.col = Core.processing.color(255);
    }

    public Point (float _x, float _y, int _color, double _weight) {
        this.xPos = _x; this.yPos = _y;
        this.originalXPos = _x; this.originalYPos = _y;
        this.weight = Helpers.clamp(_weight, 0.0, 1.0);
        this.col = _color;
    }

    public Point (Point _p) {
        this.xPos = _p.xPos; this.yPos = _p.yPos;
        this.originalXPos = _p.originalXPos; this.originalYPos = _p.originalYPos;
        this.weight = _p.weight;
        this.col = _p.col;
    }

    public Point (Grid_Point _p) {
        this.xPos = _p.xPos; this.yPos = _p.yPos;
        this.originalXPos = _p.originalXPos; this.originalYPos = _p.originalYPos;
        this.weight = _p.weight;
        this.col = _p.col;
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

    public void move_to(int _x, int _y) {
        // Moves Point to x, y.
        this.yPos = _y;
        this.xPos = _x;
    }

    public void reset() {
       // Moves Point to original x, y position (position when it was created).
        this.yPos = this.originalYPos;
        this.xPos = this.originalXPos;
    }

}