import processing.core.*;

public class Core extends PApplet {

    PointGrid pg1, pg2;
    Noise n1, n2;
    Gradient g1;
    PointList c1, c2, c3, c4, c5;

    public static PApplet processing;

    public static void main(String args[]) { PApplet.main("Core"); }

    @Override
    public void setup() {
        processing = this; /* <-- Necessary to pass PApplet reference to other classes. -- */

        pg1 = new PointGrid(width/2, height/2, 100, 100, 10, 10);
        pg2 = new PointGrid(width/2, height/2, 200, 200, 10, 10);
        n1 = new Noise(Noise.Type.SIMPLEX_FRACTAL);
        n2 = new Noise(Noise.Type.VALUE_FRACTAL);
        n2.frequency(0.005);
        g1 = new Gradient(Gradient.Type.RADIAL);
        c1 = pg1.getCircle(50, 50, 20);
        c2 = pg2.getCircleFill(100, 100, 20);


        g1.subtract = true;
        g1.inverse = true;
        g1.blend = true;
        g1.radius = 20;
        pg1.applyNoise(n1);
        pg1.weightMultiply(6);
        pg1.applyGradient(g1);
        c1.weight(1);

    };

    float counter = 0;
    float counter2 = 0;

    @Override
    public void settings() {

        size(1000, 1000);

    };

    int count = 20;


    @Override
    public void draw() {
        background(10);



        c1 = pg1.getCircle(50, 50, count);


        n1.time(counter);
        n2.time(counter2);
        pg1.applyNoise(n1);
        pg2.applyNoise(n2);
        pg1.weightMultiply(8);
        pg1.applyGradient(g1);
        c1.weight(0.5);
        c2.weight(0);

      /*  for (Grid_Point point : pg1) {

                point.color((int) (254 * point.weight), 0, (int) (255 * point.weight));

        }

       */

        pg2.draw(1, 5, true);
        pg1.draw(1, 5, true);

        counter += 0.4;
        counter2 += 2;

    };

}
