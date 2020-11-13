package com.company.function;

import com.company.RealPoint;

import java.util.ArrayList;

public class Function_7 implements Function {

    public double getYValue(double x) {
        return x*x*x*x - Math.abs(x*x*x);
    }
    public String getForm() {return "x^4 - |x^3|";}
}
