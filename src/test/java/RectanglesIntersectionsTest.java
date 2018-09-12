import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class RectanglesIntersectionsTest {
    @Test
    void ParseRectanglesFromJsonString_UnknownField_Expect_UnrecognizedPropertyException() {
        String json = "{\r\n  \"recs\": [\r\n    {\"x\": 100, \"t\": 100, \"y\": 100, \"w\": 250, \"h\": 80},\r\n    " +
                "{\"x\": 120, \"y\": 200, \"w\": 250, \"h\": 150},\r\n    " +
                "{\"x\": 140, \"y\": 160, \"w\": 250, \"h\": 100},\r\n    " +
                "{\"x\": 160, \"y\": 140, \"w\": 350, \"h\": 190}\r\n  ]\r\n}";

        assertThrows(UnrecognizedPropertyException.class, () -> RectanglesIntersections.parseRectanglesFromJsonString(json));
    }
    @Test
    void ParseRectanglesFromJsonString_InvalidJson_Expect_JsonParseException() {
        String json = "{\r\n  \"recs\": [\r\n    {\"x\": 100 \"y\": 100, \"w\": 250, \"h\": 80},\r\n    " +
                "{\"x\": 120, \"y\": 200, \"w\": 250, \"h\": 150},\r\n    " +
                "{\"x\": 140, \"y\": 160, \"w\": 250, \"h\": 100},\r\n    " +
                "{\"x\": 160, \"y\": 140, \"w\": 350, \"h\": 190}\r\n  ]\r\n}";

        assertThrows(JsonParseException.class, () -> RectanglesIntersections.parseRectanglesFromJsonString(json));
    }

    @Test
    void ParseRectanglesFromJsonString_FieldMissingInRectangle_Expect_ListofRectanglesWithoutInvalidRectangle() {
        String json = "{\r\n  \"recs\": [\r\n    {\"y\": 100, \"w\": 250, \"h\": 80},\r\n    " +
                "{\"x\": 120, \"y\": 200, \"w\": 250, \"h\": 150},\r\n    " +
                "{\"x\": 140, \"y\": 160, \"w\": 250, \"h\": 100},\r\n    " +
                "{\"x\": 160, \"y\": 140, \"w\": 350, \"h\": 190}\r\n  ]\r\n}";

        LinkedList<Rectangle> expected_retangles = new LinkedList<>();

        expected_retangles.add(new Rectangle(new Point2D(120, 200), 250, 150));
        expected_retangles.add(new Rectangle(new Point2D(140, 160), 250, 100));
        expected_retangles.add(new Rectangle(new Point2D(160, 140), 350, 190));

        try {
            assertEquals(expected_retangles, RectanglesIntersections.parseRectanglesFromJsonString(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void ParseRectanglesFromJsonString_GivenExampleInput_Expect_CorrectListOfRectangles() {
        String json = "{\r\n  \"recs\": [\r\n    {\"x\": 100, \"y\": 100, \"w\": 250, \"h\": 80},\r\n    " +
                "{\"x\": 120, \"y\": 200, \"w\": 250, \"h\": 150},\r\n    " +
                "{\"x\": 140, \"y\": 160, \"w\": 250, \"h\": 100},\r\n    " +
                "{\"x\": 160, \"y\": 140, \"w\": 350, \"h\": 190}\r\n  ]\r\n}";

        LinkedList<Rectangle> expected_retangles = new LinkedList<>();

        expected_retangles.add(new Rectangle(new Point2D(100, 100), 250, 80));
        expected_retangles.add(new Rectangle(new Point2D(120, 200), 250, 150));
        expected_retangles.add(new Rectangle(new Point2D(140, 160), 250, 100));
        expected_retangles.add(new Rectangle(new Point2D(160, 140), 350, 190));

        try {
            assertEquals(expected_retangles, RectanglesIntersections.parseRectanglesFromJsonString(json));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void CalculateIntersections_GivenExampleInput_Expect_ExampleOutput() {
        LinkedList<Rectangle> rectangles = new LinkedList<>();
        LinkedList<Rectangle> expectedRectangles = new LinkedList<>();

        rectangles.add(new Rectangle(new Point2D(100, 100), 250, 80));
        rectangles.add(new Rectangle(new Point2D(120, 200), 250, 150));
        rectangles.add(new Rectangle(new Point2D(140, 160), 250, 100));
        rectangles.add(new Rectangle(new Point2D(160, 140), 350, 190));

        expectedRectangles.add(new Rectangle(new Point2D(140,160), 210, 20));
        expectedRectangles.add(new Rectangle(new Point2D(160,140), 190, 40));
        expectedRectangles.add(new Rectangle(new Point2D(140,200), 230, 60));
        expectedRectangles.add(new Rectangle(new Point2D(160,200), 210, 130));
        expectedRectangles.add(new Rectangle(new Point2D(160,160), 230, 100));
        expectedRectangles.add(new Rectangle(new Point2D(160,160), 190, 20));
        expectedRectangles.add(new Rectangle(new Point2D(160,200), 210, 60));

        assertEquals(expectedRectangles, RectanglesIntersections.calculateIntersections(rectangles));
    }
}