package com.company.function;

public class Function_3 implements Function {
    private String functionNotation = "x^(1/3) * sin(x)";

    public Double getYValue(double x) {
        return Math.abs(Math.pow(x, 1./3)) * Math.sin(x);
    }
    public String getNotation() {return functionNotation;}
    public void setFunctionNotation(String functionNotation) {
        this.functionNotation = functionNotation;
    }
}
