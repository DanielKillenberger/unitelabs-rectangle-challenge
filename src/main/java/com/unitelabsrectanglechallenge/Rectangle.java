package com.unitelabsrectanglechallenge;

class Rectangle {
    final Vector2D origin;
    final int width, height;

    Rectangle(Vector2D origin, int width, int height) {
        this.origin = origin;
        this.width = width;
        this.height = height;
    }

    boolean isPointInRectangle(Vector2D point) {
        final Vector2D topRightCorner = origin.plus(new Vector2D(width, 0));
        final Vector2D bottomLeftCorner = origin.plus(new Vector2D(0, height));
        final Vector2D bottomRightCorner = bottomLeftCorner.plus(new Vector2D(width, 0));

        final Line topLine = new Line(origin, topRightCorner);
        final Line leftLine = new Line(origin, bottomLeftCorner);
        final Line bottomLine = new Line(bottomLeftCorner, bottomRightCorner);
        final Line rightLine = new Line(topRightCorner, bottomRightCorner);

        return (topLine.isPointOnLine(point) == 1 &&
                leftLine.isPointOnLine(point) == 1 &&
                bottomLine.isPointOnLine(point) == -1 &&
                rightLine.isPointOnLine(point) == -1);
    }
}
