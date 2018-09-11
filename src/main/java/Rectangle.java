import java.security.InvalidParameterException;
import java.util.*;


class Rectangle {
    final Point2D origin;
    final int width, height;
    int number;

    Rectangle(Point2D origin, int width, int height, int number) {
        if (width <= 0 || height <= 0) {
            throw new InvalidParameterException("Width and height must be > 0");
        }
        this.origin = origin;
        this.width = width;
        this.height = height;
        this.number = number;
    }

    Rectangle(Point2D origin, int width, int height) {
        this(origin, width, height, 1);
    }

    /**
     * @param rectangle1 First rectangle to intersect with second
     * @param rectangle2 Second Rectangle to intersect with first
     * @return RectangleFromIntersection with both intersecting rectangles as parents
     */
    static RectangleFromIntersection calculateIntersection(Rectangle rectangle1, Rectangle rectangle2) {
        if(rectangle1.equals(rectangle2)) {
            return new RectangleFromIntersection(rectangle1, rectangle1, rectangle2);
        }
        Point2D rec1Min = rectangle1.getMin();
        Point2D rec1Max = rectangle1.getMax();
        Point2D rec2Min = rectangle2.getMin();
        Point2D rec2Max = rectangle2.getMax();

        int minX = Math.max(rec1Min.x, rec2Min.x);
        int minY = Math.max(rec1Min.y, rec2Min.y);

        int maxX = Math.min(rec1Max.x, rec2Max.x);
        int maxY = Math.min(rec1Max.y, rec2Max.y);

        Point2D potentialOrigin = new Point2D(minX, minY);
        int width = maxX - minX;
        int height = maxY - minY;
        if((!rectangle1.isPointInRectangle(potentialOrigin) && !rectangle2.isPointInRectangle(potentialOrigin)) ||
                width <= 0 || height <= 0) {
            return null;
        }

        return new RectangleFromIntersection(potentialOrigin, width, height, rectangle1, rectangle2 );
    }

    private Point2D getMin() {
        return origin;
    }

    private Point2D getMax() {
        return  new Point2D(origin.x + width, origin.y+height);
    }

    boolean isPointInRectangle(Point2D point) {
        return getMin().x <= point.x && point.x <= getMax().x && getMin().y <= point.y && point.y <= getMax().y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, width, height);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Rectangle)) {
            return false;
        }
        Rectangle o = (Rectangle) other;
        return o.origin.equals(origin) && o.width == width && o.height == height;
    }

    @Override
    public String toString() {
        return number + ": Rectangle at " + origin + ", w=" + width + ", h=" + height;
    }
}
