package com.unitelabsrectanglechallenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

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