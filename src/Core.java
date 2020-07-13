import processing.core.*;

public class Core extends PApplet {
    public static PApplet processing;

    public static void main(String args[]) { PApplet.main("Core"); }

    @Override
    public void setup() {
        processing = this; /* <-- Necessary to pass PApplet reference to other classes. -- */

        Point p = new Point(2, 5);
        Tuple2<Integer, Integer> tup = new Tuple2<Integer, Integer>(20, 30);
    };

    @Override
    public void settings() {};

    @Override
    public void draw() {};

}
