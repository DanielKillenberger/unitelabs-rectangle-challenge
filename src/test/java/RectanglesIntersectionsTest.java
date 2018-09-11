import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class RectanglesIntersectionsTest {

    @Test
    void CalculateIntersections_GivenExampleInput_Expect_ExampleOutput() {
        LinkedList<Rectangle> rectangles = new LinkedList<>();
        LinkedList<Rectangle> expectedRectangles = new LinkedList<>();

        rectangles.add(new Rectangle(new Vector2D(100, 100), 250, 80));
        rectangles.add(new Rectangle(new Vector2D(120, 200), 250, 150));
        rectangles.add(new Rectangle(new Vector2D(140, 160), 250, 100));
        rectangles.add(new Rectangle(new Vector2D(160, 140), 350, 190));

        expectedRectangles.add(new Rectangle(new Vector2D(140,160), 210, 20));
        expectedRectangles.add(new Rectangle(new Vector2D(160,140), 190, 40));
        expectedRectangles.add(new Rectangle(new Vector2D(140,200), 230, 60));
        expectedRectangles.add(new Rectangle(new Vector2D(160,200), 210, 130));
        expectedRectangles.add(new Rectangle(new Vector2D(160,160), 230, 100));
        expectedRectangles.add(new Rectangle(new Vector2D(160,160), 190, 20));
        expectedRectangles.add(new Rectangle(new Vector2D(160,200), 210, 60));

        assertEquals(expectedRectangles, RectanglesIntersections.calculateIntersections(rectangles));
    }
}