package com.unitelabsrectanglechallenge;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;


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

    Rectangle(LinkedList<Vector2D> points) {

        if (points.size() > 4 || points.size() < 3) {
            throw new InvalidParameterException("Invalid amount of points - needs to be either 3 or 4");
        }

        if (new HashSet<>(points).size() != points.size()) {
            throw new InvalidParameterException("List of points is not allowed to contain duplicates");
        }

        if (points.size() == 3) {
            points.add(calculateFourthRectangleCornerFromThree(points));
        }

        Vector2D origin = getOrigin(points);

        Optional<Vector2D> pointRight = points.stream().filter(p -> p.Y == origin.Y && !p.equals(origin)).findFirst();
        Optional<Vector2D> pointDown = points.stream().filter(p -> p.X == origin.X && !p.equals(origin)).findFirst();

        int width;
        int height;
        if (pointRight.isPresent() && pointDown.isPresent()) {
            width = pointRight.get().X - origin.X;
            height = pointDown.get().Y - origin.Y;

            this.origin = origin;
            this.width = width;
            this.height = height;
        } else {
            throw new InvalidParameterException(
                    "Incorrect set of points to form a rectangle parallel to the x- and y-axis");
        }
    }

    /**
     * @param rectangle1
     * @param rectangle2
     * @return RectangleFromIntersection with both intersecting rectangles as parents
     */
    static RectangleFromIntersection calculateIntersection(Rectangle rectangle1, Rectangle rectangle2) {
        LinkedList<Vector2D> cornerPointsFrom2IntersectingWith1 = rectangle1.getIntersectingCornerPoints(rectangle2);
        LinkedList<Vector2D> cornerPointsFrom1IntersectingWith2 = rectangle2.getIntersectingCornerPoints(rectangle1);

        LinkedList<Vector2D> overlappingCornerPointsOfContainedRectangle;
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
            overlappingCornerPointsOfContainedRectangle = cornerPointsFrom1IntersectingWith2;
            containedRectangle = rectangle1;
            surroundingRectangle = rectangle2;

        } else {
            overlappingCornerPointsOfContainedRectangle = cornerPointsFrom2IntersectingWith1;
            containedRectangle = rectangle2;
            surroundingRectangle = rectangle1;
        }

        linesOfSurroundingRectangle = surroundingRectangle.getLines();

        /*
        Check if surroundingRectangle includes all the points of the containedRectangle.
        => Intersection is containedRectangle.
         */
        if (overlappingCornerPointsOfContainedRectangle.size() == 4) {
            return new RectangleFromIntersection(
                    containedRectangle,
                    rectangle2,
                    rectangle1);
        }

        /*
        Determine the intersections of the lines of the rectangles
         */
        LinkedList<Vector2D> intersectionPoints = new LinkedList<>();
        for (var corner : overlappingCornerPointsOfContainedRectangle) {
            LinkedList<Line> cornerLines = containedRectangle.getLinesConnectedToCorner(corner);
            for (var cornerLine : cornerLines) {
                for (var line : linesOfSurroundingRectangle) {
                    Vector2D intersectingPoint = Line.calculateIntersection(cornerLine, line);
                    if (intersectingPoint != null) {
                        intersectionPoints.add(intersectingPoint);
                    }
                }
            }
        }

        /*
        With the overlapping and intersecting points create a rectangle
        using the constructor that takes a list of either 3 or 4 points
         */
        intersectionPoints.addAll(overlappingCornerPointsOfContainedRectangle);
        return new RectangleFromIntersection(new Rectangle(intersectionPoints), rectangle1, rectangle2);
    }

    private static Vector2D getOrigin(LinkedList<Vector2D> points) {
        if (points.size() != 4 && new HashSet<>(points).size() != points.size()) {
            throw new InvalidParameterException("Require four distinct points to get the origin point");
        }

        Vector2D originPoint = points.get(0);

        for (int i = 1; i < points.size(); ++i) {
            Vector2D currentPoint = points.get(i);
            if (currentPoint.X < originPoint.X || currentPoint.Y < originPoint.Y) {
                originPoint = currentPoint;
            }
        }

        return originPoint;
    }

    static Vector2D calculateFourthRectangleCornerFromThree(LinkedList<Vector2D> points) {
        if (points.size() != 3 || new HashSet<>(points).size() != points.size()) {
            throw new InvalidParameterException("Require three distinct points to calculate the fourth");
        }
        int X = points.getFirst().X;
        int Y = points.getFirst().Y;

        for (int i = 1; i < 3; ++i) {
            X ^= points.get(i).X;
            Y ^= points.get(i).Y;
        }

        return new Vector2D(X, Y);
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
