package com.pynexia.week1.bmi;

public final class BmiCalculator {
    public enum Category { UNDERWEIGHT, NORMAL, OVERWEIGHT, OBESE }
    private BmiCalculator() {}

    public static double parseDecimal(String input) {
        String normalized = input.trim().replace(',', '.').replace('٫', '.');
        StringBuilder number = new StringBuilder();
        for (char character : normalized.toCharArray()) {
            int digit = Character.digit(character, 10);
            number.append(digit >= 0 ? (char) ('0' + digit) : character);
        }
        if (!number.toString().matches("[0-9]+(?:\\.[0-9]+)?")) throw new IllegalArgumentException("Invalid number");
        double value = Double.parseDouble(number.toString());
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid number");
        return value;
    }

    public static boolean validHeight(double height) { return Double.isFinite(height) && height >= 50 && height <= 300; }
    public static boolean validWeight(double weight) { return Double.isFinite(weight) && weight >= 10 && weight <= 700; }

    public static double calculate(double heightCm, double weightKg) {
        if (!validHeight(heightCm) || !validWeight(weightKg)) throw new IllegalArgumentException("Measurements out of range");
        double heightM = heightCm / 100.0;
        return weightKg / (heightM * heightM);
    }

    public static Category category(double bmi) {
        if (!Double.isFinite(bmi) || bmi <= 0) throw new IllegalArgumentException("Invalid BMI");
        if (bmi < 18.5) return Category.UNDERWEIGHT;
        if (bmi < 25) return Category.NORMAL;
        if (bmi < 30) return Category.OVERWEIGHT;
        return Category.OBESE;
    }
}
