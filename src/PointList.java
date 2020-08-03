import java.util.*;

public class PointList implements Iterable<GridPoint>{

    // Point_List is used to contain lists of Grid_Points, mainly used
    // when dealing with pattern extraction from Grids.
    // Point_List is null terminated to allow for checks, voiding the need to
    // re-populate the list during every draw cycle in certain functions (like PatternGetters).
    // Where:
    // points -> ArrayList<Grid_Point> of points

    private ArrayList<GridPoint> points;
    private boolean is_capped = false;

    public PointList() {
       this.points(new ArrayList<GridPoint>());
    }

    public PointList(int _size) {
       this.points(new ArrayList<GridPoint>(_size));
    }

    public PointList(GridPoint... pts) {
        this.points(new ArrayList<GridPoint>(pts.length));
        this.points().addAll(Arrays.asList(pts));
    }

    public PointList(ArrayList<GridPoint> _list) {
        this.points(Helpers.cloneGridPoints(_list));
    }

    public PointList(Collection<GridPoint> _list) {
        this.points(new ArrayList<GridPoint>(_list));
    }

    //** ============= ITERATOR ================= **//

    @Override
    public Iterator<GridPoint> iterator() {
        return this.points().iterator();
    }

    //** ============= UTILS ================= **//

    public PointList add(GridPoint _p) {

        // Adds a point to a point list if list is not capped.

       if (!this.isCapped()) this.points().add(_p);
       return this;

    }

    public PointList add(GridPoint... pts) {

        // Adds a series of points to a list if it is not capped.

        if (!this.isCapped()) this.points().addAll(Arrays.asList(pts));
        return this;
    }

    public PointList addAll(PointList _pl) {

        // Appends given points onto the end of point list.

        this.points().addAll(_pl.points());
        return this;

    }

    public PointList addAllCloned(PointList _pl) {

        // Appends given points onto the end of point list, clones them first.

        this.points().addAll(Helpers.cloneGridPoints(_pl.points()));
        return this;

    }

    public PointList remove(int _index) {

        // Removes a given point from the list.

        if (!this.isEmpty()) this.points().remove(_index);
        return this;

    }

    public PointList clear(int _start, int _end) {

        // Removes all points from the list.

        if (!this.isEmpty()) this.points().clear();
        return this;

    }

    public int size() {

        // Returns the size of the point list.

        return this.points().size();

    }

    public PointList cap() {

        // Adds null to the end of point list to mark the list as terminated.

        if (!this.isCapped()) this.isCapped(true);
        return this;

    }

    public boolean isEmpty() {

        // Returns true if a Point_List is empty.

        return this.points().isEmpty();

    }

    public PointList removeDuplicates() {

        // Removes duplicate points from Point_List

        Set<GridPoint> set = new LinkedHashSet<>(this.points().size());
        set.addAll(set);
        this.points().clear();
        this.points().addAll(set);

        return this;

    }

    //** ============= DRAW UTILITIES ================= **//

    public void draw() {

        Draw.draw(this);

    }

    public void draw(int _type) {

        Draw.draw(_type, this);

    }

    public void draw(int _type, boolean _weight) {

        Draw.draw(_type, _weight, this);

    }

    public void draw(int _type, float _size, boolean _weight) {

        Draw.draw(_type, _size, _weight, this);

    }

    //** ============= POINT GETTERS ================= **//

    public GridPoint get(int _index) {

       return Getters.getListPoint(_index, this);

    }

    public PointList getRange(int _start, int _end) {

        return Getters.getListPointRange(_start, _end, this);

    }

    public PointList getEvery(int _x) {

        return Getters.getListEveryOther(_x, this);

    }

    public PointList getPointsByWeight(double min, double max) {

        return Getters.getPointsByWeight(min, max, this);

    }

    // * ================ POINT MOVERS =================== * //

    public PointList move(int _x, int _y) {

        return Move.move(_x, _y, this);

    }

    public PointList moveMult(int _x, int _y) {

        return Move.multiplyPosns(_x, _y, this);

    }

    public PointList moveTo(int _x, int _y) {

        return Move.listTo(_x, _y, this);

    }

    public PointList moveReset() {

        return Move.resetPosns(this);

    }

    // * ================ UNIVERSAL APPLICATORS =================== * //

    public PointList color(int _col) {

        Applicators.color(_col, this);
        return this;

    }

    public PointList weight(double _weight) {

        Applicators.weight(_weight, this);
        return this;

    }

    public PointList weightAdd(double _to_add) {

        Applicators.weightAdd(_to_add, this);
        return this;

    }

    public PointList weightMultiply(double _factor) {

        Applicators.weightMultiply(_factor, this);
        return this;

    }

    public PointList weightReset() {

        Applicators.weightReset(this);
        return this;

    }

    public PointList binaryWeight(double threshold) {

        return Applicators.binaryWeight(threshold, this);

    }

    public PointList filter(double _low, double _high) {

        Applicators.weightFilter(_low, _high, this);
        return this;

    }

    // * ================ NOISE APPLICATORS =================== * //

    public PointList applyNoise(Noise noise) {

        noise.applyWeightToPoints(this);
        return this;

    }

    public ArrayList<GridPoint> points() {
        return points;
    }

    public void points(ArrayList<GridPoint> points) {
        this.points = points;
    }

    public boolean isCapped() {
        return is_capped;
    }

    public void isCapped(boolean is_capped) {
        this.is_capped = is_capped;
    }
}
