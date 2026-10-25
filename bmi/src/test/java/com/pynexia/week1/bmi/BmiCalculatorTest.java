package com.pynexia.week1.bmi;

import org.junit.Test;
import static org.junit.Assert.*;
public class BmiCalculatorTest {
    @Test public void computesMetricBmi() { assertEquals(22.857142857, BmiCalculator.calculate(175, 70), 0.000001); }
    @Test public void classifiesExactBoundariesWithoutRounding() {
        assertEquals(BmiCalculator.Category.UNDERWEIGHT, BmiCalculator.category(18.4999));
        assertEquals(BmiCalculator.Category.NORMAL, BmiCalculator.category(18.5));
        assertEquals(BmiCalculator.Category.NORMAL, BmiCalculator.category(24.9999));
        assertEquals(BmiCalculator.Category.OVERWEIGHT, BmiCalculator.category(25));
        assertEquals(BmiCalculator.Category.OVERWEIGHT, BmiCalculator.category(29.9999));
        assertEquals(BmiCalculator.Category.OBESE, BmiCalculator.category(30));
    }
    @Test public void parsesLocalizedNumbers() {
        assertEquals(70.5, BmiCalculator.parseDecimal("70,5"), 0);
        assertEquals(70.5, BmiCalculator.parseDecimal("٧٠٫٥"), 0);
        assertEquals(175, BmiCalculator.parseDecimal(" 175 "), 0);
    }
    @Test public void rejectsInvalidMeasurements() {
        for (String value : new String[]{"", "NaN", "Infinity", "-1", "1e3", "70..5", "1,2,3"})
            assertThrows(IllegalArgumentException.class, () -> BmiCalculator.parseDecimal(value));
        for (double height : new double[]{0, -1, 49, 301, Double.NaN, Double.POSITIVE_INFINITY})
            assertThrows(IllegalArgumentException.class, () -> BmiCalculator.calculate(height, 70));
        for (double weight : new double[]{0, -1, 9, 701, Double.NaN})
            assertThrows(IllegalArgumentException.class, () -> BmiCalculator.calculate(175, weight));
    }
}
