package com.unitelabsrectanglechallenge;

public class Line {
    public final Vector2D p1, p2;

    public Line(Vector2D p1, Vector2D p2) {
        this.p1 = p1;
        this.p2 = p2;
    }


    /**
     * @param point
     * @return -1 for point being on one side of the line and 1 for the other.
     * Point is on line when 0 is returned.
     */
    public int isPointOnLine(Vector2D point) {

        /**
         * Invert line equation in case x1 == x2 in order to not run into divide by zero
         */
        float result = p1.X == p2.X ?
                point.X - p1.X - (p2.X - p1.X) / (p2.Y - p1.Y) * (point.Y - p1.Y) :
                point.Y - p1.Y - (p2.Y - p1.Y) / (p2.X - p1.X) * (point.X - p1.X);
        return result == 0 ? 0 : result > 0 ? 1 : -1;
    }
}
