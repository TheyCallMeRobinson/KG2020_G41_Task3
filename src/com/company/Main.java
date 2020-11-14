package com.company;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        Double a = 1.;
        Double b = 0.;
        a = a / b;
        System.out.println(a.equals(Double.POSITIVE_INFINITY));
        mf.setSize(1920, 1080);
        mf.setVisible(true);
    }
}
