import processing.core.*;

import java.util.List;

public class Core extends PApplet {

    public static PApplet processing;
    public static void main(String args[]) { PApplet.main("Core"); }
    PShape shape1;
    PShape shape2;

    Point_Grid pg1;
    Noise n1;
    Noise n2;
    Gradient g1;
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

        shape1 = loadShape("C:\\Users\\Nikolai\\Documents\\Processing\\projects\\PGriddy\\.pde\\data\\eye_1.svg");
        pg1 = new Point_Grid(new Point(width/2 + 85, height/2 + 45), 50, 50, 20, 20);
        n1 = new Noise(Noise.Type.SIMPLEX_FRACTAL);
        n2 = new Noise(Noise.Type.VALUE_FRACTAL);
        g1 = new Gradient(Gradient.Type.PERIODIC);
        g1.blend = true;
        g1.subtract = false;
        n1.blend = false;

        pl1 = pg1.get_text("control", 5, point, 1);
        //shape1.setFill(255);

        pg1.applyNoise(n1);
        pg1.color(color(255));
        pg1.weight(0);
        pl1.color(color(255, 0, 0));

    };

    @Override
    public void settings() {

        size(1000, 1000);

    };

    float count = 0;
    int point = 0;
    double shift = 0;

    @Override
    public void draw() {
        background(0);
        shift += 0.1;
        count+= 0.5;
        point++;
        n1.time = count;
        n2.time = count;
        g1.shift = shift;
        g1.frequency = 2;
        //g1.maxWeight = 0.2;
        g1.opacity = 0.7;
        pg1.applyNoise(n2);
        pg1.applyNoise(n1);
        pg1.weight_multiply(2);
        //pg1.radGradient(g1);

        shape1.disableStyle();
        Image.drawScaledColoredShapeAtPoints(shape1, (float)0.08, pg1);
        pg1.color(255);
        //pg1.draw(1, 3, true);
        pg1.weight(0);

        saveFrame();

    };

}
