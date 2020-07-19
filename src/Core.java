import processing.core.*;

import java.util.Arrays;
import java.util.List;

public class Core extends PApplet {

    public static PApplet processing;
    public static void main(String args[]) { PApplet.main("Core"); }

    Point_Grid pg1;
    Point_Grid pg2;
    Point_List pl1;
    Point_List pl2;
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

        pg1.clover_curl(0, 0.8, 0.2, false, 1);
        pg1.weight_multiply(2);
        pg1.image(image, "r", 0, 0, true, true, 1.0);
        pg2.clover_curlFractal(0, 1, 1, 0.5, false, 1);

        directions = Arrays.asList(4, 4, 3, 3, 0, 2, 2, 2, 2, 4, 4, 4, 4, 5, 5, 5);

        pl2 = new Point_List(pg1.get_point(5, 10), pg1.get_point(8, 15), pg1.get_point(12, 7));

    };

    @Override
    public void settings() {

        size(1000, 500);
    };

    @Override
    public void draw() {
        background(0);
        //pg1.draw(1, 3, true);

        if (counter < 300) counter++;

        pl1 = pg1.get_pattern(0, 0, directions, counter, true);

        pg2.draw(1, 3, true);
        pl1.draw(1, 3, true);

    };

}
