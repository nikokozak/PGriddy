import java.util.ArrayList;

public class Draw {

    public static void grid(Point_Grid _pg) {
        // Draws points to screen as a simple Processing point (no fill, weight).

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                Core.processing.point(currPoint.x, currPoint.y);
            }
        }
    }

    public static void grid(int type, Point_Grid _pg) {
        // Draws points to screen as either a Processing point, circle, or rect (size 3 for circle and rect).
        // No fill, or weight set.
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect

        if (type > 2 || type < 0) type = 0;

        Core.processing.rectMode(3); // Set rectMode to CENTER;

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                switch (type) {
                    case 0 -> Core.processing.point(currPoint.x, currPoint.y);
                    case 1 -> Core.processing.circle(currPoint.x, currPoint.y, 3);
                    case 2 -> Core.processing.rect(currPoint.x, currPoint.y, 3, 3);
                }
            }
        }
    }

    public static void grid(int type, boolean weight, Point_Grid _pg) {
        // Draws points to screen as either a Processing point, circle, or rect (size 3 for circle and rect).
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect
        // weight -> whether to set alpha as weight.
        Core.processing.rectMode(3); // Set rectMode to CENTER;

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                if (weight) Core.processing.fill(currPoint.col, Helpers.weightToRGB(currPoint.weight));
                else Core.processing.fill(currPoint.col);
                switch (type) {
                    case 0 -> Core.processing.point(currPoint.x, currPoint.y);
                    case 1 -> Core.processing.circle(currPoint.x, currPoint.y, 3);
                    case 2 -> Core.processing.rect(currPoint.x, currPoint.y, 3, 3);
                }
            }
        }
    }

    public static void grid(int type, float size, boolean weight, Point_Grid _pg) {
        // Draws points to screen as either a Processing point, circle, or rect.
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect
        // size -> size of rect or circle.
        // weight -> whether to set alpha as weight.
        Core.processing.rectMode(3);

        for (ArrayList<Grid_Point> column : _pg.points) {
            for (Grid_Point currPoint : column) {
                if (weight) Core.processing.fill(currPoint.col, Helpers.weightToRGB(currPoint.weight));
                else Core.processing.fill(currPoint.col);
                switch (type) {
                    case 0 -> Core.processing.point(currPoint.x, currPoint.y);
                    case 1 -> Core.processing.circle(currPoint.x, currPoint.y, size);
                    case 2 -> Core.processing.rect(currPoint.x, currPoint.y, size, size);
                }
            }
        }
    }

    public static void point_list(Point_List _pl) {
        // Draws points to screen as a simple Processing point (no fill, weight).

        for (Grid_Point currPoint : _pl.points) {
            Core.processing.point(currPoint.x, currPoint.y);
        }
    }

    public static void point_list(int type, Point_List _pl) {
        // Draws points to screen as either a Processing point, circle, or rect (size 3 for circle and rect).
        // No fill, or weight set.
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect

        if (type > 2 || type < 0) type = 0;

        Core.processing.rectMode(3); // Set rectMode to CENTER;

        for (Grid_Point currPoint : _pl.points) {
            switch (type) {
                case 0 -> Core.processing.point(currPoint.x, currPoint.y);
                case 1 -> Core.processing.circle(currPoint.x, currPoint.y, 3);
                case 2 -> Core.processing.rect(currPoint.x, currPoint.y, 3, 3);
            }
        }
    }

    public static void point_list(int type, boolean weight, Point_List _pl) {
        // Draws points to screen as either a Processing point, circle, or rect (size 3 for circle and rect).
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect
        // weight -> whether to set alpha as weight.
        Core.processing.rectMode(3); // Set rectMode to CENTER;

        for (Grid_Point currPoint : _pl.points) {
            if (weight) Core.processing.fill(currPoint.col, Helpers.weightToRGB(currPoint.weight));
            else Core.processing.fill(currPoint.col);
            switch (type) {
                case 0 -> Core.processing.point(currPoint.x, currPoint.y);
                case 1 -> Core.processing.circle(currPoint.x, currPoint.y, 3);
                case 2 -> Core.processing.rect(currPoint.x, currPoint.y, 3, 3);
            }
        }
    }

    public static void point_list(int type, float size, boolean weight, Point_List _pl) {
        // Draws points to screen as either a Processing point, circle, or rect.
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect
        // size -> size of rect or circle.
        // weight -> whether to set alpha as weight.
        Core.processing.rectMode(3);

        for (Grid_Point currPoint : _pl.points) {
            if (weight) Core.processing.fill(currPoint.col, Helpers.weightToRGB(currPoint.weight));
            else Core.processing.fill(currPoint.col);
            switch (type) {
                case 0 -> Core.processing.point(currPoint.x, currPoint.y);
                case 1 -> Core.processing.circle(currPoint.x, currPoint.y, size);
                case 2 -> Core.processing.rect(currPoint.x, currPoint.y, size, size);
            }
        }
    }
}
