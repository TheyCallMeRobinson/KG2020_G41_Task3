package com.company.function;

public class Function_5 implements Function {
    private String functionNotation = "1 / (x^2 + 1)";
    public void setFunctionNotation(String s) {}
    public Double getYValue(double x) {
        return 1. / (x*x + 1);
    }
    public String getNotation() {return functionNotation;}
}
