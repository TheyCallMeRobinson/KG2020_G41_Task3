package com.company.function;

public class Function_3 implements Function {
    public Double getYValue(double x) {
        return Math.abs(Math.pow(x, 1./3)) * Math.sin(x);
    }
    public String getForm() {return "x^(1/3) * sin(x)";}
}
