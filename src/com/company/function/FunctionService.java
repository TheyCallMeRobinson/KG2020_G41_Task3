package com.company.function;

import com.company.RealPoint;

import java.util.ArrayList;

public class FunctionService {
    private Function f;

    public FunctionService(Function f) {
        this.f = f;
    }
/*
 * написать статический метод
 * принимающий linedrawer, screenconverter, function
 *
 */
    public ArrayList<RealPoint> execute(double first, double last) {
        ArrayList<RealPoint> points = new ArrayList<>();
        double step = (first - last) / 10;
        for(double i = first; i < last; i += 0.0001)
            points.add(new RealPoint(i, f.getYValue(i)));
        return points;
    }
}
