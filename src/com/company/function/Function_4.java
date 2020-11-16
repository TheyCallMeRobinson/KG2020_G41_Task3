package com.company.function;

public class Function_4 implements Function {
    private String functionNotation = "log(x^2 + 1)/(x^2 + 2)";
    public void setFunctionNotation(String functionNotation) {
        this.functionNotation = functionNotation;
    }
    public Double getYValue(double x) {
        return Math.log(x*x + 1) / (x*x + 2);
    }
    public String getNotation() {return functionNotation;}
}
