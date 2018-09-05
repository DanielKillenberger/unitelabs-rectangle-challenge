package com.unitelabsrectanglechallenge;

public class Vector2D {
    public final int X, Y;


    public Vector2D(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public Vector2D plus(Vector2D b) {
        return new Vector2D(Math.addExact(X, b.X), Math.addExact(Y, b.Y));
    }

    public Vector2D minus(Vector2D b) {
        return new Vector2D(Math.subtractExact(X, b.X), Math.subtractExact(Y, b.Y));
    }
}
