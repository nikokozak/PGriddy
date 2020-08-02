import java.util.ArrayList;
import java.util.Iterator;

// Custom Iterator for Point_Grid
// essentially flattens points without having to copy into new data structure
// allows for DRY methods that deal with both grids and lists.

public class Grid_Iterator implements Iterator<Grid_Point> {

    private final ArrayList<ArrayList<Grid_Point>> points;
    private int currentPosition;

    public Grid_Iterator(ArrayList<ArrayList<Grid_Point>> _points) {
        points = _points;
        currentPosition = 0;
    }

    @Override
    public boolean hasNext() {
        return (currentPosition < points.size() * points.get(0).size());
    }

    @Override
    public Grid_Point next() {
        int x = Math.floorMod(currentPosition, points.size());
        int y = currentPosition / points.size();
        currentPosition++;
        return points.get(x).get(y);
    }

    @Override
    public void remove() {};

}
