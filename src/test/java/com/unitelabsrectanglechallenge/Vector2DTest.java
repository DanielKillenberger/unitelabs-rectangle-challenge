package com.unitelabsrectanglechallenge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2DTest {

    @Test
    void Plus_Addition_Expect_AddedNumbers() {
        Vector2D v1 = new Vector2D(0, 0);
        Vector2D v2 = new Vector2D(1, 1);
        assertEquals(1, v1.plus(v2).X);
        assertEquals(1, v1.plus(v2).Y);
    }

    @Test
    void Minus_Subtraction_Expect_SubtractedNumbers() {
        Vector2D v1 = new Vector2D(0, 0);
        Vector2D v2 = new Vector2D(1, 1);
        assertEquals(-1, v1.minus(v2).X);
        assertEquals(-1, v1.minus(v2).Y);
    }

    @Test
    void Plus_IntegerOverflow_Expect_ArithmeticException() {
        Vector2D v1 = new Vector2D(2000000000, 0);
        Vector2D v2 = new Vector2D(1000000000, 0);
        assertThrows(ArithmeticException.class, () -> v1.plus(v2));
    }

    @Test
    void Minus_IntegerUnderFlow_Expect_ArithmeticException() {
        Vector2D v1 = new Vector2D(2000000000, 0);
        Vector2D v2 = new Vector2D(-1000000000, 0);
        assertThrows(ArithmeticException.class, () -> v2.minus(v1));
    }
}