package com.company.function;

import com.company.RealPoint;

import java.util.ArrayList;

public class Function_5 implements Function {

    public double getYValue(double x) {
        return 1. / (x*x + 1);
    }
    public String getForm() {return "1 / (x^2 + 1)";}
}
