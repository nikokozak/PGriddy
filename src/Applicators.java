import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Iterator;

public class Applicators {

    public static Point_Grid setWeights(double _weight, Point_Grid _pg) {

        // Sets all weights in a given Point_Grid
        // Where:
        // _weight -> new weight (value between 0 and 1)
        // _pg -> Point_Grid to affect

        _weight = Helpers.clamp(_weight, 0, 1);
        Point_Grid result = new Point_Grid(_pg);

        Iterator<ArrayList<Grid_Point>> iter_x = result.points.iterator();
        Iterator<Grid_Point> iter_y;
        Grid_Point currPoint;

        while (iter_x.hasNext()) {
            iter_y = iter_x.next().iterator();
            while (iter_y.hasNext()) {
                currPoint = iter_y.next();
                currPoint.weight = _weight;
            }
        }

        return result;

    }

    public static Point_Grid addToWeights(double _weight, Point_Grid _pg) {

        // Adds a given number to all weights in a given Point_Grid
        // Where:
        // _weight -> amount to add (value between 0 and 1)
        // _pg -> Point_Grid to affect

        Point_Grid result = new Point_Grid(_pg);

        Iterator<ArrayList<Grid_Point>> iter_x = result.points.iterator();
        Iterator<Grid_Point> iter_y;
        Grid_Point currPoint;

        while (iter_x.hasNext()) {
            iter_y = iter_x.next().iterator();
            while (iter_y.hasNext()) {
                currPoint = iter_y.next();
                currPoint.weight = Helpers.clamp(currPoint.weight + _weight, 0, 1);
            }
        }

        return result;

    }

    public static Point_Grid addToPositions(float _x, float _y, Point_Grid _pg) {

        // Moves points in a grid by adding the provided values to X and Y coordinates, scaled according to each point's weight.
        // Where:
        // _x -> amount to add to GRID_POINT.x
        // _y -> amount to add to GRID_POINT.y
        // _pg -> Point_Grid to affect

        Point_Grid result = new Point_Grid(_pg);
        Grid_Point currpoint;

        for (int x = 0; x < _pg.x; x++) {
            for (int y = 0; y < _pg.y; y++) {
                currpoint = result.points.get(x).get(y);
                currpoint.x += _x * currpoint.weight;
                currpoint.y += _y * currpoint.weight;
            }
        }

        return result;

    }

    public static Point_Grid addToPositions(float _x, float _y, Selection _s, Point_Grid _pg) {

        // Moves points in a grid by adding the provided values to X and Y coordinates, scaled according to each point's weight.
        // Incorporates a selection option.
        // Where:
        // _x -> amount to add to GRID_POINT.x
        // _y -> amount to add to GRID_POINT.y
        // _pg -> Point_Grid to affect

        Point_Grid result = new Point_Grid(_pg);
        Grid_Point currpoint;

        for (int x = _s.startCol; x <= _s.endCol; x++) {
            for (int y = _s.startRow; y <= _s.endRow; y++) {
                currpoint = result.points.get(x).get(y);
                currpoint.x += _x * currpoint.weight;
                currpoint.y += _y * currpoint.weight;
            }
        }

        return result;

    }

    public static Point_Grid multPositions(float _x, float _y, Point_Grid _pg) {

        // Moves points in a grid by multiplying the provided values to X and Y coordinates, scaled according to each point's weight.
        // Where:
        // _x -> amount to add to GRID_POINT.x
        // _y -> amount to add to GRID_POINT.y
        // _pg -> Point_Grid to affect

        Point_Grid result = new Point_Grid(_pg);
        Grid_Point currpoint;

        for (int x = 0; x < _pg.x; x++) {
            for (int y = 0; y < _pg.y; y++) {
                currpoint = result.points.get(x).get(y);
                currpoint.x *= _x * currpoint.weight;
                currpoint.y *= _y * currpoint.weight;
            }
        }

        return result;

    }

