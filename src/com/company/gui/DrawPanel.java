package com.company.gui;

import com.company.drawer.RealPoint;
import com.company.drawer.ScreenConverter;
import com.company.drawer.ScreenPoint;
import com.company.drawer.linedrawer.*;
import com.company.drawer.pixeldrawer.BufferedImagePixelDrawer;
import com.company.drawer.pixeldrawer.PixelDrawer;
import com.company.function.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, ChangeListener {
    private ArrayList<Line> lines = new ArrayList<>();
    private ArrayList<Function> functions = new ArrayList<>();
    private Function function;
    private ScreenConverter sc = new ScreenConverter(-5, 5, 10, 10, 800, 800);
    private ScreenConverter scBackup = new ScreenConverter(-5, 5, 10, 10, 800, 800);
    private Line currentLine;
    private ScreenPoint prevDrag;
    private RealPoint mouseCoordinates = new RealPoint(0, 0);
    private int scaleRotation = 1;
    private double scale = 1;
    private Stack<Double> scaleValues = new Stack<>();

    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
        functions.add(new Function_1());
        functions.add(new Function_2());
        functions.add(new Function_3());
        functions.add(new Function_4());
        functions.add(new Function_5());
        functions.add(new Function_6());
        functions.add(new Function_7());
        functions.add(new Function_custom());
    }

    public Double getScale() {
        return scale;
    }
    public Double getMouseX() {
        return mouseCoordinates != null ? mouseCoordinates.getX() : 0;
    }
    public Double getMouseY() {
        return mouseCoordinates != null ? mouseCoordinates.getY() : 0;
    }
    public String getFunctionForm() {return function.getNotation();}
    public void setFunction(int number) {
        function = functions.get(number);
        repaint();
    }
    public void resetView() {
        sc = new ScreenConverter(scBackup);
        scale = 1;
        scaleRotation = 1;
        repaint();
    }
    public void setFunctionNotation(String functionNotation) {
        function.setFunctionNotation(functionNotation);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        sc.setScreenW(getWidth());
        sc.setScreenH(getHeight());
        Graphics bi_g = bi.getGraphics();
        bi_g.setColor(new Color(255, 255, 255));
        bi_g.fillRect(0, 0, getWidth(), getHeight());
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new BresenhamLineDrawer(pd);
        scaleValues.push(1.);
        try {
            drawAll(ld, bi_g);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bi_g.dispose();
        g.drawImage(bi, 0, 0, null);
    }

    private void drawAll(LineDrawer ld, Graphics g) throws Exception {
        drawGrid(ld, g);
        drawAxes(ld);
        //drawBounds(ld);
        ld.setColor(Color.blue);
        for(Line l : lines)
            drawLine(ld, l);
        if(currentLine != null)
            drawLine(ld, currentLine);
        if(function != null) {
            drawFunction(ld);
            drawFunctionValue(g);
        }
        drawPosition(g);
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }
    private void drawFunction(LineDrawer ld) throws Exception {
        FunctionService.drawFunction(ld, sc, function);
    }
    private void drawGrid(LineDrawer ld, Graphics g) {
        ld.setColor(new Color(158, 180, 255, 172));
        g.setColor(Color.black);
        double step = scale;
        double xStart = sc.getX() - ((sc.getX() + step) % step);
        double yStart = sc.getY() - ((sc.getY() + step) % step) + step; // нетипичная формула для нахождения левой верхней точки по шкале, соседствующей с позицией левой верхней точки экрана
        double xFinish = sc.getX() + sc.getW();
        double yFinish = sc.getY() - sc.getH();

        for(double i = xStart; i < xFinish; i += step) {
            drawLine(ld, new Line(new RealPoint(i, sc.getY()), new RealPoint(i, sc.getY() - sc.getH())));
            DecimalFormat df = new DecimalFormat("0.####");
            String s = df.format(i);
            if(Math.abs(i) < 0.000001) s = "0";
            int opacity = s.length() * 3;
            double x = sc.r2s(new RealPoint(i, sc.getY() - sc.getH())).getX() - opacity;
            double y = sc.r2s(new RealPoint(i, sc.getY() - sc.getH())).getY();
            g.drawString(s, (int)x, (int)y);
        }
        for(double i = yStart; i > yFinish; i -= step) {
            drawLine(ld, new Line(new RealPoint(sc.getX(), i), new RealPoint(sc.getX() + sc.getW(), i)));
            DecimalFormat df = new DecimalFormat("0.####");
            String s = df.format(i);
            if(Math.abs(i) < 0.000001) s = "0";
            int opacity = s.length() * 7;
            double x = sc.r2s(new RealPoint(sc.getX() + sc.getW(), i)).getX() - opacity;
            double y = sc.r2s(new RealPoint(sc.getX(), i)).getY() + 4;
            g.drawString(s, (int)x, (int)y);
        }

    }
    private void drawAxes(LineDrawer ld) {
        ld.setColor(new Color(0, 0, 0));
        RealPoint xp1 = new RealPoint(sc.getX(), 0);
        RealPoint xp2 = new RealPoint(sc.getX() + sc.getW(), 0);
        RealPoint yp1 = new RealPoint(0, sc.getY());
        RealPoint yp2 = new RealPoint(0, sc.getY() - sc.getH());
        Line xAxis = new Line(xp1, xp2);
        Line yAxis = new Line(yp1, yp2);
        drawLine(ld, xAxis);
        drawLine(ld, yAxis);
    }
    private void drawBounds(LineDrawer ld) {
        ld.setColor(Color.blue);
        RealPoint first = new RealPoint(sc.getX(), sc.getY());
        RealPoint second = new RealPoint(sc.getX() + sc.getW()*0.9999, sc.getY());
        RealPoint third = new RealPoint(sc.getX() + sc.getW()*0.9999, sc.getY() - sc.getH()*0.9999);
        RealPoint fourth = new RealPoint(sc.getX(), sc.getY() - sc.getH()*0.9999);
        drawLine(ld, new Line(first, second));
        drawLine(ld, new Line(second, third));
        drawLine(ld, new Line(third, fourth));
        drawLine(ld, new Line(fourth, first));
    }
    private void drawPosition(Graphics g) throws Exception {
        g.setColor(new Color(255, 255, 255, 128));
        DecimalFormat df = new DecimalFormat("0.0000");
        String mousePosition = (df.format(mouseCoordinates.getX()) + "; " + df.format(mouseCoordinates.getY())).replaceAll(",", ".");
        int width = (mousePosition.length() - 1) * 7;
        ScreenPoint rectScreenPosition = sc.r2s(new RealPoint(sc.getX(),sc.getY()));
        g.fillRect(rectScreenPosition.getX(), rectScreenPosition.getY(), Math.max(width, 130), 30);
        g.setColor(Color.blue);
        g.drawRect(rectScreenPosition.getX(), rectScreenPosition.getY(), Math.max(width, 130), 30);
        g.setColor(Color.black);
        String scaleValue = "x" + scale;
        g.drawString(mousePosition, rectScreenPosition.getX() + 5, rectScreenPosition.getY() + 12);
        g.drawString(scaleValue, rectScreenPosition.getX() + 5, rectScreenPosition.getY() + 27);
        repaint();
    }
    private void drawFunctionValue(Graphics g) throws Exception {
        double x = mouseCoordinates.getX();
        double y = function.getYValue(mouseCoordinates.getX());
        DecimalFormat df = new DecimalFormat("0.0000");
        String resultCoordinates = (df.format(x) + "; " + df.format(y)).replaceAll("," , ".");
        int width = (resultCoordinates.length() - 2) * 7;
        ScreenPoint circlePos = sc.r2s(new RealPoint(x, y));

        g.setColor(new Color(88, 209, 67));
        g.fillOval(circlePos.getX() - 4, circlePos.getY() - 4, 8, 8);
        if(function.getYValue(mouseCoordinates.getX() + 0.0001) <= y) {
            g.setColor(Color.white);
            g.fillRect(circlePos.getX() + 10, circlePos.getY() - 23, width, 15);
            g.setColor(Color.blue);
            g.drawRect(circlePos.getX() + 10, circlePos.getY() - 23, width, 15);
            g.setColor(Color.black);
            g.drawString(resultCoordinates, circlePos.getX() + 12, circlePos.getY() - 11);
        } else {
            g.setColor(Color.white);
            g.fillRect(circlePos.getX() - width - 10, circlePos.getY() - 23, width, 15);
            g.setColor(Color.blue);
            g.drawRect(circlePos.getX() - width - 10, circlePos.getY() - 23, width, 15);
            g.setColor(Color.black);
            g.drawString(resultCoordinates, circlePos.getX() - width - 8, circlePos.getY() - 11);
        }



        repaint();
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        //repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();

        double scale = 1;
        double coef = clicks > 0 ? 0.5 : 2;

        if(this.scale - 0.001 < 0.0001 && coef < 1)
            return;
        if(this.scale >= 1000 && coef > 1)
            return;

        switch(Math.abs(scaleRotation) % 3) {
            case 0:
            case 1:
                coef = clicks > 0 ? 0.5 : 2;
                break;
            case 2:
                coef = clicks > 0 ? 0.4 : 2.5;
                scaleValues.push(coef);
                break;
        }

        if(scaleValues.peek() > 1 && coef > 1)
            scaleValues.push(coef);
        else if(scaleValues.peek() > 1 && coef < 1)
            scaleValues.pop();
        else if(scaleValues.peek() < 1 && coef < 1)
            scaleValues.push(coef);
        else scaleValues.pop();
        scaleRotation += clicks > 0 ? -1 : 1;

        scale *= coef;
        this.scale *= scale;

        sc.setW(sc.getW() * scale);
        sc.setH(sc.getH() * scale);
        sc.setX(sc.getX() * scale);
        sc.setY(sc.getY() * scale);
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ScreenPoint current = new ScreenPoint(e.getX(), e.getY());
        if (prevDrag != null) {
            ScreenPoint delta = new ScreenPoint(current.getX() - prevDrag.getX(), current.getY() - prevDrag.getY());
            RealPoint deltaReal = sc.s2r(delta);
            RealPoint zeroReal = sc.s2r(new ScreenPoint(0, 0));
            RealPoint vector = new RealPoint(deltaReal.getX() - zeroReal.getX(), deltaReal.getY() - zeroReal.getY());
            sc.setX(sc.getX() - vector.getX());
            sc.setY(sc.getY() - vector.getY());
            prevDrag = current;
        }
        if (currentLine != null) {
            currentLine.setP2(sc.s2r(current));
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseCoordinates = sc.s2r(new ScreenPoint(e.getX(), e.getY()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3)
            prevDrag = new ScreenPoint(e.getX(), e.getY());
        else if (e.getButton() == MouseEvent.BUTTON1) {
            currentLine = new Line(sc.s2r(new ScreenPoint(e.getX(), e.getY())), sc.s2r(new ScreenPoint(e.getX(), e.getY())));
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3)
            prevDrag = null;
        else if(e.getButton() == MouseEvent.BUTTON1) {
            lines.add(currentLine);
            currentLine = null;
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
/*
    private void drawGrid(LineDrawer ld) {
        ld.setColor(new Color(131, 208, 255, 255));
        Double gridStep = scale;
        double step = sc.getW() / 24; // ((int)scale % 5);
        /*
         * найти правильный x start
         * находится около левой границы
         * должен при этом быть кратен 10(5, 2)
         * в диапазоне от левой границы - шаг до левой границы + шаг будет местом первой засечки
         * * /
        for(double i = (int)sc.getX() - 1; i < (int)(sc.getX() + sc.getW()) + 1; i += step) {
            drawLine(ld, new Line(new RealPoint(i, sc.getY()), new RealPoint(i, sc.getY() - sc.getH())));
        }
        for(double i = (int)sc.getY() + 1; i > (int)(sc.getY() - sc.getH()) - 1; i -= step) {
            drawLine(ld, new Line(new RealPoint(sc.getX(), i), new RealPoint(sc.getX() + sc.getW(), i)));
        }
    }
*/