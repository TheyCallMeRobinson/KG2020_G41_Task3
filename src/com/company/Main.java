package com.company;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        long seed = (long)(Math.random() * Long.MAX_VALUE);
        Random generate = new Random(seed);
        MainWindow mw = new MainWindow();
        mw.setDefaultCloseOperation(3);
        mw.setSize(1920, 1080);
        mw.setVisible(true);
        System.out.println(generate.nextDouble());
        System.out.println(generate.nextDouble());
    }
}