    public static Point_Grid multPositions(float _x, float _y, Selection _s, Point_Grid _pg) {

        // Moves points in a grid by multiplying the provided values to X and Y coordinates, scaled according to each point's weight.
        // Incorporates a selection option.
        // Where:
        // _x -> amount to add to GRID_POINT.x
        // _y -> amount to add to GRID_POINT.y
        // _pg -> Point_Grid to affect

        Point_Grid result = new Point_Grid(_pg);
        Grid_Point currpoint;

        for (int x = _s.startCol; x <= _s.endCol; x++) {
            for (int y = _s.startRow; y <= _s.endRow; y++) {
                currpoint = result.points.get(x).get(y);
                currpoint.x *= _x * currpoint.weight;
                currpoint.y *= _y * currpoint.weight;
            }
        }

        return result;

    }


}

// TODO: REDO THE WHOLE THING IN STANDARD OR VECTOR FORM -> SIMPLIFIES SLOPE, ETC
/*
// INT, INT, INT, INT, DOUBLE, DOUBLE, POINT_GRID -> POINT_GRID
// Modifies weights of Grid_Points in a given Point_Grid according to a linear gradient, returns a new Point_Grid
// Where:
// _col0, _row0 -> beginning of gradient line
// _col1, _ro1 -> end of gradient line
// _init_decay -> initial weight value for gradient
// _decay -> unit of decay per increase in perpendicular line offset (b)
// _pg -> Point_Grid to sample from
Point_Grid applyLinGradient(int _col0, int _row0, int _col1, int _row1, double _init_weight, double _decay, Point_Grid _pg) {
  //TODO: Figure out horizontal line checks
  //TODO: Refactor (helpers, etc.)
  //TODO: Remove duplicate checks
  Point_Grid result = new Point_Grid(_pg, true); // Create zero-weighted Grid

  // y = m(slope)x + b(intercept)
  // y = -1/m(inverse_slope) + b(intercept)

  double slope_guide =  (double)(_row1 - _row0) / (double)(_col1 - _col0);
  double inverse_slope = -1 / slope_guide;

  double p1_y_intercept = _row0 - inverse_slope * _col0; // NORMALIZE B_OFFSET SO WE DONT HAVE TO CHECK BELOW
  double p2_y_intercept = _row1 - inverse_slope * _col1;
  double limit_y_intercept = (_pg.y - 1) - inverse_slope * (_pg.x - 1);

  int x_col_limit = _pg.x - 1;
  int y_row_limit = _pg.y - 1;

  //double curr_offset = b_offset_1;
  double curr_intercept = p1_y_intercept > p2_y_intercept ? limit_y_intercept : 0;
  double sampling_intercept = p1_y_intercept > p2_y_intercept ? curr_intercept - 1 : curr_intercept + 1;

  double intercept_gradient_end = Math.max(p1_y_intercept, p2_y_intercept); // Create condition that encompasses both b1 > b2 && b1 < b2
  double intercept_gradient_begin = Math.min(p1_y_intercept, p2_y_intercept);
  double intercept_counter = 0;


  double curr_weight = _init_weight;

  while (intercept_counter <= intercept_gradient_end) {

    double y1, y2;
    int y1R, y2R;

    for (int x = 0; x <= x_col_limit; x++) {

      y1 = inverse_slope*x+curr_intercept;
      y2 = inverse_slope*x+sampling_intercept;

      y1R = slope_guide == 0 ? 0 : (int)Math.floor(Math.min(y1, y2)); // Check for 0 slope to avoid OOB.
      y2R = slope_guide == 0 ? y_row_limit: (int)Math.ceil(Math.max(y1, y2));

      if (y1R > -1 && y2R < y_row_limit) {
        for (int y = y1R; y <= y2R; y++) {

          if (intercept_counter >= intercept_gradient_begin) {
            result.points.get(x).get(y).weight = clamp(curr_weight, 0, 1);
          } else {
            result.points.get(x).get(y).weight = _init_weight;
          }
        }
      }
    }

    if (p1_y_intercept > p2_y_intercept) {
      curr_intercept--;
      sampling_intercept--;
    } else {
      curr_intercept++;
      sampling_intercept++;
    }

    intercept_counter++;
    if (intercept_counter >= intercept_gradient_begin) {
      curr_weight -= _decay;
    }

  }

  return result;

}
*/
