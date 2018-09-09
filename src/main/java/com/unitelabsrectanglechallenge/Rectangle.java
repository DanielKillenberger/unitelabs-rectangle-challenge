package com.unitelabsrectanglechallenge;

import java.security.InvalidParameterException;
import java.util.LinkedList;
import java.util.Objects;


class Rectangle {
    final Vector2D origin;
    final int width, height;

    Rectangle(Vector2D origin, int width, int height) {
        if (width == 0 || height == 0) {
            throw new InvalidParameterException("Width and height must not be 0");
        }
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

        LinkedList<Vector2D> cornerPointsOfContainedRectangle;
        LinkedList<Line> linesOfSurroundingRectangle;
        Rectangle containedRectangle;
        Rectangle surroundingRectangle;

        /*
        Return null if no points are contained in the other Rectangle
         */
        if (cornerPointsFrom1IntersectingWith2.size() == 0 && cornerPointsFrom2IntersectingWith1.size() == 0) {
            return null;
        }

        if (cornerPointsFrom1IntersectingWith2.size() > cornerPointsFrom2IntersectingWith1.size()) {
            cornerPointsOfContainedRectangle = cornerPointsFrom1IntersectingWith2;
            containedRectangle = rectangle1;
            surroundingRectangle = rectangle2;

        } else {
            cornerPointsOfContainedRectangle = cornerPointsFrom2IntersectingWith1;
            containedRectangle = rectangle2;
            surroundingRectangle = rectangle1;
        }

        linesOfSurroundingRectangle = surroundingRectangle.getLines();

        /*
        Check if surroundingRectangle includes all the points of the containedRectangle.
        => Intersection is containedRectangle.
         */
        if (cornerPointsOfContainedRectangle.size() == 4) {
            return new RectangleFromIntersection(
                    containedRectangle.origin,
                    containedRectangle.width,
                    containedRectangle.height,
                    rectangle2,
                    rectangle1);
        }

        return null;
    }

    LinkedList<Line> getLinesConnectedToCorner(Vector2D corner) {
        LinkedList<Vector2D> allCorners = getCornerPoints();
        if (!allCorners.contains(corner)) {
            return null;
        }
        LinkedList<Line> lines = getLines();
        lines.removeIf(line -> (!line.p1.equals(corner) && !line.p2.equals(corner)));

        return lines;
    }


    /**
     * @return Array of 3 non-origin corner points in counterclockwise order.
     */
    LinkedList<Vector2D> getNonOriginCornerPoints() {
        LinkedList<Vector2D> result = new LinkedList<>();

        result.add(origin.plus(new Vector2D(0, height)));
        result.add(origin.plus(new Vector2D(0, height)).plus(new Vector2D(width, 0)));
        result.add(origin.plus(new Vector2D(width, 0)));
        return result;
    }

    /**
     * @return Array of all corner points in counterclockwise order starting from origin.
     */
    LinkedList<Vector2D> getCornerPoints() {
        LinkedList<Vector2D> cornerPoints = getNonOriginCornerPoints();
        cornerPoints.addFirst(origin);
        return cornerPoints;
    }

    /**
     * @return Array of all lines defining the rectangle in counterclockwise order starting from the left.
     */
    LinkedList<Line> getLines() {
        LinkedList<Vector2D> cornerPoints = getNonOriginCornerPoints();

        LinkedList<Line> result = new LinkedList<>();
        result.add(new Line(origin, cornerPoints.get(0)));
        result.add(new Line(cornerPoints.get(0), cornerPoints.get(1)));
        result.add(new Line(cornerPoints.get(1), cornerPoints.get(2)));
        result.add(new Line(origin, cornerPoints.get(2)));

        return result;
    }

    boolean isPointInRectangle(Vector2D point) {
        LinkedList<Line> lines = getLines();

        return (lines.get(0).whereIsPointRelativeToLine(point) == 1 &&
                lines.get(1).whereIsPointRelativeToLine(point) == -1 &&
                lines.get(2).whereIsPointRelativeToLine(point) == -1 &&
                lines.get(3).whereIsPointRelativeToLine(point) == 1);
    }

    /**
     * @param rectangle
     * @return LinkedList<Vector2D> with points of the other rectangle within this rectangle
     */
    LinkedList<Vector2D> getIntersectingCornerPoints(Rectangle rectangle) {
        LinkedList<Vector2D> cornerPoints = rectangle.getCornerPoints();
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
        if (other == this) {
            return true;
        }
        if (!(other instanceof Rectangle)) {
            return false;
        }
        Rectangle o = (Rectangle) other;
        return o.origin.equals(origin) && o.width == width && o.height == height;
    }
}
