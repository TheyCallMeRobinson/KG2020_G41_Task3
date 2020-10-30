package com.company.functions;

import com.company.RealPoint;

import java.util.ArrayList;

public class Function_6 implements Function {
    @Override
    public ArrayList<RealPoint> execute(double first, double last) {
        ArrayList<RealPoint> points = new ArrayList<>();
        for(double i = first; i < last; i += 0.0001)
            points.add(new RealPoint(i, getFormulaValue(i)));
        return points;
    }

    private double getFormulaValue(double x) {
        return Math.abs(x*x*x*x - x*x*x + x*x - x);
    }
}
