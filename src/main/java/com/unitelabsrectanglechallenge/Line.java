package com.unitelabsrectanglechallenge;

import java.util.Objects;

class Line {
    final Vector2D p1, p2;

    Line(Vector2D p1, Vector2D p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * @param point to test if it's on line
     * @return -1 for point being on one side of the line and 1 for the other.
     * Point is on line when 0 is returned.
     */
    int whereIsPointRelativeToLine(Vector2D point) {

        /*
         * Invert line equation in case x1 == x2 in order to not run into divide by zero
         */
        float result = p1.X == p2.X ?
                point.X - p1.X - (p2.X - p1.X) / (p2.Y - p1.Y) * (point.Y - p1.Y) :
                point.Y - p1.Y - (p2.Y - p1.Y) / (p2.X - p1.X) * (point.X - p1.X);
        return result == 0 ? 0 : result > 0 ? 1 : -1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this){
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
