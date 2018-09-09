package com.unitelabsrectanglechallenge;

import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    @Test
    void Rectangle_WidthOrHeightEqualTo0_Expect_InvalidParameterException() {
        assertThrows(InvalidParameterException.class, () -> new Rectangle(new Vector2D(0, 0), 0, 0));
        assertThrows(InvalidParameterException.class, () -> new Rectangle(new Vector2D(0, 0), 1, 0));
        assertThrows(InvalidParameterException.class, () -> new Rectangle(new Vector2D(0, 0), 0, 1));
    }

    @Test
    void CalculateIntersection_RectanglesThatDoNotIntersect_Expect_Null() {
        Rectangle rec = new Rectangle(new Vector2D(0, 0), 1, 1);
        Rectangle rec2 = new Rectangle(new Vector2D(1, 1), 1, 1);
        assertNull(Rectangle.calculateIntersection(rec, rec2));
    }

    @Test
    void CalculateIntersection_TwoCornersInRectangle_Expect_CorrectRectangle() {
        Rectangle rec = new Rectangle(new Vector2D(0, 0), 3, 2);
        Rectangle rec2 = new Rectangle(new Vector2D(1, 1), 1, 2);
        Rectangle rec3 = new Rectangle(new Vector2D(1, -1), 1, 2);
        Rectangle rec4 = new Rectangle(new Vector2D(-1, 1), 2, 1);
        Rectangle rec5 = new Rectangle(new Vector2D(2, 1), 2, 1);

        Rectangle rec_expected = new Rectangle(new Vector2D(1, 1), 1, 1);
        Rectangle rec_expected2 = new Rectangle(new Vector2D(1, 0), 1, 1);
        Rectangle rec_expected3 = new Rectangle(new Vector2D(0, 1), 1, 1);
        Rectangle rec_expected4 = new Rectangle(new Vector2D(2, 1), 1, 1);
        assertEquals(rec_expected, Rectangle.calculateIntersection(rec, rec2));
        assertEquals(rec_expected2, Rectangle.calculateIntersection(rec, rec3));
        assertEquals(rec_expected3, Rectangle.calculateIntersection(rec, rec4));
        assertEquals(rec_expected4, Rectangle.calculateIntersection(rec, rec5));
    }

    @Test
    void CalculateIntersection_OneCornerInRectangle_Expect_CorrectRectangle() {
        Rectangle rec = new Rectangle(new Vector2D(0, 0), 2, 2);
        Rectangle rec2 = new Rectangle(new Vector2D(1, 1), 2, 2);
        Rectangle rec3 = new Rectangle(new Vector2D(-1, -1), 2, 2);
        Rectangle rec4 = new Rectangle(new Vector2D(1, -1), 2, 2);
        Rectangle rec5 = new Rectangle(new Vector2D(-1, 1), 2, 2);

        Rectangle rec_expected1 = new Rectangle(new Vector2D(1, 1), 1, 1);
        Rectangle rec_expected2 = new Rectangle(new Vector2D(0, 0), 1, 1);
        Rectangle rec_expected3 = new Rectangle(new Vector2D(1, 0), 1, 1);
        Rectangle rec_expected4 = new Rectangle(new Vector2D(0, 1), 1, 1);

        assertEquals(rec_expected1, Rectangle.calculateIntersection(rec, rec2));
        assertEquals(rec_expected2, Rectangle.calculateIntersection(rec, rec3));
        assertEquals(rec_expected3, Rectangle.calculateIntersection(rec, rec4));
        assertEquals(rec_expected4, Rectangle.calculateIntersection(rec, rec5));
    }

    @Test
    void CalculateIntersection_OneRectangleContainsOtherRectangle_Expect_OtherRectangle() {
        Rectangle rec = new Rectangle(new Vector2D(0, 0), 4, 4);
        Rectangle rec2 = new Rectangle(new Vector2D(1, 1), 2, 2);

        assertEquals(rec2, Rectangle.calculateIntersection(rec, rec2));
        assertEquals(rec2, Rectangle.calculateIntersection(rec2, rec));
    }

    @Test
    void GetLinesConnectedToCorner_CornerNotInRectangle_Expect_Null() {
        Rectangle rec = new Rectangle(new Vector2D(0, 0), 1, 1);
        assertNull(rec.getLinesConnectedToCorner(new Vector2D(2, 2)));
    }

    @Test
    void GetLinesConnectedToCorner_CornerInRectangle_Expect_ConnectedLines() {
        Rectangle rec = new Rectangle(new Vector2D(0, 0), 1, 1);

        LinkedList<Line> lines = rec.getLinesConnectedToCorner(new Vector2D(0, 0));
        assertTrue(lines.contains(new Line(new Vector2D(0, 0), new Vector2D(0, 1))));
        assertTrue(lines.contains(new Line(new Vector2D(0, 0), new Vector2D(1, 0))));
    }

    @Test
    void GetOtherCornerPoints() {
        Rectangle rec = new Rectangle(new Vector2D(0, 0), 3, 3);
        //Check if in counterclockwise order
        LinkedList<Vector2D> otherCornerPoints = rec.getNonOriginCornerPoints();
        assertEquals(otherCornerPoints.get(0), new Vector2D(0, 3));
        assertEquals(otherCornerPoints.get(1), new Vector2D(3, 3));
        assertEquals(otherCornerPoints.get(2), new Vector2D(3, 0));
    }

    @Test
    void GetCornerPoints() {
        Rectangle rec = new Rectangle(new Vector2D(0, 0), 3, 3);

        LinkedList<Vector2D> cornerPoints = rec.getCornerPoints();
        assertEquals(cornerPoints.get(0), new Vector2D(0, 0));
        assertEquals(cornerPoints.get(1), new Vector2D(0, 3));
        assertEquals(cornerPoints.get(2), new Vector2D(3, 3));
        assertEquals(cornerPoints.get(3), new Vector2D(3, 0));
    }

    @Test
    void GetIntersectingCornerPoints() {
        Rectangle rec1 = new Rectangle(new Vector2D(0, 0), 3, 3);
        Rectangle rec2 = new Rectangle(new Vector2D(0, 0), 2, 2);

        assertEquals(1, rec1.getIntersectingCornerPoints(rec2).size());
        assertEquals(new Vector2D(2,2), rec1.getIntersectingCornerPoints(rec2).get(0));
    }

    @Test
    void GetLines() {
        Rectangle rec = new Rectangle(new Vector2D(0, 0), 3, 3);

        LinkedList<Line> lines = rec.getLines();
        assertEquals(lines.get(0), new Line(new Vector2D(0, 0), new Vector2D(0, 3)));
        assertEquals(lines.get(1), new Line(new Vector2D(0, 3), new Vector2D(3, 3)));
        assertEquals(lines.get(2), new Line(new Vector2D(3, 3), new Vector2D(3, 0)));
        assertEquals(lines.get(3), new Line(new Vector2D(3, 0), new Vector2D(0, 0)));
    }

    @Test
    void IsPointInRectangle_WhenPointInRectangle_Expect_True() {
        Rectangle rec = new Rectangle(new Vector2D(0, 0), 3, 3);
        assertTrue(rec.isPointInRectangle(new Vector2D(1, 1)));
        assertTrue(rec.isPointInRectangle(new Vector2D(1, 2)));
        assertTrue(rec.isPointInRectangle(new Vector2D(2, 2)));
        assertTrue(rec.isPointInRectangle(new Vector2D(2, 1)));
    }

    @Test
    void IsPointInRectangle_WhenPointOutsideRectangleOrOnBorder_Expect_False() {
        Rectangle rec = new Rectangle(new Vector2D(0, 0), 3, 3);
        // Outside on both axis
        assertFalse(rec.isPointInRectangle(new Vector2D(-1, -1)));
        assertFalse(rec.isPointInRectangle(new Vector2D(-1, 4)));
        assertFalse(rec.isPointInRectangle(new Vector2D(4, 4)));
        assertFalse(rec.isPointInRectangle(new Vector2D(4, -1)));

        //On corners of the rectangle
        assertFalse(rec.isPointInRectangle(new Vector2D(0, 0)));
        assertFalse(rec.isPointInRectangle(new Vector2D(0, 3)));
        assertFalse(rec.isPointInRectangle(new Vector2D(3, 0)));
        assertFalse(rec.isPointInRectangle(new Vector2D(3, 3)));

        //On border of rectangle
        assertFalse(rec.isPointInRectangle(new Vector2D(1, 0)));
        assertFalse(rec.isPointInRectangle(new Vector2D(0, 1)));
        assertFalse(rec.isPointInRectangle(new Vector2D(3, 1)));
        assertFalse(rec.isPointInRectangle(new Vector2D(1, 3)));

        //Outside only on one axis
        assertFalse(rec.isPointInRectangle(new Vector2D(-1, 1)));
        assertFalse(rec.isPointInRectangle(new Vector2D(1, -1)));
        assertFalse(rec.isPointInRectangle(new Vector2D(1, 4)));
        assertFalse(rec.isPointInRectangle(new Vector2D(4, 1)));
    }
}