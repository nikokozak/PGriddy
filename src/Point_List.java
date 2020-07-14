import java.util.ArrayList;

public class Point_List {

    // Point_List is used to contain lists of Grid_Points, mainly used
    // when dealing with pattern extraction from Grids.
    // Point_List is null terminated to allow for checks, voiding the need to
    // re-populate the list during every draw cycle.
    // Where:
    // points -> ArrayList<Grid_Point> of points

    public ArrayList<Grid_Point> points;

    public Point_List() {
       this.points = new ArrayList<Grid_Point>();
    }

    public Point_List(int _size) {
       this.points = new ArrayList<Grid_Point>(_size);
    }

    public Point_List(ArrayList<Grid_Point> _list) {
        this.points = Helpers.clonePoints(_list);
        this.points.add(null);
    }

}
