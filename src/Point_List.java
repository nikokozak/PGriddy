import java.util.*;

public class Point_List implements Iterable<Grid_Point>{

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

    //** ============= ITERATOR ================= **//

    @Override
    public Iterator<Grid_Point> iterator() {
        return this.points.iterator();
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

    public Point_List get_range(int _start, int _end) {

        return Getters.get_list_point_range(_start, _end, this);

    }

    public Point_List get_every(int _x) {

        return Getters.get_list_every_other(_x, this);

    }

    // * ================ POINT MOVERS =================== * //

    public Point_List move(int _x, int _y) {

        return Move.move(_x, _y, this);

    }

    public Point_List move_mult(int _x, int _y) {

        return Move.multiply_posns(_x, _y, this);

    }

    public Point_List move_to(int _x, int _y) {

        return Move.list_to(_x, _y, this);

    }

    public Point_List move_reset() {

        return Move.reset_posns(this);

    }

    // * ================ UNIVERSAL APPLICATORS =================== * //

    public void color(int _col) {

        Applicators.color(_col, this);

    }

    public void weight(double _weight) {

        Applicators.weight(_weight, this);

    }

    public void weight_add(double _to_add) {

        Applicators.weight_add(_to_add, this);

    }

    public void weight_multiply(double _factor) {

        Applicators.weight_multiply(_factor, this);

    }

    public void weight_reset() {

        Applicators.weight_reset(this);

    }

    public void filter(double _low, double _high) {

        Applicators.weight_filter(_low, _high, this);

    }

    // * ================ NOISE APPLICATORS =================== * //

    public Point_List perlin(double _min, double _max, float _time, boolean _blend, double _opacity) {

        return Noise.apply_perlin(_min, _max, _time, _blend, _opacity, this);

    }

    public Point_List random(double _min, double _max, boolean _blend, double _opacity) {

        return Noise.apply_random(_min, _max, _blend, _opacity, this);

    }

    public Point_List clover_2D(double _min, double _max, boolean _blend, double _opacity) {

       return Noise.apply_clover_2D(_min, _max, _blend, _opacity, this);

    }

    public Point_List fractal(double _min, double _max, int _iterations, boolean _blend, double _opacity) {

        return Noise.apply_clover_fractal(_min, _max, _iterations, _blend, _opacity, this);

    }

    public Point_List frost(double _min, double _max, boolean _blend, double _opacity) {

        return Noise.apply_clover_frost(_min, _max, _blend, _opacity, this);

    }

    public Point_List marble(double _min, double _max, boolean _blend, double _opacity) {

        return Noise.apply_clover_marble(_min, _max, _blend, _opacity, this);

    }

    public Point_List curl(double _min, double _max, double _mix, boolean _blend, double _opacity) {

        return Noise.apply_clover_curl(_min, _max, _mix, _blend, _opacity, this);

    }

    public Point_List curl_fractal(double _min, double _max, int _iterations, double _mix, boolean _blend, double _opacity) {

        return Noise.apply_clover_curlFractal(_min, _max, _iterations, _mix, _blend, _opacity, this);

    }


    public Point_List noise_value(double _min, double _max, double _time, double _freq, boolean _blend, double _opacity) {

        return Noise.apply_value(_min, _max, _time, _freq, _blend, _opacity, this);

    }

    public Point_List noise_value_fractal(double _min, double _max, double _time, double _freq, int _octaves, double _lacunarity, double _gain, boolean _blend, double _opacity) {

        return Noise.apply_value_fractal(_min, _max, _time, _freq, _octaves, _lacunarity, _gain, _blend, _opacity, this);

    }

    public Point_List noise_simplex(double _min, double _max, double _time, double _freq, boolean _blend, double _opacity) {

        return Noise.apply_simplex(_min, _max, _time, _freq, _blend, _opacity, this);

    }

    public Point_List noise_simplex_fractal(double _min, double _max, double _time, double _freq, int _octaves, double _lacunarity, double _gain, boolean _blend, double _opacity) {

        return Noise.apply_simplex_fractal(_min, _max, _time, _freq, _octaves, _lacunarity, _gain, _blend, _opacity, this);

    }

    public Point_List noise_cellular(double _min, double _max, double _time, double _freq, boolean _blend, double _opacity) {

        return Noise.apply_cellular(_min, _max, _time, _freq, _blend, _opacity, this);

    }

    public Point_List noise_cubic(double _min, double _max, double _time, double _freq, boolean _blend, double _opacity) {

        return Noise.apply_cubic(_min, _max, _time, _freq, _blend, _opacity, this);

    }

    public Point_List noise_cubic_fractal(double _min, double _max, double _time, double _freq, int _octaves, double _lacunarity, double _gain, boolean _blend, double _opacity) {

        return Noise.apply_cubic_fractal(_min, _max, _time, _freq, _octaves, _lacunarity, _gain, _blend, _opacity, this);

    }

    public Point_List noise_perlin_fractal(double _min, double _max, double _time, double _freq, int _octaves, double _lacunarity, double _gain, boolean _blend, double _opacity) {

        return Noise.apply_perlin_fractal(_min, _max, _time, _freq, _octaves, _lacunarity, _gain, _blend, _opacity, this);

    }

}
