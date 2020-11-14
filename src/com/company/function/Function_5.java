package com.company.function;

public class Function_5 implements Function {

    public Double getYValue(double x) {
        return 1. / (x*x + 1);
    }
    public String getForm() {return "1 / (x^2 + 1)";}
}
