package com.company.function;

import com.company.RealPoint;

import java.util.ArrayList;

public class Function_6 implements Function {

    public double getYValue(double x) {
        return Math.abs(x*x*x*x - x*x*x + x*x - x);
    }
    public String getForm() {return "x^4 - x^3 + x^2 - x";}
}
