package com.company.functions;

import com.company.RealPoint;
import com.company.ScreenPoint;

import java.awt.*;
import java.util.ArrayList;

public interface Function {
   ArrayList<RealPoint> execute(double first, double last);
}
