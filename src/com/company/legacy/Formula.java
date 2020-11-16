package com.company.legacy;

import java.util.*;
//не работает
class Formula {
    private List<String> reversePolishNotation = new ArrayList<>();
    private Map<String, Double> variables = new HashMap<>();

    private String deleteSpaces(String s) {
        char[] array = s.toCharArray();
        int count = 0;

        for(int i = 0; i < array.length; i++) {
            if (array[i] == ' ') {
                count++;
                for(int j = i; j < array.length-1; j++) {
                    array[j] = array[j+1];
                }
            }
        }

        char[] newArray = new char[array.length - count];

        for(int i = 0; i < newArray.length; i++) {
            newArray[i] = array[i];
        }

        return new String(newArray);
    }

    private List<String> divideByOperandParts(char[] array) {
        List<String> str = new ArrayList<>();

        int i = 0;
        while(i < array.length) {
            StringBuilder s = new StringBuilder();
            while(i < array.length && isNumber(array[i])) {
                s.append(array[i]);
                i++;
            }
            if (!s.toString().equals("")) {
                str.add(s.toString());
            }
            if(i < array.length && (isOperand(array[i]) || array[i] == '(' || array[i] == ')')) {
                s = new StringBuilder();
                s.append(array[i]);
                str.add(s.toString());
                i++;
            }
        }

        return str;
    }

    private double doThings(String op, double a, double b) {
        double result = 0;

        switch(op) {
            case "*":
                result = b * a;
                break;
            case "/":
                result = b / a;
                break;
            case "+":
                result = b + a;
                break;
            case "-":
                result = b - a;
                break;
        }

        return result;
    }

    public void prepare(String s) {
        Stack<String> rpn = new Stack<>();
        List<String> output = new ArrayList<>();
        List<String> array = new ArrayList<>(divideByOperandParts(deleteSpaces(s).toCharArray()));

        int j = 0;
        while(j < array.size()) {
            if(isNumber(array.get(j))) {
                if (isVariable(array.get(j)))
                    variables.put(array.get(j), 0.0);
                output.add(array.get(j));
            }
            if(array.get(j).equals("(")) {
                rpn.push(array.get(j));
            }
            if(array.get(j).equals(")")) {
                while(!rpn.peek().equals("(")) {
                    output.add(rpn.pop());
                }
                rpn.pop(); // delete '('
            }
            if(isHighBinary(array.get(j))) {
                while (!rpn.empty() && (isHighBinary(rpn.peek()))) {
                    output.add(rpn.pop());
                }
                rpn.push(array.get(j));
            }
            if(isLowBinary(array.get(j))) {
                while (!rpn.empty() && (isOperand(rpn.peek()))) {
                    output.add(rpn.pop());
                }
                rpn.push(array.get(j));
            }
            j++;
        }

        while (!rpn.empty()) {
            output.add(rpn.pop());
        }

        this.reversePolishNotation = output;
    }

    public double execute(Double ... v) throws Exception{
        Stack<Double> stack = new Stack<>();
        Scanner scn = new Scanner(System.in);

        if(v.length == 0 || v.length != variables.size()) {
            for (String i : variables.keySet()) {
                System.out.print("Enter " + i + ": ");
                variables.put(i, scn.nextDouble());
            }
        } else {
            int j = 0;
            for(String i : variables.keySet()) {
                variables.put(i, v[j]);
                j++;
            }
        }


        for(int i = 0; i < reversePolishNotation.size(); i++) {
            if(isNumber(reversePolishNotation.get(i))) {
                if(isVariable(reversePolishNotation.get(i))) {
                    stack.push(variables.get(reversePolishNotation.get(i)));
                } else {
                    stack.push(convertToDouble(reversePolishNotation.get(i)));
                }
            }
            if(isOperand(reversePolishNotation.get(i))) {
                stack.push(doThings(reversePolishNotation.get(i), stack.pop(), stack.pop()));
            }
        }

        return stack.peek();
    }

    private boolean isNumber(char ch) {
        return (!isOperand(ch) || isVariable(ch)) && ch != '(' && ch != ')';
    }
    private boolean isNumber(String s) {
        return (!isOperand(s) || isVariable(s)) && !s.equals("(") && !s.equals(")");
    }

    private boolean isHighBinary(char ch) {
        return ch == '*' || ch == '/';
    }
    private boolean isLowBinary(char ch) {
        return ch == '+' || ch == '-';
    }

    private boolean isHighBinary(String s) {
        return s.equals("*") || s.equals("/");
    }
    private boolean isLowBinary(String s) {
        return s.equals("+") || s.equals("-");
    }

    private boolean isOperand(char ch) {
        return isHighBinary(ch) || isLowBinary(ch);
    }
    private boolean isOperand(String s) {
        return isHighBinary(s) || isLowBinary(s);
    }

    private double convertToDouble(String str) {
        return Double.parseDouble(str);
    }

    private boolean isVariable(char ch) {
        return Character.isLetter(ch);
    }
    private boolean isVariable(String s) {
        char[] ch = s.toCharArray();
        return Character.isLetter(ch[0]);
    }

}