package com.company.function;

public class Function_7 implements Function {

    public Double getYValue(double x) {
        return x*x*x*x - Math.abs(x*x*x);
    }
    public String getForm() {return "x^4 - |x^3|";}
}
