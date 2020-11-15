package com.company.legacy;

import com.company.function.*;
import com.company.gui.DrawPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

public class MainWindow extends JFrame {
    JSlider slider = new JSlider(1, 7, 1);
    DrawPanel mp = new DrawPanel();
    JPanel controls = new JPanel();
    ArrayList<Function> f = new ArrayList<>();

    public MainWindow() {
        slider.setVisible(true);
        f.add(new Function_1());
        f.add(new Function_2());
        f.add(new Function_3());
        f.add(new Function_4());
        f.add(new Function_5());
        f.add(new Function_6());
        f.add(new Function_7());

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //mp.setF(f.get(slider.getValue()));
            }
        });
        controls.add(slider);
        this.add(controls);
        this.add(mp);
    }
}