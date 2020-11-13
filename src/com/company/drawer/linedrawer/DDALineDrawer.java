package com.company.drawer.linedrawer;

import com.company.ScreenPoint;
import com.company.drawer.pixeldrawer.PixelDrawer;

import java.awt.*;

public class DDALineDrawer implements LineDrawer {
    private PixelDrawer pd;
    private Color color = Color.black;

    public DDALineDrawer(PixelDrawer pd) {
        this.pd = pd;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        int x1 = p1.getX();
        int x2 = p2.getX();
        int y1 = p1.getY();
        int y2 = p2.getY();
        double dx = x2 - x1;
        double dy = y2 - y1;
        if (Math.abs(dx) > Math.abs(dy)) {
            if(x1 > x2) {
                int tmp = x1; x1 = x2; x2 = tmp;
                tmp = y1; y1 = y2; y2 = tmp;
            }
            double k = dy / dx;
            for(int j = x1; j <= x2; j++) {
                double i = k * (j - x1) + y1;
                pd.setPixel(j, (int)i, Color.RED);
            }
        } else if (Math.abs(dx) <= Math.abs(dy)) {
            if(y1 > y2) {
                int tmp = y1; y1 = y2; y2 = tmp;
                tmp = x1; x1 = x2; x2 = tmp;
            }
            double k = dx / dy;

            for(int i = y1; i <= y2; i++) {
                double j = (i - y1)*k + x1;
                pd.setPixel((int)j, i, Color.BLUE);
            }
        }
    }
}
