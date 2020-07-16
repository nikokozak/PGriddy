import processing.core.*;

public class Core extends PApplet {

    public static PApplet processing;
    public static void main(String args[]) { PApplet.main("Core"); }

    Point_Grid pg1;
    Point_Grid pg2;
    PImage image;

    @Override
    public void setup() {
        processing = this; /* <-- Necessary to pass PApplet reference to other classes. -- */
        image = loadImage("C:\\Users\\Nikolai\\Documents\\Processing\\projects\\PGriddy\\.pde\\data\\Processing_Test_Image.png");
        pg1 = new Point_Grid(30, 30, new Point(width/4, height/2), 10, 10);
        pg2 = new Point_Grid(30, 30, new Point((width/4) * 3, height/2), 10, 10);
        pg1.color(color(255, 0, 0));
        pg2.color(color(255, 0, 0));
        pg1.image(image, "r", 0, 0, false, false, 1);
        pg2.image(image, "r", 0, 200, true, true, 1);

    };

    @Override
    public void settings() {

        size(1000, 500);
    };

    @Override
    public void draw() {
        background(0);
        pg1.draw(1, 5, true);
        pg2.draw(1, 5, true);

    };

}
