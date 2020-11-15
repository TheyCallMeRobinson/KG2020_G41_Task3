package com.company.function;

public interface Function {
   //ArrayList<RealPoint> execute(double first, double last);
   void setFunctionNotation(String s);
   Double getYValue(double x) throws Exception;
   String getNotation();
}
