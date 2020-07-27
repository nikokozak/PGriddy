import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PShape;

public class Image {

    public static <T extends Iterable<Grid_Point>> void drawShapeAtPoints(PShape shape, float scale, T points) {

        // Draws a shape at each of the points of a Point_Grid or Point_List

        shape.scale(scale);
        Core.processing.shapeMode(PConstants.CENTER);

        for (Grid_Point point : points) {
            Core.processing.shape(shape, point.xPos, point.yPos);
        }

    }

    public static <T extends Iterable<Grid_Point>> void drawScaledShapeAtPoints(PShape shape, double factor, T points) {

        // Draws a shape at each of the points of a Point_Grid or Point_List
        // Scaled by every point's weight. Can be further tuned by adjusting the factor.

        Core.processing.shapeMode(PConstants.CENTER);

        for (Grid_Point point : points) {
            shape.scale((float)(point.weight * factor));
            Core.processing.shape(shape, point.xPos, point.yPos);
        }

    }

    public static <T extends Iterable<Grid_Point>> Point_List shapeMask(PShape shape, T points) {

        // Returns a list of points found to be contained in a given PShape (i.e. a mask).

        Point_List result = new Point_List();

        for (Grid_Point point : points) {
            if (shape.contains(point.xPos, point.yPos)) {
                result.add(point);
            }
        }

        return result;

    }

    public static void image(PImage _img, String _mode, int _shift_x, int _shift_y, boolean _subtract, boolean _blend, double _opacity, Point_Grid pg) {
        // TODO: Add Scaling

        // Loads an image and applies weights to Grid_Points in Point_Grid
        // based on R, G, B, L (lightness) values or combinations thereof.
        // Where:
        // _img -> PImage to sample from
        // _mode -> any of the following: "r", "g", "b", "l" (luma)
        // _shift_x -> shift the image left (-) or right (+)
        // _shift_y -> shift the image top (+) or bottom (-)
        // _blend -> whether to blend the new weights onto previous weights
        // _subtract -> whether to subtract the new weights from previous weights (only works in blend mode)
        // _opacity -> opacity of new weights

        PImage new_img;

        int grid_pixel_width = pg.x * pg.sX;
        int grid_pixel_height = pg.y * pg.sY;

        int sample_padding_X = Math.abs((grid_pixel_width - _img.width)/2);
        int sample_padding_Y = Math.abs((grid_pixel_height - _img.height)/2);

        if (_img.width > grid_pixel_width || _img.height > grid_pixel_height) {
            new_img = _img.get(sample_padding_X, sample_padding_Y, grid_pixel_width, grid_pixel_height);
            new_img.loadPixels();
            sample_padding_X = 0;
            sample_padding_Y = 0;
        } else {
            new_img = _img;
            new_img.loadPixels();
        }

        Grid_Point currPoint;
        int x, y, r, g, b;
        int currPixel;
        double weight = 0;

        for (int x_g = 0; x_g < pg.x; x_g++) {
            x = (sample_padding_X + _shift_x) + (x_g * pg.sX);
            for (int y_g = 0; y_g < pg.y; y_g++) {
                y = (sample_padding_Y + _shift_y) + (y_g * pg.sY);
                if (y*new_img.width+x > new_img.pixels.length - 1 || y*new_img.width+x < 0) currPixel = Core.processing.color(0);
                else currPixel = new_img.pixels[y*new_img.width+x];
                currPoint = pg.points.get(x_g).get(y_g);
                r = (currPixel >> 16) & 0xFF;
                g = (currPixel >> 8) & 0xFF;
                b = currPixel & 0xFF;
                switch (_mode) {
                    case ("r") -> weight = PApplet.map((float) r, 0, 255, 0, 1);
                    case ("g") -> weight = PApplet.map((float) g, 0, 255, 0, 1);
                    case ("b") -> weight = PApplet.map((float) b, 0, 255, 0, 1);
                    case ("l") -> weight = PApplet.map(Helpers.rgbToLuma(r, g, b), 0, 255, 0, 1);
                }
                if (_subtract && _blend) weight *= -1;
                if (_blend) currPoint.weight = Helpers.clamp(currPoint.weight + weight * _opacity, 0.0, 1.0);
                else currPoint.weight = weight * _opacity;

            }
        }

    }



}
