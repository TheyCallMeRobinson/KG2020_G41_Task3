package com.company;

import com.company.drawer.linedrawer.BresenhamLineDrawer;
import com.company.drawer.linedrawer.Line;
import com.company.drawer.pixeldrawer.BufferedImagePixelDrawer;
import com.company.drawer.linedrawer.LineDrawer;
import com.company.drawer.pixeldrawer.PixelDrawer;
import com.company.function.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, ChangeListener {
    private ArrayList<Line> lines = new ArrayList<>();
    private ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 800, 800);
    private ScreenPoint prevDrag;
    private Line currentLine;
    private Function function;
    private ArrayList<Function> functions = new ArrayList<>();
    private double scale = 1;

    public double getScale() {
        return scale;
    }

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
    }

    public void setFunction(int number) {
        function = functions.get(number);
        repaint();
    }

    public String getFunctionForm() {return function.getForm();}

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        sc.setScreenW(getWidth());
        sc.setScreenH(getHeight());
        Graphics bi_g = bi.getGraphics();
        bi_g.setColor(new Color(238, 238, 238));
        bi_g.fillRect(0, 0, getWidth(), getHeight());
        bi_g.dispose();
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new BresenhamLineDrawer(pd);

        drawGrid(ld);
        drawAxes(ld);
        for(Line l : lines) {
            drawLine(ld, l);
        }
        if(currentLine != null)
            drawLine(ld, currentLine);
        g.drawImage(bi, 0, 0, null);
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }

    private void drawGrid(LineDrawer ld) {
        ld.setColor(new Color(131, 208, 255, 255));
        double step = sc.getW() / 12;
        for(double i = (int)sc.getX() - 1; i < (int)(sc.getX() + sc.getW()) + 1; i += step) {
            drawLine(ld, new Line(new RealPoint(i, sc.getY()), new RealPoint(i, sc.getY() - sc.getH())));
        }
        for(double i = (int)sc.getY() + 1; i > (int)(sc.getY() - sc.getH()) - 1; i -= step) {
            drawLine(ld, new Line(new RealPoint(sc.getX(), i), new RealPoint(sc.getX() + sc.getW(), i)));
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

    @Override
    public void stateChanged(ChangeEvent e) {
        //repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        if(Math.abs(this.scale - 0.0001) < 0.00001) {
            this.scale *= 2;
            return;
        }
        if(this.scale > 1000) {
            this.scale /= 2;
            return;
        }

        double scale = 1;
        double coef = clicks > 0 ? 0.5 : 2;
        for(int i = 0; i < Math.abs(clicks); i++)
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

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // prevDrag = new ScreenPoint(e.getX(), e.getY());
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
