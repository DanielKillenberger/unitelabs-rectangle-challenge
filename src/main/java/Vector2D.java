import java.util.Objects;

class Vector2D {
    final int x, y;

    Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Vector2D plus(Vector2D b) {
        return new Vector2D(Math.addExact(x, b.x), Math.addExact(y, b.y));
    }

    Vector2D minus(Vector2D b) {
        return new Vector2D(Math.subtractExact(x, b.x), Math.subtractExact(y, b.y));
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
        return o.x == x && o.y == y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
