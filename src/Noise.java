
public class Noise {

    public double minimumWeight, maximumWeight; // -> Ceil and Floor mapping for noise.
    public float time; // -> Z axis of noise, useful for animation.
    public double opacity; // -> Opacity of weight when added to grid.
    public int iterations; // -> How many iterations of noise are produced.
    public double mix; // -> Linear mix between X and Y vectors for vector-producing noises.
    public double frequency; // -> How coarse the noise generated is. Default is 0.01.
    public double lacunarity; // -> Frequency multiplier between octaves. Default is 2.0.
    public int octaves; // -> Amount of noise layers used to create fractal. Default is 3
    public double gain; // -> Relative strength of each layer when compared to last.
    public boolean blend; // -> Whether to blend weight with any previous weight present in Point_Grid
    private Type type;


    public enum Type {
        RANDOM,
        PERLIN,
        CLOVER_2D,
        CLOVER_FRACTAL,
        CLOVER_FROST,
        CLOVER_MARBLE,
        CLOVER_CURL,
        CLOVER_CURL_FRACTAL,
        VALUE,
        VALUE_FRACTAL,
        SIMPLEX,
        SIMPLEX_FRACTAL,
        CELLULAR,
        CUBIC,
        CUBIC_FRACTAL,
        PERLIN_FRACTAL,
    }

    public Noise(Type type) {

        this.type = type;
        this.minimumWeight = 0;
        this.maximumWeight = 1;
        this.time = 0;
        this.opacity = 1;
        this.iterations = 2;
        this.mix = 0.5;
        this.frequency = 0.01;
        this.lacunarity = 2;
        this.octaves = 3;
        this.gain = 1.0;
        this.blend = false;

    }

    public <T extends Iterable<Grid_Point>> T applyWeightToPoints(T points) {

        if (isCloverType(this.type)) {
            CloverNoise.Noise2D noise2D = new CloverNoise.Noise2D();

            applyCloverNoiseToPoints(this.type, points);
        }

        if (isFastType(this.type)) {
            FastNoise fastNoise = new FastNoise();
            setFastNoiseType(this.type, fastNoise);
            fastNoise.SetFrequency((float)this.frequency);
            fastNoise.SetFractalOctaves(this.octaves);
            fastNoise.SetFractalLacunarity((float)this.lacunarity);
            fastNoise.SetFractalGain((float)this.gain);
            fastNoise.SetInterp(FastNoise.Interp.Hermite);

            applyFastNoiseToPoints(fastNoise, points);
        }

        if (this.type == Type.PERLIN) {
            applyPerlinNoiseToPoints(points);
        }

        if (this.type == Type.RANDOM) {
            applyRandomNoiseToPoints(points);
        }

        return points;

    }

    private <T extends Iterable<Grid_Point>> void applyCloverNoiseToPoints(Type type, T pg) {

        // TODO: think about extracting CloverNoise to allow for a version of this that doens't call a switch statement on each iter (check if JIT optimizes type here)

        CloverNoise.Noise2D noise = new CloverNoise.Noise2D();

        for (Grid_Point point : pg) {
            if (this.blend) point.weight = Helpers.clamp(
                    point.weight + Helpers.map(getCloverNoiseValueForPoint(type, noise, point), 0, 1, this.minimumWeight, this.maximumWeight) * this.opacity, 0.0, 1.0);
            else
                point.weight = Helpers.map(getCloverNoiseValueForPoint(type, noise, point), 0, 1, this.minimumWeight, this.maximumWeight) * this.opacity;
        }

    }

    private double getCloverNoiseValueForPoint(Type type, CloverNoise.Noise2D noise, Grid_Point point) {

        switch(type) {
            case CLOVER_2D: return noise.noise(point.xPos, point.yPos);
            case CLOVER_FRACTAL: return noise.fractalNoise(point.xPos, point.yPos, this.iterations);
            case CLOVER_FROST: noise.frostNoise(point.xPos, point.yPos);
            case CLOVER_MARBLE: noise.marbleNoise(point.xPos, point.yPos);
            case CLOVER_CURL: {
                CloverNoise.Vector2 curl = noise.curlNoise(point.xPos, point.yPos);
                return Helpers.mix(curl.getX(), curl.getY(), this.mix);
            }
            case CLOVER_CURL_FRACTAL: {
                CloverNoise.Vector2 curl = noise.fractalCurlNoise(point.xPos, point.yPos, this.iterations);
                return Helpers.mix(curl.getX(), curl.getY(), this.mix);
            }
            default: return 0;
        }

    }

