import java.util.*;

public class Point_List {

    // Point_List is used to contain lists of Grid_Points, mainly used
    // when dealing with pattern extraction from Grids.
    // Point_List is null terminated to allow for checks, voiding the need to
    // re-populate the list during every draw cycle in certain functions (like PatternGetters).
    // Where:
    // points -> ArrayList<Grid_Point> of points

    public ArrayList<Grid_Point> points;
    private boolean is_capped = false;

    public Point_List() {
       this.points = new ArrayList<Grid_Point>();
    }

    public Point_List(int _size) {
       this.points = new ArrayList<Grid_Point>(_size);
    }

    public Point_List(Grid_Point... pts) {
        this.points = new ArrayList<Grid_Point>(pts.length);
        this.points.addAll(Arrays.asList(pts));
    }

    public Point_List(ArrayList<Grid_Point> _list) {
        this.points = Helpers.cloneGridPoints(_list);
    }

    public Point_List(Collection<Grid_Point> _list) {
        this.points = new ArrayList<Grid_Point>(_list);
    }

    //** ============= UTILS ================= **//

    public Point_List add(Grid_Point _p) {

        // Adds a point to a point list if list is not capped.

       if (!this.is_capped()) this.points.add(_p);
       return this;

    }

    public Point_List add(Grid_Point... pts) {

        // Adds a series of points to a list if it is not capped.

        if (!this.is_capped()) this.points.addAll(Arrays.asList(pts));
        return this;
    }

    public Point_List add_all(Point_List _pl) {

        // Appends given points onto the end of point list.

        this.points.addAll(_pl.points);
        return this;

    }

    public Point_List add_all_cloned(Point_List _pl) {

        // Appends given points onto the end of point list, clones them first.

        this.points.addAll(Helpers.cloneGridPoints(_pl.points));
        return this;

    }

    public Point_List remove(int _index) {

        // Removes a given point from the list.

        if (!this.is_empty()) this.points.remove(_index);
        return this;

    }

    public Point_List clear(int _start, int _end) {

        // Removes all points from the list.

        if (!this.is_empty()) this.points.clear();
        return this;

    }

    public int size() {

        // Returns the size of the point list.

        return this.points.size();

    }

    public Point_List cap() {

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

    public Iterator<Grid_Point> iterator() {

        // Returns iterator for list of points.

        return this.points.iterator();

    }

    public Point_List remove_duplicates() {

        // Removes duplicate points from Point_List

        Set<Grid_Point> set = new LinkedHashSet<>(this.points.size());
        set.addAll(set);
        this.points.clear();
        this.points.addAll(set);

        return this;

    }

    //** ============= DRAW UTILITIES ================= **//

    public void draw() {

        Draw.point_list(this);

    }

    public void draw(int _type) {

        Draw.point_list(_type, this);

    }

    public void draw(int _type, boolean _weight) {

        Draw.point_list(_type, _weight, this);

    }

    public void draw(int _type, float _size, boolean _weight) {

        Draw.point_list(_type, _size, _weight, this);

    }

    //** ============= POINT GETTERS ================= **//

    public Grid_Point get(int _index) {

       return Getters.get_list_point(_index, this);

    }

    public Point_List get_range(int _start, int _end) {

        return Getters.get_list_point_range(_start, _end, this);

    }

    public Point_List get_every(int _x) {

        return Getters.get_list_every_other(_x, this);

    }

    // * ================ POINT MOVERS =================== * //

    public Point_List move(int _x, int _y) {

        return Move.list(_x, _y, this);

    }

    public Point_List move_mult(int _x, int _y) {

        return Move.list_mult(_x, _y, this);

    }

    public Point_List move_to(int _x, int _y) {

        return Move.list_to(_x, _y, this);

    }

    public Point_List move_reset() {

        return Move.list_reset(this);

    }

    // * ================ UNIVERSAL APPLICATORS =================== * //

    public void color(int _col) {

        // Sets all points in the Point_List to _col.
        // Where:
        // _col -> Processing color()

        for (Grid_Point currPoint : this.points) {
            currPoint.col = _col;
        }

    }

    public void weight(double _weight) {

        // Sets the weight of all points in the Point_List to _weight.
        // Where:
        // _weight -> weight to set. DOUBLE[0.0-1.0]

        _weight = Helpers.clamp(_weight, 0.0, 1.0);

        for (Grid_Point currPoint : this.points) {
            currPoint.weight = _weight;
        }

    }

    public void weight_add(double _to_add) {

        // Adds a given weight to all points in the list.
        // Where:
        // _to_add -> weight to add. DOUBLE[0.0-1.0]

        _to_add = Helpers.clamp(_to_add, 0.0, 1.0);

        for (Grid_Point currPoint : this.points) {
            currPoint.weight += _to_add;
        }

    }

    public void weight_multiply(double _factor) {

        // Multiplies the weights of all points by a given number.
        // Where:
        // _factor -> factor by which to multiply

        for (Grid_Point currPoint: this.points) {
            currPoint.weight = Helpers.clamp(currPoint.weight * _factor, 0, 1.0);
        }

    }

    public void weight_reset() {

        // Sets all points in Point_List to weight 1.0

        for (Grid_Point currPoint : this.points) {
            currPoint.weight = 1.0;
        }

    }

    public void filter(double _low, double _high) {

        // Sets all weights outside the threshold to zero.

        for (Grid_Point currPoint : this.points) {
           if (currPoint.weight < _low || currPoint.weight > _high) {
               currPoint.weight = 0;
           }
        }

    }


}
