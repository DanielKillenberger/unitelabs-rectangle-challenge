import java.security.InvalidParameterException;
import java.util.Objects;

class Line {
    final Vector2D p1, p2;

    Line(Vector2D p1, Vector2D p2) {
        if (p1.equals(p2)) {
            throw new InvalidParameterException("Points can not be equal to form a line");
        }

        if (p1.x != p2.x && p1.y != p2.y) {
            throw new InvalidParameterException("Line needs to be parallel to either axis");
        }
        this.p1 = p1;
        this.p2 = p2;
    }

    static Vector2D calculateIntersection(Line line1, Line line2) throws InvalidParameterException {
        //Check if lines are parallel
        if (
            line1.p1.x == line1.p2.x && line2.p1.x == line2.p2.x ||
            line1.p1.y == line1.p2.y && line2.p1.y == line2.p2.y) {
            return null;
        }

        //Line1 is horizontal => line2 is vertical as they are not parallel but aligned with axis
        if(line1.p1.x == line1.p2.x) {

            //Form potential intersecting point
            int X = line1.p1.x;
            int Y = line2.p1.y;

            //Return if intersection within limits of the two lines
            if (Math.max(line1.p1.y, line1.p2.y) >= Y && Math.min(line1.p1.y, line1.p2.y) <= Y &&
                    Math.max(line2.p1.x, line2.p2.x) >= X && Math.min(line2.p1.x, line2.p2.x) <= X) {
                return new Vector2D(X, Y);
            }
        }
        //Line1 is vertical

        //Form potential intersecting point
        int X = line2.p1.x;
        int Y = line1.p1.y;

        //Return if intersection within limits of the two lines
        if (Math.max(line2.p1.y, line2.p2.y) >= Y && Math.min(line2.p1.y, line2.p2.y) <= Y &&
                Math.max(line1.p1.x, line1.p2.x) >= X && Math.min(line1.p1.x, line1.p2.x) <= X) {
            return new Vector2D(X, Y);
        }

        // Lines don't intersect due to them being too short
        return null;

    }

    /**
     * @param point to test if it's on line
     * @return -1 for point being on one side of the line and 1 for the other.
     * Point is on line when 0 is returned.
     */
    int whereIsPointRelativeToLine(Vector2D point) {
        int result = p1.x == p2.x ?
                point.x - p1.x :
                point.y - p1.y;
        return Integer.compare(result, 0);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Line)) {
            return false;
        }
        Line o = (Line) other;
        return (o.p1.equals(p1) && o.p2.equals(p2)) || (o.p1.equals(p2) && o.p2.equals(p1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(p1, p2);
    }
}
