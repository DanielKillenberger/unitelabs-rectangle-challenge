package com.unitelabsrectanglechallenge;

class Vector2D {
    final int X, Y;

    Vector2D(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    Vector2D plus(Vector2D b) {
        return new Vector2D(Math.addExact(X, b.X), Math.addExact(Y, b.Y));
    }

    Vector2D minus(Vector2D b) {
        return new Vector2D(Math.subtractExact(X, b.X), Math.subtractExact(Y, b.Y));
    }
}
