package com.company.function;

import com.company.RealPoint;

import java.util.ArrayList;

public class Function_4 implements Function {

    public double getYValue(double x) {
        return Math.log(x*x + 1) / (x*x + 2);
    }
    public String getForm() {return "log(x^2 + 1)\n------------\nx^2 + 2";}
}
