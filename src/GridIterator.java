import java.util.ArrayList;
import java.util.Iterator;

// Custom Iterator for Point_Grid
// essentially flattens points without having to copy into new data structure
// allows for DRY methods that deal with both grids and lists.

public class GridIterator implements Iterator<GridPoint> {

    private final ArrayList<ArrayList<GridPoint>> points;
    private int currentPosition;

    public GridIterator(ArrayList<ArrayList<GridPoint>> _points) {
        points = _points;
        currentPosition = 0;
    }

    @Override
    public boolean hasNext() {
        return (currentPosition < points.size() * points.get(0).size());
    }

    @Override
    public GridPoint next() {
        int x = Math.floorMod(currentPosition, points.size());
        int y = currentPosition / points.size();
        currentPosition++;
        return points.get(x).get(y);
    }

    @Override
    public void remove() {};

}
