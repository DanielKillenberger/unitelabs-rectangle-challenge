import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    void Rectangle_WidthOrHeightSmallerOrEqualTo0_Expect_InvalidParameterException() {
        Assertions.assertThrows(InvalidParameterException.class, () -> new Rectangle(new Point2D(0, 0), 0, 0));
        Assertions.assertThrows(InvalidParameterException.class, () -> new Rectangle(new Point2D(0, 0), 1, 0));
        Assertions.assertThrows(InvalidParameterException.class, () -> new Rectangle(new Point2D(0, 0), -1, 1));
    }

    @Test
    void CalculateIntersection_RectanglesThatDoNotIntersect_Expect_Null() {
        Rectangle rec = new Rectangle(new Point2D(0, 0), 2, 2);
        Rectangle rec2 = new Rectangle(new Point2D(2, 1), 1, 1);
        Rectangle rec3 = new Rectangle(new Point2D(2, 2), 1, 1);

        assertNull(Rectangle.calculateIntersection(rec, rec2));
        assertNull(Rectangle.calculateIntersection(rec, rec3));
    }

    @Test
    void CalculateIntersection_TwoCornersInRectangle_Expect_CorrectRectangle() {
        Rectangle rec = new Rectangle(new Point2D(0, 0), 3, 2);
        Rectangle rec2 = new Rectangle(new Point2D(1, 1), 1, 2);
        Rectangle rec3 = new Rectangle(new Point2D(1, -1), 1, 2);
        Rectangle rec4 = new Rectangle(new Point2D(-1, 1), 2, 1);
        Rectangle rec5 = new Rectangle(new Point2D(2, 1), 2, 1);

        Rectangle rec_expected = new Rectangle(new Point2D(1, 1), 1, 1);
        Rectangle rec_expected2 = new Rectangle(new Point2D(1, 0), 1, 1);
        Rectangle rec_expected3 = new Rectangle(new Point2D(0, 1), 1, 1);
        Rectangle rec_expected4 = new Rectangle(new Point2D(2, 1), 1, 1);

        assertEquals(rec_expected, Rectangle.calculateIntersection(rec, rec2));
        assertEquals(rec_expected2, Rectangle.calculateIntersection(rec, rec3));
        assertEquals(rec_expected3, Rectangle.calculateIntersection(rec, rec4));
        assertEquals(rec_expected4, Rectangle.calculateIntersection(rec, rec5));
    }

    @Test
    void CalculateIntersection_OneCornerInRectangle_Expect_CorrectRectangle() {
        Rectangle rec = new Rectangle(new Point2D(0, 0), 2, 2);
        Rectangle rec2 = new Rectangle(new Point2D(1, 1), 2, 2);
        Rectangle rec3 = new Rectangle(new Point2D(-1, -1), 2, 2);
        Rectangle rec4 = new Rectangle(new Point2D(1, -1), 2, 2);
        Rectangle rec5 = new Rectangle(new Point2D(-1, 1), 2, 2);

        Rectangle rec_expected1 = new Rectangle(new Point2D(1, 1), 1, 1);
        Rectangle rec_expected2 = new Rectangle(new Point2D(0, 0), 1, 1);
        Rectangle rec_expected3 = new Rectangle(new Point2D(1, 0), 1, 1);
        Rectangle rec_expected4 = new Rectangle(new Point2D(0, 1), 1, 1);

        assertEquals(rec_expected1, Rectangle.calculateIntersection(rec, rec2));
        assertEquals(rec_expected2, Rectangle.calculateIntersection(rec, rec3));
        assertEquals(rec_expected3, Rectangle.calculateIntersection(rec, rec4));
        assertEquals(rec_expected4, Rectangle.calculateIntersection(rec, rec5));
    }

    @Test
    void CalculateIntersection_TwoEqualRectangles_Expect_IdenticalRectangle() {
        Rectangle rec = new Rectangle(new Point2D(0, 0), 2, 2);

        assertEquals(rec, Rectangle.calculateIntersection(rec, rec));
    }

    @Test
    void CalculateIntersection_OneRectangleContainsOtherRectangle_Expect_OtherRectangle() {
        Rectangle rec = new Rectangle(new Point2D(0, 0), 4, 4);
        Rectangle rec2 = new Rectangle(new Point2D(1, 1), 2, 2);

        assertEquals(rec2, Rectangle.calculateIntersection(rec, rec2));
        assertEquals(rec2, Rectangle.calculateIntersection(rec2, rec));
    }

    @Test
    void IsPointInRectangle_WhenPointInRectangle_Expect_True() {
        Rectangle rec = new Rectangle(new Point2D(0, 0), 3, 3);

        assertTrue(rec.isPointInRectangle(new Point2D(1, 1)));
        assertTrue(rec.isPointInRectangle(new Point2D(1, 2)));
        assertTrue(rec.isPointInRectangle(new Point2D(2, 2)));
        assertTrue(rec.isPointInRectangle(new Point2D(2, 1)));

        assertTrue(rec.isPointInRectangle(new Point2D(0, 0)));
        assertTrue(rec.isPointInRectangle(new Point2D(3, 3)));
        assertTrue(rec.isPointInRectangle(new Point2D(1, 0)));
        assertTrue(rec.isPointInRectangle(new Point2D(0, 1)));
    }

    @Test
    void IsPointInRectangle_WhenPointOutsideRectangle_Expect_False() {
        Rectangle rec = new Rectangle(new Point2D(0, 0), 3, 3);

        // Outside on both axis
        assertFalse(rec.isPointInRectangle(new Point2D(-1, -1)));
        assertFalse(rec.isPointInRectangle(new Point2D(-1, 4)));
        assertFalse(rec.isPointInRectangle(new Point2D(4, 4)));
        assertFalse(rec.isPointInRectangle(new Point2D(4, -1)));


        //Outside only on one axis
        assertFalse(rec.isPointInRectangle(new Point2D(-1, 1)));
        assertFalse(rec.isPointInRectangle(new Point2D(1, -1)));
        assertFalse(rec.isPointInRectangle(new Point2D(1, 4)));
        assertFalse(rec.isPointInRectangle(new Point2D(4, 1)));
    }
}