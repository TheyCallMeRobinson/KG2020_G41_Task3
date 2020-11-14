package com.company;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;

public class MainFrame extends JFrame {
    private JPanel rootPanel;
    private JButton execute;
    private JPanel graphicsPanel;
    private JTextArea formulaInterpretator;
    private JSlider formulaSlider;
    private JPanel sliderContainer;
    private DrawPanel drawPanel;
    private JTextPane debug;
    private JPanel debugFieldContainer;
    private JButton reset;
    private String mousePosition;
    private String scale;
    private String screenMousePosition;

    private void createUIComponents() {
        //graphicsPanel = new DrawPanel();
    }

    public MainFrame() {
        $$$setupUI$$$();
        setContentPane(rootPanel);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        formulaSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                drawPanel.setFunction(formulaSlider.getValue());
                formulaInterpretator.setText("y = " + drawPanel.getFunctionForm());
            }
        });
        drawPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Double x = drawPanel.getMouseX();
                Double y = drawPanel.getMouseY();
                int realX = e.getX();
                int realY = e.getY();
                DecimalFormat df = new DecimalFormat("0.0000");
                mousePosition = df.format(x) + "; " + df.format(y);
                scale = "\n" + "Scale: " + drawPanel.getScale();
                screenMousePosition = "\n" + realX + " " + realY;

                debug.setText(mousePosition + screenMousePosition + scale);
            }
        });
        drawPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                Double x = drawPanel.getMouseX();
                Double y = drawPanel.getMouseY();
                int realX = mouseWheelEvent.getX();
                int realY = mouseWheelEvent.getY();
                DecimalFormat df = new DecimalFormat("0.0000");
                mousePosition = df.format(x) + "; " + df.format(y);
                scale = "\n" + "Scale: " + drawPanel.getScale();
                screenMousePosition = "\n" + realX + " " + realY;
                debug.setText(mousePosition + screenMousePosition + scale);
            }
        });
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawPanel.resetView();
            }
        });
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        rootPanel = new JPanel();
        rootPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        sliderContainer = new JPanel();
        sliderContainer.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(sliderContainer, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        formulaSlider = new JSlider();
        formulaSlider.setMaximum(6);
        formulaSlider.setOrientation(1);
        formulaSlider.setValue(0);
        formulaSlider.setValueIsAdjusting(true);
        formulaSlider.putClientProperty("JSlider.isFilled", Boolean.TRUE);
        sliderContainer.add(formulaSlider, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        debugFieldContainer = new JPanel();
        debugFieldContainer.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        sliderContainer.add(debugFieldContainer, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        debug = new JTextPane();
        Font debugFont = this.$$$getFont$$$("Courier New", -1, 14, debug.getFont());
        if (debugFont != null) debug.setFont(debugFont);
        debugFieldContainer.add(debug, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(150, 74), new Dimension(-1, 60), 0, false));
        reset = new JButton();
        reset.setText("Reset View");
        sliderContainer.add(reset, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        graphicsPanel = new JPanel();
        graphicsPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(graphicsPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        drawPanel = new DrawPanel();
        graphicsPanel.add(drawPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, new Dimension(800, 800), new Dimension(800, 800), new Dimension(800, 800), 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        rootPanel.add(panel1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        execute = new JButton();
        execute.setText("Draw");
        panel1.add(execute, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(100, 50), new Dimension(100, 50), 0, false));
        formulaInterpretator = new JTextArea();
        formulaInterpretator.setBackground(new Color(-657931));
        Font formulaInterpretatorFont = this.$$$getFont$$$("Courier New", -1, 26, formulaInterpretator.getFont());
        if (formulaInterpretatorFont != null) formulaInterpretator.setFont(formulaInterpretatorFont);
        formulaInterpretator.setForeground(new Color(-16777216));
        formulaInterpretator.setLineWrap(false);
        formulaInterpretator.setMargin(new Insets(10, 20, 0, 0));
        formulaInterpretator.setText("Function");
        formulaInterpretator.putClientProperty("html.disable", Boolean.FALSE);
        rootPanel.add(formulaInterpretator, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_NORTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(-1, 50), new Dimension(-1, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return rootPanel;
    }

}
