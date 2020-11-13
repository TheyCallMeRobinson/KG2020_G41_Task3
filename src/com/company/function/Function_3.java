package com.company.function;

import com.company.RealPoint;

import java.util.ArrayList;

public class Function_3 implements Function {
    public double getYValue(double x) {
        return Math.pow(x, 1./3) * Math.sin(x);
    }
}
