package com.asapp.common.utils;

import java.util.List;

public class MathUtil {

    private MathUtil() {
        //Empty Constructor
    }

    public static double diffBtwMinOfMinListMaxOfMaxList(List<Double> minList, List<Double> maxList) {
        return maxList.stream().mapToDouble(Double::doubleValue).max().orElse(0) -
                minList.stream().mapToDouble(Double::doubleValue).min().orElse(0);
    }

}
