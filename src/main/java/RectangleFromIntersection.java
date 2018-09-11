import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;

class RectangleFromIntersection extends Rectangle {
    Rectangle parent1;
    Rectangle parent2;

    RectangleFromIntersection(Point2D origin, int width, int height, Rectangle parent1, Rectangle parent2) {
        super(origin, width, height);
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    RectangleFromIntersection(Rectangle rectangle, Rectangle parent1, Rectangle parent2) {
        this(rectangle.origin, rectangle.width, rectangle.height, parent1, parent2);
    }

    static LinkedList<Rectangle> getParents(Rectangle rectangle,
                                            LinkedList<Rectangle> parents) {
        if (rectangle instanceof RectangleFromIntersection) {
            RectangleFromIntersection r = (RectangleFromIntersection) rectangle;
            parents = getParents(r.parent1, parents);
            parents = getParents(r.parent2, parents);
            return parents;
        } else {
            parents.add(rectangle);
            parents = new LinkedList<>(new HashSet<>(parents));
            parents.sort(Comparator.comparingInt(rectangle2 -> rectangle2.number));
            return parents;
        }
    }

    LinkedList<Rectangle> getParents() {
        return getParents(this, new LinkedList<>());
    }

    @Override
    public String toString() {
        LinkedList<Rectangle> parents = getParents();

        StringBuilder parentNumbersString = new StringBuilder();

        for (int i = 0; i < parents.size(); ++i) {
            parentNumbersString.append(parents.get(i).number);
            parentNumbersString.append(i < parents.size() - 2
                    ? ", "
                    : i == parents.size() - 2
                    ? " and "
                    : "");
        }

        return number + ": Between rectangle " + parentNumbersString + " at " + origin +
                ", w=" + width + ", h=" + height;
    }
}
