package com.dgut.sell.util;

public class MathUtil {

    private static final Double MONEY_RANGE = 0.01;

    public static boolean equal(Double d1, Double d2) {

        if (Math.abs(d1 - d2) < MONEY_RANGE) {
            return true;
        } else return false;
    }
}
