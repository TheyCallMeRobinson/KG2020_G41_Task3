package com.company.function;

import com.company.parser.MatchParser;

public class Function_custom implements Function {
    private MatchParser mp = new MatchParser();
    private String functionNotation = "";

    public void setFunctionNotation(String functionNotation) {
        this.functionNotation = functionNotation;
    }

    public Double getYValue(double x) throws Exception {
        mp.setVariable("x", x);
        return mp.parse(functionNotation);
    }

    public String getNotation() {
        return functionNotation;
    }
}
