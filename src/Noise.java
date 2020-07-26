
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
                    currPoint.weight + Helpers.map(Core.processing.noise(currPoint.xPos, currPoint.yPos, _time), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(Core.processing.noise(currPoint.xPos, currPoint.yPos, _time), 0, 1, _min, _max) * _opacity; // Call Perlin ~
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
                    currPoint.weight + Helpers.map(noise2D.noise(currPoint.xPos, currPoint.yPos), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(noise2D.noise(currPoint.xPos, currPoint.yPos), 0, 1, _min, _max) * _opacity;
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
                    currPoint.weight + Helpers.map(noise2D.fractalNoise(currPoint.xPos, currPoint.yPos, _iterations), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(noise2D.fractalNoise(currPoint.xPos, currPoint.yPos, _iterations), 0, 1, _min, _max) * _opacity;
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
                    currPoint.weight + Helpers.map(noise2D.frostNoise(currPoint.xPos, currPoint.yPos), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(noise2D.frostNoise(currPoint.xPos, currPoint.yPos), 0, 1, _min, _max) * _opacity;
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
                    currPoint.weight + Helpers.map(noise2D.marbleNoise(currPoint.xPos, currPoint.yPos), 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(noise2D.marbleNoise(currPoint.xPos, currPoint.yPos), 0, 1, _min, _max) * _opacity;
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
            CloverNoise.Vector2 curl = noise2D.curlNoise(currPoint.xPos, currPoint.yPos);
            xVal = curl.getX(); yVal = curl.getY();
            weight = Helpers.mix(xVal, yVal, _mix);
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(weight, 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(noise2D.marbleNoise(currPoint.xPos, currPoint.yPos), 0, 1, _min, _max) * _opacity;
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
            CloverNoise.Vector2 curl = noise2D.fractalCurlNoise(currPoint.xPos, currPoint.yPos, _iterations);
            xVal = curl.getX(); yVal = curl.getY();
            weight = Helpers.mix(xVal, yVal, _mix);
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(weight, 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(noise2D.marbleNoise(currPoint.xPos, currPoint.yPos), 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_value(double _min, double _max, double _time, double _freq, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on FastNoise Apply noise implementation.
        // See https://github.com/Auburns/FastNoise/wiki for details.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _time -> z-value of noise - used in 2D mode to animate.
        // _freq -> How coarse the noise generated is. Default is 0.01.
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights


        FastNoise valueNoise = new FastNoise();
        valueNoise.SetNoiseType(FastNoise.NoiseType.Value);
        valueNoise.SetFrequency((float)_freq);
        valueNoise.SetInterp(FastNoise.Interp.Hermite);
        double weight;

        for (Grid_Point currPoint : _l) {
            weight = valueNoise.GetNoise(currPoint.xPos, currPoint.yPos, (float)_time);
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(weight, 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(weight, 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_value_fractal(double _min, double _max, double _time, double _freq, int _octaves, double _lacunarity, double _gain, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on FastNoise ValueFractal noise implementation.
        // See https://github.com/Auburns/FastNoise/wiki for details.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _time -> z-value of noise - used in 2D mode to animate.
        // _freq -> How coarse the noise generated is. Default is 0.01.
        // _octaves -> Amount of noise layers used to create fractal. Default is 3
        // _lacunarity -> Frequency multiplier between octaves. Default is 2.0.
        // _gain -> Relative strength of each layer when compared to last.
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        FastNoise valueFractalNoise = new FastNoise();
        valueFractalNoise.SetNoiseType(FastNoise.NoiseType.ValueFractal);
        valueFractalNoise.SetFrequency((float)_freq);
        valueFractalNoise.SetFractalOctaves(_octaves);
        valueFractalNoise.SetFractalLacunarity((float)_lacunarity);
        valueFractalNoise.SetFractalGain((float)_gain);
        valueFractalNoise.SetInterp(FastNoise.Interp.Hermite);
        double weight;

        for (Grid_Point currPoint : _l) {
            weight = valueFractalNoise.GetNoise(currPoint.xPos, currPoint.yPos, (float) _time);
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(weight, 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(weight, 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_simplex(double _min, double _max, double _time, double _freq, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on FastNoise Simplex noise implementation.
        // See https://github.com/Auburns/FastNoise/wiki for details.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _time -> z-value of noise - used in 2D mode to animate.
        // _freq -> How coarse the noise generated is. Default is 0.01.
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights


        FastNoise simplexNoise = new FastNoise();
        simplexNoise.SetNoiseType(FastNoise.NoiseType.Simplex);
        simplexNoise.SetFrequency((float)_freq);
        simplexNoise.SetInterp(FastNoise.Interp.Hermite);
        double weight;

        for (Grid_Point currPoint : _l) {
            weight = simplexNoise.GetNoise(currPoint.xPos, currPoint.yPos, (float) _time);
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(weight, 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(weight, 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_simplex_fractal(double _min, double _max, double _time, double _freq, int _octaves, double _lacunarity, double _gain, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on FastNoise SimplexFractal noise implementation.
        // See https://github.com/Auburns/FastNoise/wiki for details.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _time -> z-value of noise - used in 2D mode to animate.
        // _freq -> How coarse the noise generated is. Default is 0.01.
        // _octaves -> Amount of noise layers used to create fractal. Default is 3
        // _lacunarity -> Frequency multiplier between octaves. Default is 2.0.
        // _gain -> Relative strength of each layer when compared to last.
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        FastNoise simplexFractalNoise = new FastNoise();
        simplexFractalNoise.SetNoiseType(FastNoise.NoiseType.SimplexFractal);
        simplexFractalNoise.SetFrequency((float)_freq);
        simplexFractalNoise.SetFractalOctaves(_octaves);
        simplexFractalNoise.SetFractalLacunarity((float)_lacunarity);
        simplexFractalNoise.SetFractalGain((float)_gain);
        simplexFractalNoise.SetInterp(FastNoise.Interp.Hermite);
        double weight;

        for (Grid_Point currPoint : _l) {
            weight = simplexFractalNoise.GetNoise(currPoint.xPos, currPoint.yPos, (float) _time);
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(weight, 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(weight, 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_cellular(double _min, double _max, double _time, double _freq, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on FastNoise Cellular noise implementation.
        // See https://github.com/Auburns/FastNoise/wiki for details.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _time -> z-value of noise - used in 2D mode to animate.
        // _freq -> How coarse the noise generated is. Default is 0.01.
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights


        FastNoise simplexNoise = new FastNoise();
        simplexNoise.SetNoiseType(FastNoise.NoiseType.Cellular);
        simplexNoise.SetFrequency((float)_freq);
        simplexNoise.SetInterp(FastNoise.Interp.Hermite);
        double weight;

        for (Grid_Point currPoint : _l) {
            weight = simplexNoise.GetNoise(currPoint.xPos, currPoint.yPos, (float) _time);
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(weight, 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(weight, 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_cubic(double _min, double _max, double _time, double _freq, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on FastNoise Cubic noise implementation.
        // See https://github.com/Auburns/FastNoise/wiki for details.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _time -> z-value of noise - used in 2D mode to animate.
        // _freq -> How coarse the noise generated is. Default is 0.01.
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights


        FastNoise simplexNoise = new FastNoise();
        simplexNoise.SetNoiseType(FastNoise.NoiseType.Cubic);
        simplexNoise.SetFrequency((float)_freq);
        simplexNoise.SetInterp(FastNoise.Interp.Hermite);
        double weight;

        for (Grid_Point currPoint : _l) {
            weight = simplexNoise.GetNoise(currPoint.xPos, currPoint.yPos, (float) _time);
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(weight, 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(weight, 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_cubic_fractal(double _min, double _max, double _time, double _freq, int _octaves, double _lacunarity, double _gain, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on FastNoise CubicFractal noise implementation.
        // See https://github.com/Auburns/FastNoise/wiki for details.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _time -> z-value of noise - used in 2D mode to animate.
        // _freq -> How coarse the noise generated is. Default is 0.01.
        // _octaves -> Amount of noise layers used to create fractal. Default is 3
        // _lacunarity -> Frequency multiplier between octaves. Default is 2.0.
        // _gain -> Relative strength of each layer when compared to last.
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        FastNoise simplexFractalNoise = new FastNoise();
        simplexFractalNoise.SetNoiseType(FastNoise.NoiseType.CubicFractal);
        simplexFractalNoise.SetFrequency((float)_freq);
        simplexFractalNoise.SetFractalOctaves(_octaves);
        simplexFractalNoise.SetFractalLacunarity((float)_lacunarity);
        simplexFractalNoise.SetFractalGain((float)_gain);
        simplexFractalNoise.SetInterp(FastNoise.Interp.Hermite);
        double weight;

        for (Grid_Point currPoint : _l) {
            weight = simplexFractalNoise.GetNoise(currPoint.xPos, currPoint.yPos, (float) _time);
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(weight, 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(weight, 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    public static <T extends Iterable<Grid_Point>> T apply_perlin_fractal(double _min, double _max, double _time, double _freq, int _octaves, double _lacunarity, double _gain, boolean _blend, double _opacity, T _l) {

        // Apply weights to points based on FastNoise PerlinFractal noise implementation.
        // See https://github.com/Auburns/FastNoise/wiki for details.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _time -> z-value of noise - used in 2D mode to animate.
        // _freq -> How coarse the noise generated is. Default is 0.01.
        // _octaves -> Amount of noise layers used to create fractal. Default is 3
        // _lacunarity -> Frequency multiplier between octaves. Default is 2.0.
        // _gain -> Relative strength of each layer when compared to last.
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        FastNoise simplexFractalNoise = new FastNoise();
        simplexFractalNoise.SetNoiseType(FastNoise.NoiseType.PerlinFractal);
        simplexFractalNoise.SetFrequency((float) _freq);
        simplexFractalNoise.SetFractalOctaves(_octaves);
        simplexFractalNoise.SetFractalLacunarity((float) _lacunarity);
        simplexFractalNoise.SetFractalGain((float) _gain);
        simplexFractalNoise.SetInterp(FastNoise.Interp.Hermite);
        double weight;

        for (Grid_Point currPoint : _l) {
            weight = simplexFractalNoise.GetNoise(currPoint.xPos, currPoint.yPos, (float) _time);
            if (_blend) currPoint.weight = Helpers.clamp(
                    currPoint.weight + Helpers.map(weight, 0, 1, _min, _max) * _opacity, 0.0, 1.0);
            else currPoint.weight = Helpers.map(weight, 0, 1, _min, _max) * _opacity;
        }

        return _l;

    }

    }
