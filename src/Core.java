import processing.core.*;

public class Core extends PApplet {

    public static PApplet processing;
    public static void main(String args[]) { PApplet.main("Core"); }

    Point_Grid pg1;

    @Override
    public void setup() {
        processing = this; /* <-- Necessary to pass PApplet reference to other classes. -- */

        pg1 = new Point_Grid(30, 30, new Point(width/2, height/2), 10, 10);
        pg1.color(color(255, 0, 0));
        pg1.weight(0);
        pg1.applyLinRadGradient(15, 15, 10, 1.0, true, false, 1.0);
    };

    @Override
    public void settings() {

        size(500, 500);
    };

    @Override
    public void draw() {
        background(0);
        pg1.draw(1, 5, true);

    };

}
