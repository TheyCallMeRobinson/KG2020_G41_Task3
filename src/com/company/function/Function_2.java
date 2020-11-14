package com.company.function;

public class Function_2 implements Function {

    public Double getYValue(double x) {
        return x*x*x - x*x;
    }
    public String getForm() {return "x^3 - x^2";}
}
