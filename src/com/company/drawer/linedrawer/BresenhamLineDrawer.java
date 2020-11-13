package com.company.drawer.linedrawer;

import com.company.ScreenPoint;
import com.company.drawer.pixeldrawer.PixelDrawer;

import java.awt.*;

/*
 * (x - x1)dy - (y - y1)dx = 0;
 * F(x, y) = (x - x1)dy - (y - y1)dx, если F(x,y) = 0, то точка находится на прямой, если >0, то ниже прямой и наоборот
 * (Xp, Yp) -> (Xp + 1, Yp + 1/2)
 * d = F(Xp + 1, Yp + 1/2) - понимаем, какой из пикселей нам рисовать; d0 = F(x1 + 1, y1 + 1/2)
 * d<0, d>0 => d<0: d' = F(Xp + 2, Yp + 1/2), deltad = d' - d; deltad = dy(spoiler)
 *             d>0: d' = F(Xp + 2, Yp + 3/2), deltad = d' - d = dy - dx;
 * d0 = F(x1 + 1, y1 + 1/2)(начальное d) = dy - dx/2 = 2dy - dx => все остальные deltad умножить на 2
 * */
public class BresenhamLineDrawer implements LineDrawer {
    private PixelDrawer pd;
    private Color color = Color.black;

    public BresenhamLineDrawer(PixelDrawer pd) {
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
        int y1 = p1.getY();
        int x2 = p2.getX();
        int y2 = p2.getY();

        pd.setPixel(x1, y1, color);
        pd.setPixel(x2, y2, color);
        if(Math.abs(x2 - x1) > Math.abs(y2 - y1)) {
            if(x1 > x2) {
                int temp = x1; x1 = x2; x2 = temp;
                temp = y1; y1 = y2; y2 = temp;
            }

            int k = y2 - y1 > 0 ? 1 : -1; // костыль для 2 и 4 четвертей
            int dx = x2 - x1, dy = y2 - y1, d = 2*dy*k - dx;
            int y = y1;

            for (int x = x1; x <= x2; x++) {
                pd.setPixel(x, y, color);
                if (d > 0) {
                    d += 2 * dy * k - 2 * dx;
                    y += k;
                } else {
                    d += 2 * dy * k;
                }
            }
        }
        else {
            if(y1 > y2) {
                int temp = y1; y1 = y2; y2 = temp;
                temp = x1; x1 = x2; x2 = temp;
            }

            int k = x2 - x1 > 0 ? 1 : -1; // костыль для 2 и 4 четвертей
            int dx = x2 - x1, dy = y2 - y1, d = 2*dx*k - dy;
            int y = y1;

            for (int x = x1; y <= y2; y++) {
                pd.setPixel(x, y, color);

                if (d > 0) {
                    d += 2 * dx * k - 2 * dy;
                    x += k;
                } else {
                    d += 2 * dx * k;
                }
            }
        }
    }

