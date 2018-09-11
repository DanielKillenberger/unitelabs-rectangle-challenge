import java.security.InvalidParameterException;
import java.util.*;


class Rectangle {
    final Vector2D origin;
    final int width, height;
    int number;

    Rectangle(Vector2D origin, int width, int height, int number) {
        if (width <= 0 || height <= 0) {
            throw new InvalidParameterException("Width and height must be > 0");
        }
        this.origin = origin;
        this.width = width;
        this.height = height;
        this.number = number;
    }

    Rectangle(Vector2D origin, int width, int height) {
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

        int minX = Math.max(rectangle1.getMinX(), rectangle2.getMinX());
        int minY = Math.max(rectangle1.getMinY(), rectangle2.getMinY());

        int maxX = Math.min(rectangle1.getMaxX(), rectangle2.getMaxX());
        int maxY = Math.min(rectangle1.getMaxY(), rectangle2.getMaxY());

        Vector2D potentialOrigin = new Vector2D(minX, minY);
        int width = maxX - minX;
        int height = maxY - minY;
        if((!rectangle1.isPointInRectangle(potentialOrigin) && !rectangle2.isPointInRectangle(potentialOrigin)) ||
                width <= 0 || height <= 0) {
            return null;
        }

        return new RectangleFromIntersection(potentialOrigin, width, height, rectangle1, rectangle2 );
    }

    private int getMinX() {
        return origin.x;
    }

    private int getMaxX() {
        return  origin.x + width;
    }

    private int getMinY() {
        return origin.y;
    }

    private int getMaxY() {
        return origin.y + height;
    }

    boolean isPointInRectangle(Vector2D point) {
        return getMinX() <= point.x && point.x <= getMaxX() && getMinY() <= point.y && point.y <= getMaxY();
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
