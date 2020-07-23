import processing.core.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Core extends PApplet {

    public static PApplet processing;
    public static void main(String args[]) { PApplet.main("Core"); }

    Point_Grid pg1;
    Point_Grid pg2;
    Point_List pl1;
    Point_List pl2;
    Point_List A;
    Grid_Point p1;
    List<Integer> directions;
    PImage image;
    int counter = 0;

    @Override
    public void setup() {
        processing = this; /* <-- Necessary to pass PApplet reference to other classes. -- */
        image = loadImage("C:\\Users\\Nikolai\\Documents\\Processing\\projects\\PGriddy\\.pde\\data\\Processing_Test_Image.png");
        pg1 = new Point_Grid(60, 60, new Point(width/4, height/2), 5, 5);
        pg2 = new Point_Grid(60, 60, new Point((width/4) * 3, height/2), 5, 5);
        pg1.color(color(255));
        pg2.color(color(255));
        //pg1.image(image, "r", 0, 0, false, false, 1);
        //pg2.image(image, "r", 0, 200, true, true, 1);
        pl2 = new Point_List();
        pl2.add(pg1.get_point(0, 15), pg1.get_point(20, 25), pg1.get_point(15, 45), pg1.get_point(4, 50));

        //pl1 = pg1.get_polyline_fill(pl2);
        pl1 = pg1.get_polyline_fill(pl2);
        p1 = pg1.get_point(11, 28);
        print(pl1.size());
        A = Text.get_sentence("HKLLKJ", 0, 8, 1, pg1);
        pg1.get_circle(15, 15, 4).color(color(255, 0, 0));
        pg1.get_circle_fill(40, 40, 25).color(color(0, 255, 0));

        pg2 = new Point_Grid(pg1);
        pg2.move_to((width/4) * 3, height/2);
        pg2 = Noise.apply_perlin_fractal(0, 1, 5, 0.01, 3, 2.0, 1, false, 1, pg2);

        pg2.color(color(255, 0, 0));
    };

    @Override
    public void settings() {

        size(1000, 500);
    };

    @Override
    public void draw() {
        background(0);


        pl1.draw(1, 3, false);
        A.color(color(0, 255, 0));
        A.draw(1, 3, false);
        pg2.draw(1, 3, true);

    };

}
