import processing.core.*;

public class Core extends PApplet {

    PointGrid pg1;
    Noise n1;
    Gradient g1;
    PointList c1;

    public static PApplet processing;

    public static void main(String args[]) { PApplet.main("Core"); }

    @Override
    public void setup() {
        processing = this; /* <-- Necessary to pass PApplet reference to other classes. -- */

        pg1 = new PointGrid(width/2, height/2, 50, 50, 10, 10);
        n1 = new Noise(Noise.Type.SIMPLEX_FRACTAL);
        g1 = new Gradient(Gradient.Type.RADIAL);
        c1 = pg1.get_circle(25, 25, 20);

        g1.subtract = true;
        g1.inverse = true;
        g1.blend = true;
        g1.radius = 20;
        pg1.applyNoise(n1);
        pg1.weight_multiply(6);
        pg1.applyGradient(g1);
        c1.weight(1);

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

        pg1.draw(1, 5, true);


    };

}
