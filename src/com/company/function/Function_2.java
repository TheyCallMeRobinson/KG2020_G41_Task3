package com.company.function;

public class Function_2 implements Function {
    private String functionNotation = "x^3 - x^2";

    public Double getYValue(double x) {
        return x*x*x - x*x;
    }
    public String getNotation() {return functionNotation;}
    public void setFunctionNotation(String functionNotation) {
        this.functionNotation = functionNotation;
    }
}
