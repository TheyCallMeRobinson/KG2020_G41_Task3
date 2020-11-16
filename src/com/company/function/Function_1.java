package com.company.function;

//абстрактная фабрика и синглтоны
public class Function_1 implements Function {
    private String functionNotation = "e^sin(x*3)";

    public Double getYValue(double x) {
        return Math.exp(Math.sin(x*3));
    }
    public String getNotation() {return functionNotation;}
    public void setFunctionNotation(String functionNotation) {}
}
