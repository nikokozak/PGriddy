
// IMPURE
// VECTOR[POINT] -> VOID
// Draws all points in a POINT Vector onto the window
// Where:
// _pv -> Vector to fetch points from
// _type -> Type of Processing object to draw (INT) [1: POINT, 2: CIRCLE, 3: RECT]
void drawPointArray(ArrayList<Grid_Point> _pg, int type, boolean _display_weight) {
  
  int col;
  
  for (int i = 0; i < _pg.size(); i++) {
    
    if (_display_weight) {
      col = weightToRGB(_pg.get(i).weight);
      stroke(col);
      fill(col);
    } else {
      stroke(255);
      fill(255);
    }
    
      switch(type) {
         case 1:
           point(_pg.get(i).x, _pg.get(i).y);
           break;
         case 2:
           circle(_pg.get(i).x, _pg.get(i).y, 5);
           break;
          case 3:
           square(_pg.get(i).x, _pg.get(i).y, 5);
           break;
            
       }
    }
}

// IMPURE
// POINT_GRID -> VOID
// Draws all points of a POINT_GRID pg onto the window
// Where:
// pg -> Point_Grid to draw (POINT_GRID)
// type -> Type of Processing object to draw (INT) [1: POINT, 2: CIRCLE, 3: RECT]
void drawPointGrid(Point_Grid _pg, int _type, boolean _display_weight) {
  
  for (int i = 0; i < _pg.points.size(); i++) {
    
    for (int y = 0; y < _pg.points.get(i).size(); y++) {
      
      if (_display_weight) {
        int col = weightToRGB(_pg.points.get(i).get(y).weight);
        stroke(col);
        fill(col);
      } else {
        stroke(255);
        fill(255);
      }
      
       switch(_type) {
         
         case 1:
           point(_pg.points.get(i).get(y).x, _pg.points.get(i).get(y).y);
           break;
         case 2:
           circle(_pg.points.get(i).get(y).x, _pg.points.get(i).get(y).y, 3);
           break;
          case 3:
           rectMode(CENTER);
           square(_pg.points.get(i).get(y).x, _pg.points.get(i).get(y).y, 5);
           break;
            
       }
    }
  }
}
