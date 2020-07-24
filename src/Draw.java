import java.util.ArrayList;

public class Draw {

    public static <T extends Iterable<Grid_Point>> void draw(T _l) {
        // Draws points to screen as a simple Processing point (no fill, weight).

        for (Grid_Point currPoint : _l) {
            Core.processing.point(currPoint.x, currPoint.y);
        }
    }

    public static <T extends Iterable<Grid_Point>> void draw(int type, T _l) {
        // Draws points to screen as either a Processing point, circle, or rect (size 3 for circle and rect).
        // No fill, or weight set.
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect

        if (type > 2 || type < 0) type = 0;

        Core.processing.rectMode(3); // Set rectMode to CENTER;

        for (Grid_Point currPoint : _l) {
            switch (type) {
                case 0 -> Core.processing.point(currPoint.x, currPoint.y);
                case 1 -> Core.processing.circle(currPoint.x, currPoint.y, 3);
                case 2 -> Core.processing.rect(currPoint.x, currPoint.y, 3, 3);
            }
        }
    }

    public static <T extends Iterable<Grid_Point>> void draw(int type, boolean weight, T _l) {
        // Draws points to screen as either a Processing point, circle, or rect (size 3 for circle and rect).
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect
        // weight -> whether to set alpha as weight.
        Core.processing.rectMode(3); // Set rectMode to CENTER;

        for (Grid_Point currPoint : _l) {
            if (weight) Core.processing.fill(currPoint.col, Helpers.weightToRGB(currPoint.weight));
            else Core.processing.fill(currPoint.col);
            switch (type) {
                case 0 -> Core.processing.point(currPoint.x, currPoint.y);
                case 1 -> Core.processing.circle(currPoint.x, currPoint.y, 3);
                case 2 -> Core.processing.rect(currPoint.x, currPoint.y, 3, 3);
            }
        }
    }

    public static <T extends Iterable<Grid_Point>> void draw(int type, float size, boolean weight, T _l) {
        // Draws points to screen as either a Processing point, circle, or rect.
        // Where:
        // type -> [0, 1, or 2] 0: Point, 1: Circle, 2: Rect
        // size -> size of rect or circle.
        // weight -> whether to set alpha as weight.
        Core.processing.rectMode(3);

        for (Grid_Point currPoint : _l) {
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
