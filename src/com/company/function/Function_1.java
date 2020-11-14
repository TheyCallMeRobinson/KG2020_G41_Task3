package com.company.function;

public class Function_1 implements Function {
//абстрактная фабрика и синглтоны
    public Double getYValue(double x) {
        return 1 / (x+1);
    }
    public String getForm() {return "e^sin(3x)";}
}
