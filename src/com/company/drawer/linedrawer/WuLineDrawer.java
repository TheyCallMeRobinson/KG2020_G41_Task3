package com.company.drawer.linedrawer;


import com.company.drawer.ScreenPoint;
import com.company.drawer.pixeldrawer.PixelDrawer;

import java.awt.*;

public class WuLineDrawer implements LineDrawer {
    /*
     * алгоритм брезенхема, но закрашиваются 2 пикселя с разной интенсивностью
     * */
    private PixelDrawer pd;
    private Color color = Color.black;

    public WuLineDrawer(PixelDrawer pd) { this.pd = pd; }
    public WuLineDrawer(PixelDrawer pd, Color color) { this.pd = pd; this.color = color; }


    private double remotenessFirst(double a) {
        return 1 - remotenessSecond(a);
    }
    private double remotenessSecond(double a) {
        return a > 0 ? a - (int)a :  a - (int)(a + 1);
    }
    private void drawPixel(int x, int y, double opacity) {
        pd.setPixel(x, y, new Color(color.getRed(), color.getGreen(), color.getBlue(), (int)(255 * (1 - opacity))));
    }

    public void drawEllipse() {

    }

    public void drawLine(int x1, int y1, int x2, int y2) {
        boolean isSteep = Math.abs(y2 - y1) > Math.abs(x2 - x1);
        if (isSteep){
            int temp = x1; x1 = y1; y1 = temp;
            temp = x2; x2 = y2; y2 = temp;
        }
        if (x1 > x2){
            int temp = x1; x1 = x2; x2 = temp;
            temp = y1; y1 = y2; y2 = temp;
        }

        int dx = x2 - x1;
        int dy = y2 - y1;
        double gradient = (double)dy/dx;
        double realY = y1;

        for(int x = x1; x <= x2; x++) {
            drawPixel(isSteep ? (int) (realY) + 1 : x, isSteep ? x : (int) (realY) + 1, remotenessFirst(realY));
            drawPixel(isSteep ? (int) (realY) : x, isSteep ? x : (int) (realY), remotenessSecond(realY));
            realY += gradient;
        }
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color c) {
        this.color = c;
    }

    @Override
    public void drawLine(ScreenPoint p1, ScreenPoint p2) {
        int x1 = p1.getX();
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();

        boolean isSteep = Math.abs(y2 - y1) > Math.abs(x2 - x1);
        if (isSteep){
            int temp = x1; x1 = y1; y1 = temp;
            temp = x2; x2 = y2; y2 = temp;
        }
        if (x1 > x2){
            int temp = x1; x1 = x2; x2 = temp;
            temp = y1; y1 = y2; y2 = temp;
        }

        int dx = x2 - x1;
        int dy = y2 - y1;
        double gradient = (double)dy/dx;
        double realY = y1;

        for(int x = x1; x <= x2; x++) {
            drawPixel(isSteep ? (int) (realY) + 1 : x, isSteep ? x : (int) (realY) + 1, remotenessFirst(realY));
            drawPixel(isSteep ? (int) (realY) : x, isSteep ? x : (int) (realY), remotenessSecond(realY));
            realY += gradient;
        }
    }
}
