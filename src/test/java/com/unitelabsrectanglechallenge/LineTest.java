package com.unitelabsrectanglechallenge;

import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    Vector2D origin = new Vector2D(0, 0);

    @Test
    void Line_When_ParameterPointsAreEqual_Expect_InvalidParameterException() {
        assertThrows(InvalidParameterException.class, () -> new Line(origin, origin));
    }

    @Test
    void Line_When_LineNotParallelToAxis_Expect_InvalidParameterException() {
        assertThrows(InvalidParameterException.class, () -> new Line(origin, new Vector2D(1, 1)));
    }


    @Test
    void CalculateIntersection_When_LinesDoNotCross_Expect_Null() {
        Line line1 = new Line(origin, new Vector2D(2, 0));
        Line line2 = new Line(new Vector2D(3, 1), new Vector2D(3, -1));

        assertNull(Line.calculateIntersection(line1, line2));
        assertNull(Line.calculateIntersection(line2, line1));
    }

    @Test
    void CalculateIntersection_When_IntersectionOnLine_Expect_PointOnLine() {
        Line line1 = new Line(origin, new Vector2D(2, 0));
        Line line2 = new Line(new Vector2D(1, 0), new Vector2D(1, 1));

        assertEquals(new Vector2D(1, 0), Line.calculateIntersection(line1, line2));
        assertEquals(new Vector2D(1, 0), Line.calculateIntersection(line2, line1));
    }

    @Test
    void CalculateIntersection_When_LinesIntersect_Expect_CorrectPoint() {
        Line line1 = new Line(origin, new Vector2D(2, 0));
        Line line2 = new Line(new Vector2D(1, 1), new Vector2D(1, -1));
        assertEquals(new Vector2D(1, 0), Line.calculateIntersection(line1, line2));
        assertEquals(new Vector2D(1, 0), Line.calculateIntersection(line2, line1));
    }

    @Test
    void CalculateIntersection_When_LinesParallel_Expect_Null() {
        //Parallel horizontal lines
        Line line1 = new Line(origin, new Vector2D(1, 0));
        Line line2 = new Line(new Vector2D(0, 1), new Vector2D(1, 1));

        //Parallel vertical lines
        Line line3 = new Line(origin, new Vector2D(0, 1));
        Line line4 = new Line(new Vector2D(1, 0), new Vector2D(1, 1));

        assertNull(Line.calculateIntersection(line1, line2));
        assertNull(Line.calculateIntersection(line2, line1));
        assertNull(Line.calculateIntersection(line3, line4));
        assertNull(Line.calculateIntersection(line4, line3));
    }

    @Test
    void IsPointOnLine_When_PointOnLine_Expect_0() {
        Line horizontalLine = new Line(origin, new Vector2D(1, 0));
        assertEquals(0, horizontalLine.whereIsPointRelativeToLine(origin));

        Line verticalLine = new Line(origin, new Vector2D(0, 1));
        assertEquals(0, verticalLine.whereIsPointRelativeToLine(origin));
    }

    @Test
    void IsPointOnLine_When_PointOnPositiveSideOfLine_Expect_1() {
        Line horizontalLine = new Line(origin, new Vector2D(1, 0));
        assertEquals(1, horizontalLine.whereIsPointRelativeToLine(new Vector2D(0, 1)));

        Line verticalLine = new Line(origin, new Vector2D(0, 1));
        assertEquals(1, verticalLine.whereIsPointRelativeToLine(new Vector2D(1, 0)));
    }

    @Test
    void IsPointOnLine_When_PointOnNegativeSideOfLine_Expect_negative_1() {
        Line horizontalLine = new Line(origin, new Vector2D(1, 0));
        assertEquals(-1, horizontalLine.whereIsPointRelativeToLine(new Vector2D(0, -1)));

        Line verticalLine = new Line(origin, new Vector2D(0, 1));
        assertEquals(-1, verticalLine.whereIsPointRelativeToLine(new Vector2D(-1, 0)));
    }
}