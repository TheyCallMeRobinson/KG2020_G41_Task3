package com.company.function;

import com.company.RealPoint;

import java.util.ArrayList;

public class Function_1 implements Function {
//абстрактная фабрика и синглтоны
    public double getYValue(double x) {
        return Math.exp(Math.sin(x * 3));
    }
    public String getForm() {return "e^sin(3x)";}
}
