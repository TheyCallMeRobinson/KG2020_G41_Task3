package com.company.function;

import com.company.RealPoint;

import java.util.ArrayList;

public class Function_2 implements Function {

    public double getYValue(double x) {
        return x*x*x - x*x;
    }
}
