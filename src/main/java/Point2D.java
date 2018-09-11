import java.util.Objects;

class Point2D {
    final int x, y;

    Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Point2D)) {
            return false;
        }
        Point2D o = (Point2D) other;
        return o.x == x && o.y == y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