    /*
    private void plot4Pixels(int Cx, int Cy, int x, int y) {
        pd.setPixel(Cx + x, Cy + y, color);
        pd.setPixel(Cx + x, Cy - y, color);
        pd.setPixel(Cx - x, Cy + y, color);
        pd.setPixel(Cx - x, Cy - y, color);
    }
    private void plot4Lines(int Cx, int Cy, int x, int y) {
        drawLine(Cx + x, Cy + y, Cx, Cy);
        drawLine(Cx + x + 1, Cy + y + 1, Cx, Cy);
        drawLine(Cx + x - 1, Cy + y - 1, Cx, Cy);
        drawLine(Cx + x + 1, Cy + y - 1, Cx, Cy);
        drawLine(Cx + x - 1, Cy + y + 1, Cx, Cy);
        drawLine(Cx + x, Cy - y, Cx, Cy);
        drawLine(Cx + x + 1, Cy - y + 1, Cx, Cy);
        drawLine(Cx + x - 1, Cy - y - 1, Cx, Cy);
        drawLine(Cx + x + 1, Cy - y - 1, Cx, Cy);
        drawLine(Cx + x - 1, Cy - y + 1, Cx, Cy);
        drawLine(Cx - x, Cy + y, Cx, Cy);
        drawLine(Cx - x + 1, Cy + y + 1, Cx, Cy);
        drawLine(Cx - x - 1, Cy + y - 1, Cx, Cy);
        drawLine(Cx - x + 1, Cy + y - 1, Cx, Cy);
        drawLine(Cx - x - 1, Cy + y + 1, Cx, Cy);
        drawLine(Cx - x, Cy - y, Cx, Cy);
        drawLine(Cx - x + 1, Cy - y + 1, Cx, Cy);
        drawLine(Cx - x - 1, Cy - y - 1, Cx, Cy);
        drawLine(Cx - x + 1, Cy - y - 1, Cx, Cy);
        drawLine(Cx - x - 1, Cy - y + 1, Cx, Cy);
        //жесть
    }

    public void drawEllipse(int Cx, int Cy, int a, int b) {
        drawHArc(Cx, Cy, a, b);
        drawVArc(Cx, Cy, a, b);
    }
    public void fillEllipse(int Cx, int Cy, int a, int b) {
//        boolean s = a > b;
//        for(int i = 0; i < a; i++) {
//            drawEllipse(Cx, Cy, a - i, b - i);
//        }
//        fillHArc(Cx, Cy, a, b);
//        fillVArc(Cx, Cy, a, b);
        fill(Cx, Cy, a, b, Cx, Cy, 1);
    }

    public void fill(int Cx, int Cy, int a, int b, int x, int y, int step) {
        if(b * b * (Cx - x) * (Cx - x) + a * a * (Cy - y) * (Cy - y) < a*a*b*b && step < 100) {
            pd.setPixel(x, y, color);
            fill(Cx, Cy, a, b, x+1, y+1, step++);
            fill(Cx, Cy, a, b, x+1, y-1, step++);
            fill(Cx, Cy, a, b, x-1, y+1, step++);
            fill(Cx, Cy, a, b, x-1, y-1, step++);
        }
    }

    public void drawCircle(int x, int y, int r) {
        drawEllipse(x, y, r, r);
    }
    public void fillCircle(int x, int y, int r) {
        fillEllipse(x, y, r, r);
    }

    private void fillHArc(int Cx, int Cy, int a, int b) {
        int aSquare = 2 * a * a;
        int bSquare = 2 * b * b;
        int x = a, y = 0;
        int dx = b * b * (1 - 2 * a);
        int dy = a * a;
        int d = 0;
        int stopX = bSquare * a;
        int stopY = 0;
        while (stopX >= stopY) {
            plot4Lines(Cx, Cy, x, y);
            y++;
            stopY += aSquare;
            d += dy;
            dy += aSquare;
            if (2 * d + dx > 0) {
                x--;
                stopX -= bSquare;
                d += dx;
                dx += bSquare;
            }
        }
    }
    private void fillVArc(int Cx, int Cy, int a, int b) {
        int aSquare = 2 * a * a;
        int bSquare = 2 * b * b;
        int x = 0, y = b;
        int dx = b * b;
        int dy = a * a*(1 - 2*b);
        int d = 0;
        int stopX = 0;
        int stopY = aSquare * b;
        while(stopX <= stopY) {
            plot4Lines(Cx, Cy, x, y);
            x++;
            stopX += bSquare;
            d += dx;
            dx += bSquare;
            if(2*d + dy > 0) {
                y--;
                stopY -= aSquare;
                d += dy;
                dy += aSquare;
            }
        }
    }
    private void drawHArc(int Cx, int Cy, int a, int b) {
        int aSquare = 2 * a * a;
        int bSquare = 2 * b * b;
        int x = a, y = 0;
        int dx = b * b * (1 - 2*a);
        int dy = a * a;
        int d = 0;
        int stopX = bSquare * a;
        int stopY = 0;
        while(stopX >= stopY) {
            plot4Pixels(Cx, Cy, x, y);
            y++;
            stopY += aSquare;
            d += dy;
            dy += aSquare;
            if(2*d + dx > 0) {
                x--;
                stopX -= bSquare;
                d += dx;
                dx += bSquare;
            }
        }
    }
    private void drawVArc(int Cx, int Cy, int a, int b) {
        int aSquare = 2 * a * a;
        int bSquare = 2 * b * b;
        int x = 0, y = b;
        int dx = b * b;
        int dy = a * a*(1 - 2*b);
        int d = 0;
        int stopX = 0;
        int stopY = aSquare * b;
        while(stopX <= stopY) {
            plot4Pixels(Cx, Cy, x, y);
            x++;
            stopX += bSquare;
            d += dx;
            dx += bSquare;
            if(2*d + dy > 0) {
                y--;
                stopY -= aSquare;
                d += dy;
                dy += aSquare;
            }
        }
    }
/*

    public void drawBresenhamCircle(int x, int y, int radius) {
        int _x = radius;
        int _y = 0;
        int radiusError = 1 - _x;
        while (_x >= _y) {
            pd.setPixel(_x + x + radius, _y + y + radius, Color.BLACK);
            pd.setPixel(_y + x + radius, _x + y + radius, Color.BLACK);
            pd.setPixel(-_x + x + radius, _y + y + radius, Color.BLACK);
            pd.setPixel(-_y + x + radius, _x + y + radius, Color.BLACK);
            pd.setPixel(-_x + x + radius, -_y + y + radius, Color.BLACK);
            pd.setPixel(-_y + x + radius, -_x + y + radius, Color.BLACK);
            pd.setPixel(_x + x + radius, -_y + y + radius, Color.BLACK);
            pd.setPixel(_y + x + radius, -_x + y + radius, Color.BLACK);
            _y++;
            if (radiusError < 0) {
                radiusError += 2 * _y + 1;
            } else {
                _x--;
                radiusError += 2 * (_y - _x + 1);
            }
        }
    }

    private void pixel4(int x, int y, int _x, int _y, Color color) {// Рисование пикселя для первого квадранта, и, симметрично, для остальных
        pd.setPixel(x + _x, y + _y, color);
        pd.setPixel(x + _x, y - _y, color);
        pd.setPixel(x - _x, y - _y, color);
        pd.setPixel(x - _x, y + _y, color);
    }

    public void draw_ellipse(int x, int y, int a, int b) {
        int _x = 0;
        int _y = b;
        int a_sqr = a * a;
        int b_sqr = b * b;
        int delta = 4 * b_sqr * ((_x + 1) * (_x + 1)) + a_sqr * ((2 * _y - 1) * (2 * _y - 1)) - 4 * a_sqr * b_sqr;
        while (a_sqr * (2 * _y - 1) > 2 * b_sqr * (_x + 1)) {
            pd.setPixel(x + _x, y + _y, Color.BLACK);
            pd.setPixel(x + _x, y - _y, Color.BLACK);
            pd.setPixel(x - _x, y - _y, Color.BLACK);
            pd.setPixel(x - _x, y + _y, Color.BLACK);
            if (delta < 0) {
                _x++;
                delta += 4 * b_sqr * (2 * _x + 3);
            } else {
                _x++;
                delta = delta - 8 * a_sqr * (_y - 1) + 4 * b_sqr * (2 * _x + 3);
                _y--;
            }
        }
        delta = b_sqr * ((2 * _x + 1) * (2 * _x + 1)) + 4 * a_sqr * ((_y + 1) * (_y + 1)) - 4 * a_sqr * b_sqr;
        while (_y + 1 != 0) {
            pd.setPixel(x + _x, y + _y, Color.BLACK);
            pd.setPixel(x + _x, y - _y, Color.BLACK);
            pd.setPixel(x - _x, y - _y, Color.BLACK);
            pd.setPixel(x - _x, y + _y, Color.BLACK);
            if (delta < 0) {
                _y--;
                delta += 4 * a_sqr * (2 * _y + 3);
            } else {
                _y--;
                delta = delta - 8 * b_sqr * (_x + 1) + 4 * a_sqr * (2 * _y + 3);
                _x++;
            }
        }
    }
*/
}
