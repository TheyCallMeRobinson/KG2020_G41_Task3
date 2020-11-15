package com.company.function;

//абстрактная фабрика и синглтоны
public class Function_1 implements Function {
    private String functionNotation = "1 / (x + 1)";

    public void setFunctionNotation(String functionNotation) {
        this.functionNotation = functionNotation;
    }
    public Double getYValue(double x) {
        return 1 / (x+1);
    }
    public String getNotation() {return functionNotation;}
}
