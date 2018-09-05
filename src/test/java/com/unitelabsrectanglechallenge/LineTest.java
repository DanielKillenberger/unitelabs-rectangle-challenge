package com.unitelabsrectanglechallenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    Vector2D origin = new Vector2D(0, 0);

    @Test
    void IsPointOnLine_When_PointOnLine_Expect_0() {
        Line line = new Line(origin, new Vector2D(2, 2));
        assertEquals(0, line.isPointOnLine(new Vector2D(-1, -1)));
        assertEquals(0, line.isPointOnLine(new Vector2D(0, 0)));
        assertEquals(0, line.isPointOnLine(new Vector2D(1, 1)));
        assertEquals(0, line.isPointOnLine(new Vector2D(2, 2)));
        assertEquals(0, line.isPointOnLine(new Vector2D(3, 3)));

        Line horizontalLine = new Line(origin, new Vector2D(1, 0));
        assertEquals(0, horizontalLine.isPointOnLine(new Vector2D(0, 0)));

        Line verticalLine = new Line(origin, new Vector2D(0, 1));
        assertEquals(0, verticalLine.isPointOnLine(new Vector2D(0, 0)));
    }

    @Test
    void IsPointOnLine_When_PointOnPositiveSideOfLine_Expect_1() {
        Line line = new Line(origin, new Vector2D(2, 2));
        assertEquals(1, line.isPointOnLine(new Vector2D(-1, 0)));
        assertEquals(1, line.isPointOnLine(new Vector2D(0, 1)));
        assertEquals(1, line.isPointOnLine(new Vector2D(1, 2)));
        assertEquals(1, line.isPointOnLine(new Vector2D(2, 100)));

        Line horizontalLine = new Line(origin, new Vector2D(1, 0));
        assertEquals(1, horizontalLine.isPointOnLine(new Vector2D(0, 1)));

        Line verticalLine = new Line(origin, new Vector2D(0, 1));
        assertEquals(1, verticalLine.isPointOnLine(new Vector2D(1, 0)));
    }

    @Test
    void IsPointOnLine_When_PointOnNegativeSideOfLine_Expect_negative_1() {
        Line line = new Line(origin, new Vector2D(2, 2));
        assertEquals(-1, line.isPointOnLine(new Vector2D(0, -1)));
        assertEquals(-1, line.isPointOnLine(new Vector2D(1, 0)));
        assertEquals(-1, line.isPointOnLine(new Vector2D(2, 1)));
        assertEquals(-1, line.isPointOnLine(new Vector2D(100, 2)));

        Line horizontalLine = new Line(origin, new Vector2D(1, 0));
        assertEquals(-1, horizontalLine.isPointOnLine(new Vector2D(0, -1)));

        Line verticalLine = new Line(origin, new Vector2D(0, 1));
        assertEquals(-1, verticalLine.isPointOnLine(new Vector2D(-1, 0)));
    }
}