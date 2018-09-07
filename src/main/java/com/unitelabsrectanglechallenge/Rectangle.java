package com.unitelabsrectanglechallenge;

import java.util.LinkedList;
import java.util.Objects;

class Rectangle {
    final Vector2D origin;
    final int width, height;

    Rectangle(Vector2D origin, int width, int height) {
        this.origin = origin;
        this.width = width;
        this.height = height;
    }

    /**
     * @param rectangle1
     * @param rectangle2
     * @return RectangleFromIntersection with both intersecting rectangles as parents
     */
    static RectangleFromIntersection calculateIntersection(Rectangle rectangle1, Rectangle rectangle2) {
        LinkedList<Vector2D> cornerPointsFrom2IntersectingWith1 = rectangle1.getIntersectingCornerPoints(rectangle2);
        LinkedList<Vector2D> cornerPointsFrom1IntersectingWith2 = rectangle2.getIntersectingCornerPoints(rectangle1);

        /*
        Check if one of the rectangle includes all the points of the other rectangle.
        => Intersection would be the rectangle contained in the other.
         */
        if (cornerPointsFrom2IntersectingWith1.size() == 4) {
            return new RectangleFromIntersection(rectangle2.origin, rectangle2.width, rectangle2.height, rectangle2, rectangle1);
        }
        if (cornerPointsFrom1IntersectingWith2.size() == 4) {
            return new RectangleFromIntersection(rectangle1.origin, rectangle1.width, rectangle1.height, rectangle2, rectangle1);
        }

        return null;
    }


    /**
     * @return Array of 3 non-origin corner points in counterclockwise order.
     */
    Vector2D[] getNonOriginCornerPoints() {
        return new Vector2D[]{
                origin.plus(new Vector2D(0, height)),
                origin.plus(new Vector2D(0, height)).plus(new Vector2D(width, 0)),
                origin.plus(new Vector2D(width, 0))
        };
    }

    /**
     * @return Array of all corner points in counterclockwise order starting from origin.
     */
    Vector2D[] getCornerPoints() {
        Vector2D[] otherCornerPoints = getNonOriginCornerPoints();
        return new Vector2D[]{origin, otherCornerPoints[0], otherCornerPoints[1], otherCornerPoints[2]};
    }

    /**
     * @return Array of all lines defining the rectangle in counterclockwise order starting from the left.
     */
    Line[] getLines() {
        Vector2D[] cornerPoints = getNonOriginCornerPoints();

        final Line leftLine = new Line(origin, cornerPoints[0]);
        final Line bottomLine = new Line(cornerPoints[0], cornerPoints[1]);
        final Line rightLine = new Line(cornerPoints[1], cornerPoints[2]);
        final Line topLine = new Line(origin, cornerPoints[2]);

        return new Line[]{leftLine, bottomLine, rightLine, topLine};
    }

    boolean isPointInRectangle(Vector2D point) {
        Line[] lines = getLines();

        return (lines[0].whereIsPointRelativeToLine(point) == 1 &&
                lines[1].whereIsPointRelativeToLine(point) == -1 &&
                lines[2].whereIsPointRelativeToLine(point) == -1 &&
                lines[3].whereIsPointRelativeToLine(point) == 1);
    }

    /**
     *
     * @param rectangle
     * @return LinkedList<Vector2D> with points of the other rectangle within this rectangle
     */
    LinkedList<Vector2D> getIntersectingCornerPoints(Rectangle rectangle) {
        Vector2D[] cornerPoints = rectangle.getCornerPoints();
        LinkedList<Vector2D> cornerPointsIntersecting = new LinkedList<>();
        for (var cornerPoint : cornerPoints) {
            if (isPointInRectangle(cornerPoint)) {
                cornerPointsIntersecting.add(cornerPoint);
            }
        }
        return cornerPointsIntersecting;
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, width, height);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if(!(other instanceof Rectangle)){
            return false;
        }
        Rectangle o = (Rectangle) other;
        return o.origin.equals(origin) && o.width == width && o.height == height;
    }
}