    private <T extends Iterable<Grid_Point>> void applyFastNoiseToPoints(FastNoise noise, T points) {

        for (Grid_Point point : points) {
            double weight = noise.GetNoise(point.xPos, point.yPos, this.time);
            if (this.blend) point.weight = Helpers.clamp(
                    point.weight + Helpers.map(weight, 0, 1, this.minimumWeight, this.maximumWeight) * this.opacity, 0.0, 1.0);
            else point.weight = Helpers.map(weight, 0, 1, this.minimumWeight, this.maximumWeight) * this.opacity;
        }

    }

    private void setFastNoiseType(Type type, FastNoise noise) {

        switch(type) {
            case VALUE -> noise.SetNoiseType(FastNoise.NoiseType.Value);
            case VALUE_FRACTAL -> noise.SetNoiseType(FastNoise.NoiseType.ValueFractal);
            case SIMPLEX -> noise.SetNoiseType(FastNoise.NoiseType.Simplex);
            case SIMPLEX_FRACTAL -> noise.SetNoiseType(FastNoise.NoiseType.SimplexFractal);
            case CELLULAR -> noise.SetNoiseType(FastNoise.NoiseType.Cellular);
            case CUBIC -> noise.SetNoiseType(FastNoise.NoiseType.Cubic);
            case CUBIC_FRACTAL -> noise.SetNoiseType(FastNoise.NoiseType.CubicFractal);
            case PERLIN_FRACTAL -> noise.SetNoiseType(FastNoise.NoiseType.PerlinFractal);
        }

    }

    private <T extends Iterable<Grid_Point>> void applyPerlinNoiseToPoints(T points) {

        // Apply weights to points based on Perlin Noise.
        // Where:
        // _min -> Min weight
        // _max -> Max weight
        // _time -> Time (Z-axis) factor for animating Perlin (takes values from 0.0 - 1.0);
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights
        for (Grid_Point point : points) {
            if (this.blend) point.weight = Helpers.clamp(point.weight +
                    Helpers.map(Core.processing.noise(point.xPos, point.yPos, this.time), 0, 1, this.minimumWeight, this.maximumWeight)
                            * this.opacity, 0.0, 1.0);
            else
                point.weight = Helpers.map(Core.processing.noise(point.xPos, point.yPos, this.time), 0, 1, this.minimumWeight, this.maximumWeight) * this.opacity; // Call Perlin ~
        }

    }

    private <T extends Iterable<Grid_Point>> void applyRandomNoiseToPoints(T points) {

        // Apply weights to points randomly.
        // Where:
        // _min -> Min weight threshold
        // _max -> Max weight threshold
        // _blend -> Whether to blend weight with any previous weight present in Point_Grid
        // _opacity -> Opacity of applied weights

        for (Grid_Point point : points) {
            if (this.blend) point.weight = Helpers.clamp(point.weight +
                    Helpers.map(Core.processing.random(0, 1), 0.0, 1.0, this.minimumWeight, this.maximumWeight)
                            * this.opacity, 0, 1);
            else
                point.weight = Helpers.map(Core.processing.random(0, 1), 0, 1, this.minimumWeight, this.maximumWeight) * this.opacity;
        }

    }

    private boolean isCloverType(Type type) {

        return switch(type) {
            case CLOVER_2D, CLOVER_FRACTAL, CLOVER_FROST, CLOVER_MARBLE, CLOVER_CURL, CLOVER_CURL_FRACTAL -> true;
            default -> false;
        };

    }

    private boolean isFastType(Type type) {

        return switch(type) {
            case VALUE, VALUE_FRACTAL, SIMPLEX, SIMPLEX_FRACTAL, CELLULAR, CUBIC, CUBIC_FRACTAL, PERLIN_FRACTAL -> true;
            default -> false;
        };

    }

}
