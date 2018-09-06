package com.unitelabsrectanglechallenge;

class RectangleFromIntersection extends Rectangle {
    Rectangle parent1;
    Rectangle parent2;

    RectangleFromIntersection(Vector2D origin, int width, int height, Rectangle parent1, Rectangle parent2) {
        super(origin, width, height);
        this.parent1 = parent1;
        this.parent2 = parent2;
    }
}
