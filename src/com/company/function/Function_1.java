package com.company.function;

import com.company.RealPoint;

import java.util.ArrayList;

public class Function_1 implements Function {
    @Override
    public ArrayList<RealPoint> execute(double first, double last) {
        ArrayList<RealPoint> points = new ArrayList<>();
        for(double i = first; i < last; i += 0.0001)
            points.add(new RealPoint(i, getFormulaValue(i)));
        return points;
    }

    private double getFormulaValue(double x) {
        return Math.exp(Math.sin(x * 3));
    }
}
