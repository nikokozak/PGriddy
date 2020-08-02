public class Gradient {

    private int gradientCenterX;
    private int gradientCenterY; // center of gradient in GridX and GridY coordinates.
    private double radius;
    private double initWeight;
    private double minWeight;
    private double maxWeight;
    private double frequency;
    private double shift;
    private boolean inverse;
    private boolean blend;
    private boolean subtract;
    private double opacity;
    private double feather;
    private final Type type;

    public Type getType() {
        return type;
    }

    public int gradientCenterX() {
        return gradientCenterX;
    }

    public void gradientCenterX(int gradientCenterX) {
        this.gradientCenterX = gradientCenterX;
    }

    public int gradientCenterY() {
        return gradientCenterY;
    }

    public void gradientCenterY(int gradientCenterY) {
        this.gradientCenterY = gradientCenterY;
    }

    public double radius() {
        return radius;
    }

    public void radius(double radius) {
        this.radius = radius;
    }

    public double initWeight() {
        return initWeight;
    }

    public void initWeight(double initWeight) {
        this.initWeight = initWeight;
    }

    public double minWeight() {
        return minWeight;
    }

    public void minWeight(double minWeight) {
        this.minWeight = minWeight;
    }

    public double maxWeight() {
        return maxWeight;
    }

    public void maxWeight(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public double frequency() {
        return frequency;
    }

    public void frequency(double frequency) {
        this.frequency = frequency;
    }

    public double shift() {
        return shift;
    }

    public void shift(double shift) {
        this.shift = shift;
    }

    public boolean isInverse() {
        return inverse;
    }

    public void inverse(boolean inverse) {
        this.inverse = inverse;
    }

    public boolean isBlend() {
        return blend;
    }

    public void blend(boolean blend) {
        this.blend = blend;
    }

    public boolean isSubtract() {
        return subtract;
    }

    public void subtract(boolean subtract) {
        this.subtract = subtract;
    }

    public double opacity() {
        return opacity;
    }

    public void opacity(double opacity) {
        this.opacity = opacity;
    }

    public double feather() {
        return feather;
    }

    public void feather(double feather) {
        this.feather = feather;
    }

    public enum Type {
        RADIAL,
        RADIAL_SLOW,
        RADIAL_SMOOTH,
        RADIAL_SMOOTH_SLOW,
        PERIODIC,
        PERIODIC_SLOW
    }

    public Gradient(Type type) {

        this.type = type;
        this.radius(10.0);
        this.initWeight(1.0);
        this.minWeight(0.0);
        this.maxWeight(1.0);
        this.frequency(0.5);
        this.shift(2*Math.PI);
        this.feather(1.0);
        this.inverse(false);
        this.blend(false);
        this.subtract(false);
        this.opacity(1.0);
        this.gradientCenterX(-1); // -1 used to mark uninitialized centerPoint
        this.gradientCenterY(-1);

    }

    public PointGrid applyWeightsToPoints(PointGrid pg) {

        for (GridPoint currPoint : pg) {

            switch (this.type) {
                case RADIAL -> applyWeightToPoint(false, Type.RADIAL, currPoint, pg);
                case RADIAL_SLOW -> applyWeightToPoint(true, Type.RADIAL, currPoint, pg);
                case RADIAL_SMOOTH -> applyWeightToPoint(false, Type.RADIAL_SMOOTH, currPoint, pg);
                case RADIAL_SMOOTH_SLOW -> applyWeightToPoint(true, Type.RADIAL_SMOOTH, currPoint, pg);
                case PERIODIC -> applyWeightToPoint(false, Type.PERIODIC, currPoint, pg);
                case PERIODIC_SLOW -> applyWeightToPoint(true, Type.PERIODIC, currPoint, pg);
            };

        }

        return pg;

    }

    private void applyWeightToPoint(boolean slow, Type type, GridPoint currPoint, PointGrid pg) {

        GridPoint centerPoint = makeDefaultCenterPoint(pg);

        double maximumDistance = slow?
                getFarthestDistanceFromPointExact(centerPoint.gridIndexX(), centerPoint.gridIndexY(), pg) :
                getFarthestDistanceFromPointApprox(centerPoint.gridIndexX(), centerPoint.gridIndexY(), pg);

        double currentDistance = slow? pg.gridExactDist(currPoint, centerPoint) : pg.gridApproxDist(currPoint, centerPoint);
        double radius = (slow? this.radius() : Math.pow(this.radius(), 2)) - maximumDistance;
        double weight = 0;

        switch(type) {
            case RADIAL -> weight = getRadialWeightForPoint(radius, currentDistance, maximumDistance);
            case RADIAL_SMOOTH -> weight = getSmoothWeightForPoint(radius, currentDistance, maximumDistance);
            case PERIODIC -> weight = getSinWeightForPoint(radius, currentDistance, maximumDistance);
        }

        currPoint.weight(this.isBlend() ? clampWeight(this.isSubtract() ? (currPoint.weight() - weight) : (currPoint.weight() + weight)) : weight);

    }

    private double getRadialWeightForPoint (double radius, double distanceToCenter, double maxDistance) {
        double minimumDistance = 0;
        double weight;

        if (this.isInverse()) weight = Helpers.map(distanceToCenter, minimumDistance, maxDistance + radius, 0, this.initWeight()) * this.opacity();
        else weight = Helpers.map(distanceToCenter, maxDistance + radius, minimumDistance, 0, this.initWeight()) * this.opacity();

        return weight;

    }

    private double getSmoothWeightForPoint (double radius, double distanceToCenter, double maxDistance) {
        double minimumDistance = 0;
        double weight;

        if (this.isInverse()) weight = Helpers.easeInOutCubic(Helpers.map(distanceToCenter, minimumDistance, maxDistance + radius, 0, this.initWeight()), this.initWeight(), this.initWeight(), this.feather()) * this.opacity();
        else weight = Helpers.easeInOutCubic(Helpers.map(distanceToCenter, maxDistance + radius, minimumDistance, 0, this.initWeight()), 0, this.initWeight(), this.feather()) * this.opacity();

        return weight;

    }

    private double getSinWeightForPoint (double radius, double distanceToCenter, double maxDistance) {
        double minimumDistance = 0;
        double weight;

        if (this.isInverse()) weight = Helpers.map(Helpers.sinMap(Helpers.map(distanceToCenter, minimumDistance, maxDistance + radius, 0, 2*Math.PI), this.frequency(), this.shift(), this.maxWeight()), 1, -1, this.minWeight(), 1) * this.opacity();
        else weight = Helpers.map(Helpers.sinMap(Helpers.map(distanceToCenter, minimumDistance, maxDistance, 0, 2*Math.PI), this.frequency(), this.shift(), this.maxWeight()), -1, 1, this.minWeight(), 1) * this.opacity();

        return weight;

    }

    private double clampWeight (double weight) {

        return Helpers.clamp(weight, 0, 1);
    }

    private GridPoint makeDefaultCenterPoint(PointGrid pg) {

        if (this.gradientCenterX() == -1 || this.gradientCenterY() == -1) {
            return pg.getPoint(pg.xPoints() / 2, pg.yPoints() / 2);
        } else {
            return pg.getPoint(this.gradientCenterX(), this.gradientCenterY());
        }

    }

    private static double getFarthestDistanceFromPointExact(int _col, int _row, PointGrid _pg) {

        // Returns the greatest distance from current point possible within a given grid,
        // using Pythagorean method. Use approx version of this function if performance is a concern.
        // Where:
        // _col, _row -> grid coordinates of point to be sampled from.
        // _pg -> grid to sample from.

        double max_distance = 0;
        GridPoint point = Getters.getGridPoint(_col, _row, _pg);

        switch(_pg.checkQuad(point)) {
            case 1 -> max_distance = _pg.gridExactDist(point, Getters.getGridPoint(0, _pg.yPoints() - 1, _pg));
            case 2 -> max_distance = _pg.gridExactDist(point, Getters.getGridPoint(_pg.xPoints() - 1, _pg.yPoints() - 1, _pg));
            case 3 -> max_distance = _pg.gridExactDist(point, Getters.getGridPoint(_pg.xPoints() - 1, 0, _pg));
            case 4 -> max_distance = _pg.gridExactDist(point, Getters.getGridPoint(0, 0, _pg));
        }

        return max_distance;

    }

    private static double getFarthestDistanceFromPointApprox(int _col, int _row, PointGrid _pg) {

        // Returns the greatest distance from current point possible within a given grid,
        // using a relative dist approximation method.
        // Where:
        // _col, _row -> grid coordinates of point to be sampled from.
        // _pg -> grid to sample from.

        double max_distance = 0;
        GridPoint point = Getters.getGridPoint(_col, _row, _pg);

        switch(_pg.checkQuad(point)) {
            case 1 -> max_distance = _pg.gridApproxDist(point, Getters.getGridPoint(0, _pg.yPoints() - 1, _pg));
            case 2 -> max_distance = _pg.gridApproxDist(point, Getters.getGridPoint(_pg.xPoints() - 1, _pg.yPoints() - 1, _pg));
            case 3 -> max_distance = _pg.gridApproxDist(point, Getters.getGridPoint(_pg.xPoints() - 1, 0, _pg));
            case 4 -> max_distance = _pg.gridApproxDist(point, Getters.getGridPoint(0, 0, _pg));
        }

        return max_distance;

    }

}
