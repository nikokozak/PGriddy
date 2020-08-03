public class Draw {

    public static <T extends Iterable<GridPoint>> void draw(T l) {

        /**
         * Draws points to screen as simple Processing point()s (no fill or weight).
         * @param l a PointGrid or PointList.
         * @return void
         */

        for (GridPoint currPoint : l) {
            Core.processing.point(currPoint.xPos(), currPoint.yPos());
        }
    }

    public static <T extends Iterable<GridPoint>> void draw(int type, T l) {

        /**
         * Draws points to screen as either a Processing point(), circle(), or rect().
         * Size 3 is applied to circle() and rect() modes. No fill or weight set.
         * @param type [0 - Point, 1 - Circle, or 2 - Rect]
         * @param l a PointGrid or PointList.
         * @return void
         */

        if (type > 2 || type < 0) type = 0;

        Core.processing.rectMode(3); // Set rectMode to CENTER;

        for (GridPoint currPoint : l) {
            switch (type) {
                case 0 -> Core.processing.point(currPoint.xPos(), currPoint.yPos());
                case 1 -> Core.processing.circle(currPoint.xPos(), currPoint.yPos(), 3);
                case 2 -> Core.processing.rect(currPoint.xPos(), currPoint.yPos(), 3, 3);
            }
        }
    }

    public static <T extends Iterable<GridPoint>> void draw(int type, boolean weight, T l) {

        /**
         * Draws a GridPoint to screen as either a Processing point(), circle(), or rect().
         * Size 3 is applied to circle() and rect() modes.
         * @param type [0 - Point, 1 - Circle, or 2 - Rect]
         * @param weight whether to set weight as alpha for draw color.
         * @param l a PointGrid or PointList.
         * @return void
         */

        Core.processing.rectMode(3); // Set rectMode to CENTER;

        for (GridPoint currPoint : l) {
            if (weight) {
                Core.processing.fill(currPoint.col(), Helpers.weightToRGB(currPoint.weight()));
                Core.processing.stroke(currPoint.col(), Helpers.weightToRGB(currPoint.weight()));
            }
            else {
                Core.processing.stroke(currPoint.col());
                Core.processing.fill(currPoint.col());
            }
            switch (type) {
                case 0 -> Core.processing.point(currPoint.xPos(), currPoint.yPos());
                case 1 -> Core.processing.circle(currPoint.xPos(), currPoint.yPos(), 3);
                case 2 -> Core.processing.rect(currPoint.xPos(), currPoint.yPos(), 3, 3);
            }
        }
    }

    public static <T extends Iterable<GridPoint>> void draw(int type, float size, boolean weight, T l) {

        /**
         * Draws points to screen as either a Processing point, circle, or rect.
         * @param type [0 - Point, 1 - Circle, 2 - Rect]
         * @param size size of rect or circle.
         * @param weight whether to set weight as alpha for draw color.
         * @param l a PointGrid or PointList
         * @return void
         */

        Core.processing.rectMode(3);

        for (GridPoint currPoint : l) {
            if (weight) {
                Core.processing.fill(currPoint.col(), Helpers.weightToRGB(currPoint.weight()));
                Core.processing.stroke(currPoint.col(), Helpers.weightToRGB(currPoint.weight()));
            }
            else {
                Core.processing.stroke(currPoint.col());
                Core.processing.fill(currPoint.col());
            }
            switch (type) {
                case 0 -> Core.processing.point(currPoint.xPos(), currPoint.yPos());
                case 1 -> Core.processing.circle(currPoint.xPos(), currPoint.yPos(), size);
                case 2 -> Core.processing.rect(currPoint.xPos(), currPoint.yPos(), size, size);
            }
        }
    }

}
