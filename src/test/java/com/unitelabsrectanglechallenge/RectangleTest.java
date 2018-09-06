package com.unitelabsrectanglechallenge;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {
    @Test
    void GetOtherCornerPoints() {
        Rectangle rec = new Rectangle(new Vector2D(0, 0), 3, 3);
        //Check if in counterclockwise order
        Vector2D[] otherCornerPoints = rec.getNonOriginCornerPoints();
        assertEquals(otherCornerPoints[0], new Vector2D(0, 3));
        assertEquals(otherCornerPoints[1], new Vector2D(3, 3));
        assertEquals(otherCornerPoints[2], new Vector2D(3, 0));
    }

    @Test
    void GetCornerPoints() {
        Rectangle rec = new Rectangle(new Vector2D(0, 0), 3, 3);

        Vector2D[] cornerPoints = rec.getCornerPoints();
        assertEquals(cornerPoints[0], new Vector2D(0, 0));
        assertEquals(cornerPoints[1], new Vector2D(0, 3));
        assertEquals(cornerPoints[2], new Vector2D(3, 3));
        assertEquals(cornerPoints[3], new Vector2D(3, 0));
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

        Line[] lines = rec.getLines();
        assertEquals(lines[0], new Line(new Vector2D(0, 0), new Vector2D(0,3)));
        assertEquals(lines[1], new Line(new Vector2D(0, 3), new Vector2D(3,3)));
        assertEquals(lines[2], new Line(new Vector2D(3, 3), new Vector2D(3,0)));
        assertEquals(lines[3], new Line(new Vector2D(3, 0), new Vector2D(0,0)));
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