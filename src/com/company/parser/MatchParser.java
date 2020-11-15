package com.company.parser;

import java.util.HashMap;

public class MatchParser {
    private HashMap<String, Double> variables = new HashMap<>();

    public MatchParser() {}

    public MatchParser(String variableName, double value) {
        variables.put(variableName, value);
    }

    public void setVariable(String variableName, Double variableValue) {
        variables.put(variableName, variableValue);
    }

    public Double getVariable(String variableName) {
        if (!variables.containsKey(variableName)) {
            System.err.println("Error: Try get unexists variable '" + variableName + "'");
            return 0.0;
        }
        return variables.get(variableName);
    }

    public double parse(String s) throws Exception {
        s = s.replaceAll("\\s","");
        Result result = lowLevelOperation(s);
        if (!result.rest.isEmpty()) {
            throw new Exception("Expression can't be parsed");
        }
        return result.acc;
    }

    private Result lowLevelOperation(String s) throws Exception {
        Result current = highLevelOperation(s);
        double acc = current.acc;

        while (current.rest.length() > 0) {
            if (!(current.rest.charAt(0) == '+' || current.rest.charAt(0) == '-')) break;

            char sign = current.rest.charAt(0);
            String next = current.rest.substring(1);

            current = highLevelOperation(next);
            if (sign == '+') {
                acc += current.acc;
            } else {
                acc -= current.acc;
            }
        }
        return new Result(acc, current.rest);
    }

    private Result bracket(String s) throws Exception {
        char zeroChar = s.charAt(0);
        if (zeroChar == '(') {
            Result r = lowLevelOperation(s.substring(1));
            try {
                r.rest = r.rest.substring(1);
            }
            catch(Exception e) {
                throw new Exception("Error: extra open bracket" + e.getMessage());
            }
            return r;
        }
        return functionVariable(s);
    }

    private Result functionVariable(String s) throws Exception {
        String f = "";
        int i = 0;
        while (i < s.length() && (Character.isLetter(s.charAt(i)) || (Character.isDigit(s.charAt(i)) && i > 0))) {
            f += s.charAt(i);
            i++;
        }
        if (!f.isEmpty()) {
            if (s.length() > i && s.charAt(i) == '(') {
                Result r = bracket(s.substring(f.length()));
                return processFunction(f, r);
            } else {
                return new Result(getVariable(f), s.substring(f.length()));
            }
        }
        return constant(s);
    }

    private Result highLevelOperation(String s) throws Exception {
        Result current = bracket(s);

        double acc = current.acc;
        while (true) {
            if (current.rest.length() == 0) {
                return current;
            }
            char sign = current.rest.charAt(0);
            if ((sign != '*' && sign != '/')) return current;

            String next = current.rest.substring(1);
            Result right = bracket(next);

            if (sign == '*') {
                acc *= right.acc;
            } else {
                acc /= right.acc;
            }

            current = new Result(acc, right.rest);
        }
    }

    private Result constant(String s) throws Exception {
        int i = 0;
        int dotCount = 0;
        boolean negative = false;
        if (s.charAt(0) == '-') {
            negative = true;
            s = s.substring(1);
        }
        while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
            if (s.charAt(i) == '.' && ++dotCount > 1) {
                throw new Exception("Not valid number '" + s.substring(0, i + 1) + "'");
            }
            i++;
        }
        if (i == 0) {
            throw new Exception("Can't get valid number in '" + s + "'");
        }

        double dPart = Double.parseDouble(s.substring(0, i));
        if (negative) dPart = -dPart;
        String restPart = s.substring(i);

        return new Result(dPart, restPart);
    }

    private Result processFunction(String func, Result r) throws Exception {
        try {
        if (func.equals("sin")) {
            return new Result(Math.sin(r.acc), r.rest);
        } else if (func.equals("cos")) {
            return new Result(Math.cos(r.acc), r.rest);
        } else if (func.equals("tan") || func.equals("tg")) {
            return new Result(Math.tan(r.acc), r.rest);
        } else if (func.equals("log")) {
            return new Result(Math.log(r.acc), r.rest);
        }
        } catch (Exception e) {
            throw new Exception("function '" + func + "' is not defined");
        }
        return r;
    }
}
