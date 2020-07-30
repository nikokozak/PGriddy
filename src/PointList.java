import java.util.*;

public class PointList implements Iterable<Grid_Point>{

    // Point_List is used to contain lists of Grid_Points, mainly used
    // when dealing with pattern extraction from Grids.
    // Point_List is null terminated to allow for checks, voiding the need to
    // re-populate the list during every draw cycle in certain functions (like PatternGetters).
    // Where:
    // points -> ArrayList<Grid_Point> of points

    public ArrayList<Grid_Point> points;
    private boolean is_capped = false;

    public PointList() {
       this.points = new ArrayList<Grid_Point>();
    }

    public PointList(int _size) {
       this.points = new ArrayList<Grid_Point>(_size);
    }

    public PointList(Grid_Point... pts) {
        this.points = new ArrayList<Grid_Point>(pts.length);
        this.points.addAll(Arrays.asList(pts));
    }

    public PointList(ArrayList<Grid_Point> _list) {
        this.points = Helpers.cloneGridPoints(_list);
    }

    public PointList(Collection<Grid_Point> _list) {
        this.points = new ArrayList<Grid_Point>(_list);
    }

    //** ============= ITERATOR ================= **//

    @Override
    public Iterator<Grid_Point> iterator() {
        return this.points.iterator();
    }

    //** ============= UTILS ================= **//

    public PointList add(Grid_Point _p) {

        // Adds a point to a point list if list is not capped.

       if (!this.is_capped()) this.points.add(_p);
       return this;

    }

    public PointList add(Grid_Point... pts) {

        // Adds a series of points to a list if it is not capped.

        if (!this.is_capped()) this.points.addAll(Arrays.asList(pts));
        return this;
    }

    public PointList add_all(PointList _pl) {

        // Appends given points onto the end of point list.

        this.points.addAll(_pl.points);
        return this;

    }

    public PointList add_all_cloned(PointList _pl) {

        // Appends given points onto the end of point list, clones them first.

        this.points.addAll(Helpers.cloneGridPoints(_pl.points));
        return this;

    }

    public PointList remove(int _index) {

        // Removes a given point from the list.

        if (!this.is_empty()) this.points.remove(_index);
        return this;

    }

    public PointList clear(int _start, int _end) {

        // Removes all points from the list.

        if (!this.is_empty()) this.points.clear();
        return this;

    }

    public int size() {

        // Returns the size of the point list.

        return this.points.size();

    }

    public PointList cap() {

        // Adds null to the end of point list to mark the list as terminated.

        if (!this.is_capped) this.is_capped = true;
        return this;

    }

    public boolean is_capped() {

        // Returns true if a Point_List is capped (null as last item).

        return this.is_capped;

    }

    public boolean is_empty() {

        // Returns true if a Point_List is empty.

        return this.points.isEmpty();

    }

    public PointList remove_duplicates() {

        // Removes duplicate points from Point_List

        Set<Grid_Point> set = new LinkedHashSet<>(this.points.size());
        set.addAll(set);
        this.points.clear();
        this.points.addAll(set);

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

    public Grid_Point get(int _index) {

       return Getters.get_list_point(_index, this);

    }

    public PointList get_range(int _start, int _end) {

        return Getters.get_list_point_range(_start, _end, this);

    }

    public PointList get_every(int _x) {

        return Getters.get_list_every_other(_x, this);

    }

    public PointList get_points_by_weight(double min, double max) {

        return Getters.get_points_by_weight(min, max, this);

    }

    // * ================ POINT MOVERS =================== * //

    public PointList move(int _x, int _y) {

        return Move.move(_x, _y, this);

    }

    public PointList move_mult(int _x, int _y) {

        return Move.multiply_posns(_x, _y, this);

    }

    public PointList move_to(int _x, int _y) {

        return Move.list_to(_x, _y, this);

    }

    public PointList move_reset() {

        return Move.reset_posns(this);

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

    public PointList weight_add(double _to_add) {

        Applicators.weight_add(_to_add, this);
        return this;

    }

    public PointList weight_multiply(double _factor) {

        Applicators.weight_multiply(_factor, this);
        return this;

    }

    public PointList weight_reset() {

        Applicators.weight_reset(this);
        return this;

    }

    public PointList filter(double _low, double _high) {

        Applicators.weight_filter(_low, _high, this);
        return this;

    }

    // * ================ NOISE APPLICATORS =================== * //

    public PointList applyNoise(Noise noise) {

        noise.applyWeightToPoints(this);
        return this;

    }

}
