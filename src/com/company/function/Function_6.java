package com.company.function;

public class Function_6 implements Function {
    private String functionNotation = "x^4 - x^3 + x^2 - x";

    public void setFunctionNotation(String functionNotation) {}
    public Double getYValue(double x) {
        return Math.abs(x*x*x*x - x*x*x + x*x - x);
    }
    public String getNotation() {return functionNotation;}
}
