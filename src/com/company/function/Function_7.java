package com.company.function;

public class Function_7 implements Function {
    private String functionNotation = "x^4 - |x^3|";
    public void setFunctionNotation(String functionNotation) {
        this.functionNotation = functionNotation;
    }
    public Double getYValue(double x) {
        return x*x*x*x - Math.abs(x*x*x);
    }
    public String getNotation() {return functionNotation;}
}
