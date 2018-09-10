import java.util.Objects;

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

    @Override
    public boolean equals(Object other) {
        if (other == this){
            return true;
        }
        if (!(other instanceof Vector2D)) {
            return false;
        }
        Vector2D o = (Vector2D) other;
        return o.X == X && o.Y == Y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }

    @Override
    public String toString() {
        return "(" + X + "," + Y + ")";
    }
}
