import java.util.LinkedList;

class RectangleFromIntersection extends Rectangle {
    Rectangle parent1;
    Rectangle parent2;

    RectangleFromIntersection(Vector2D origin, int width, int height, Rectangle parent1, Rectangle parent2) {
        super(origin, width, height);
        this.parent1 = parent1;
        this.parent2 = parent2;
    }

    RectangleFromIntersection(Rectangle rectangle, Rectangle parent1, Rectangle parent2) {
        this(rectangle.origin, rectangle.width, rectangle.height, parent1, parent2);
    }

    private LinkedList<Integer> getParentNumbers(Rectangle rectangle,
                                                 LinkedList<Integer> parentNumbers) {
        if(!(rectangle instanceof RectangleFromIntersection)) {
            parentNumbers.add(rectangle.number);
            return parentNumbers;
        }
        parentNumbers = getParentNumbers(parent1, parentNumbers);
        parentNumbers = getParentNumbers(parent2, parentNumbers);
        return parentNumbers;
    }

    @Override
    public String toString() {
        LinkedList<Integer> parentNumbers = new LinkedList<>();
        parentNumbers = getParentNumbers(parent1, parentNumbers);
        parentNumbers = getParentNumbers(parent2, parentNumbers);

        String parentNumbersString = "";

        for(int i = 0; i < parentNumbers.size(); ++i) {
            parentNumbersString += parentNumbers.get(i).intValue() + i < parentNumbers.size() ? ", " : " and ";
        }
        parentNumbersString += " ";

        return number +": Between rectangle " + parentNumbersString + origin +
                ", w="+width +", h=" + height;
    }
}
