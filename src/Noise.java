import java.util.ArrayList;

public class Noise {

    public static <T extends Iterable<Grid_Point>> T apply_perlin(double _min, double _max, float _time, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on Perlin Noise.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _time -> Time (Z-axis) factor for animating Perlin (takes values from 0.0 - 1.0);
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        for (Grid_Point currPoint : _l) {
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(Core.processing.noise(currPoint.x, currPoint.y, _time), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(Core.processing.noise(currPoint.x, currPoint.y, _time), 0, 1, _min, _max) * _opacity; // Call Perlin ~
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_random(double _min, double _max, boolean _blend, double _opacity, T _l) {

        // Apply weights to points randomly.
        // Where:
        // _min -> Min weight threshold
        // _max -> Max weight threshold
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        for (Grid_Point currPoint : _l) {
            if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + Helpers.map(Core.processing.random(0, 1), 0.0, 1.0, _min, _max) * _opacity, 0, 1);
            else currPoint.weight = Helpers.map(Core.processing.random(0, 1), 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_clover_2D(double _min, double _max, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on 2D clover noise implementation.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        CloverNoise.Noise2D noise2D = new CloverNoise.Noise2D();

        for (Grid_Point currPoint : _l) {
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(noise2D.noise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(noise2D.noise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_clover_fractal(double _min, double _max, int _iterations, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on 2D fractal clover noise implementation.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        CloverNoise.Noise2D noise2D = new CloverNoise.Noise2D();

        for (Grid_Point currPoint : _l) {
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(noise2D.fractalNoise(currPoint.x, currPoint.y, _iterations), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(noise2D.fractalNoise(currPoint.x, currPoint.y, _iterations), 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_clover_frost(double _min, double _max, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on 2D frost clover noise implementation.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        CloverNoise.Noise2D noise2D = new CloverNoise.Noise2D();

        for (Grid_Point currPoint : _l) {
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(noise2D.frostNoise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(noise2D.frostNoise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_clover_marble(double _min, double _max, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on 2D marble clover noise implementation.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        CloverNoise.Noise2D noise2D = new CloverNoise.Noise2D();

        for (Grid_Point currPoint : _l) {
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(noise2D.marbleNoise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(noise2D.marbleNoise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_clover_curl(double _min, double _max, double _mix, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on 2D curl clover noise implementation.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _mix -> Curl is a Vector2, _mix determines bias when reducing to one double for weight.
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        CloverNoise.Noise2D noise2D = new CloverNoise.Noise2D();
        double xVal, yVal, weight;

        for (Grid_Point currPoint : _l) {
            CloverNoise.Vector2 curl = noise2D.curlNoise(currPoint.x, currPoint.y);
            xVal = curl.getX(); yVal = curl.getY();
            weight = Helpers.mix(xVal, yVal, _mix);
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(weight, 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(noise2D.marbleNoise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_clover_curlFractal(double _min, double _max, int _iterations, double _mix, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on 2D curl fractal clover noise implementation.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _iterations -> How many iterations the noise generator goes through.
        // _mix -> Curl is a Vector2, _mix determines bias when reducing to one double for weight.
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        CloverNoise.Noise2D noise2D = new CloverNoise.Noise2D();
        double xVal, yVal, weight;

        for (Grid_Point currPoint : _l) {
            CloverNoise.Vector2 curl = noise2D.fractalCurlNoise(currPoint.x, currPoint.y, _iterations);
            xVal = curl.getX(); yVal = curl.getY();
            weight = Helpers.mix(xVal, yVal, _mix);
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(weight, 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(noise2D.marbleNoise(currPoint.x, currPoint.y), 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

}
