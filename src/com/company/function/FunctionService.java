package com.company.function;

import com.company.drawer.RealPoint;
import com.company.drawer.ScreenConverter;
import com.company.drawer.ScreenPoint;
import com.company.drawer.linedrawer.LineDrawer;

import java.awt.*;
import java.util.ArrayList;

public class FunctionService {

    public static void drawFunction(LineDrawer ld, ScreenConverter sc, Function f) throws Exception {
        ld.setColor(new Color(88, 209, 67));
        Double first = sc.getX();
        Double last = sc.getX() + sc.getW();
        Double step = (last - first) / 500;
        ArrayList<RealPoint> points = new ArrayList<>();

        for(double i = first; i <= last; i += step)
            if(Math.abs(f.getYValue(i) - f.getYValue(i + step)) > 0.000001)
                points.add(new RealPoint(i, f.getYValue(i)));

        for(int i = 0; i < points.size() - 1; i++) {
            ScreenPoint left = new ScreenPoint(sc.r2s(points.get(i)).getX(), sc.r2s(points.get(i)).getY());
            ScreenPoint right = new ScreenPoint(sc.r2s(points.get(i + 1)).getX(), sc.r2s(points.get(i + 1)).getY());
            if(sc.s2r(left).getY() > sc.getY() - 2*sc.getH() && sc.s2r(left).getY() < sc.getY() + sc.getH() && sc.s2r(right).getY() > sc.getY() - 2*sc.getH() && sc.s2r(right).getY() < sc.getY() + sc.getH()) {
                ld.drawLine(left, right);
            }
        }
    }
}
/*
            Line line = new Line(points.get(i + 1), points.get(i));
            Line prev = new Line(points.get(i), points.get(i - 1));
            Line next = new Line(points.get(i + 2), points.get(i + 1));
            if (!(line.getLength() > prev.getLength() && line.getLength() > next.getLength())) {
                ld.drawLine(sc.r2s(line.getP1()), sc.r2s(line.getP2()));
                if (i == 1)
                    ld.drawLine(sc.r2s(prev.getP1()), sc.r2s(prev.getP2()));
                if (i == points.size() - 1)
                    ld.drawLine(sc.r2s(next.getP1()), sc.r2s(next.getP2()));
            }
//                ScreenPoint left = new ScreenPoint(sc.r2s(points.get(i)).getX(), sc.r2s(points.get(i)).getY());
//                ScreenPoint right = new ScreenPoint(sc.r2s(points.get(i + 1)).getX(), sc.r2s(points.get(i + 1)).getY());
*/
