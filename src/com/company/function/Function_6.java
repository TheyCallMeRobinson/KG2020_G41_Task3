package com.company.function;

public class Function_6 implements Function {

    public Double getYValue(double x) {
        return Math.abs(x*x*x*x - x*x*x + x*x - x);
    }
    public String getForm() {return "x^4 - x^3 + x^2 - x";}
}
