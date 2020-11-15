package com.company.drawer.linedrawer;

import com.company.drawer.ScreenPoint;

import java.awt.*;

public interface LineDrawer {
    Color getColor();
    void setColor(Color c);
    void drawLine(ScreenPoint p1, ScreenPoint p2);
}
