package com.unitelabsrectanglechallenge;

class Rectangle {
    final Vector2D origin;
    final int width, height;

    Rectangle(Vector2D origin, int width, int height) {
        this.origin = origin;
        this.width = width;
        this.height = height;
    }

    /**
     * @return Array of 3 other corner points in counterclockwise order.
     */
    Vector2D[] getOtherCornerPoints() {
        return new Vector2D[]{
                origin.plus(new Vector2D(0, height)),
                origin.plus(new Vector2D(0, height)).plus(new Vector2D(width, 0)),
                origin.plus(new Vector2D(width, 0))
        };
    }

    boolean isPointInRectangle(Vector2D point) {
        Vector2D[] cornerPoints = getOtherCornerPoints();

        final Line leftLine = new Line(origin, cornerPoints[0]);
        final Line topLine = new Line(origin, cornerPoints[2]);
        final Line bottomLine = new Line(cornerPoints[2], cornerPoints[1]);
        final Line rightLine = new Line(cornerPoints[0], cornerPoints[1]);

        return (topLine.isPointOnLine(point) == 1 &&
                leftLine.isPointOnLine(point) == 1 &&
                bottomLine.isPointOnLine(point) == -1 &&
                rightLine.isPointOnLine(point) == -1);
    }

    RectangleFromIntersection calculateIntersection(Rectangle rectangle) {
        return new RectangleFromIntersection(origin, width, height, this, this);
    }
}
