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
    private ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 1920, 1080);
    private Line yAxis = new Line(0, -1, 0, 1);
    private Line xAxis = new Line(-1, 0, 1, 0);
    private ScreenPoint prevDrag;
    private Line currentLine;
    private ArrayList<Function> functions = new ArrayList<>();
    private double scale;

    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        sc.setScreenW(getWidth());
        sc.setScreenH(getHeight());
        Graphics bi_g = bi.getGraphics();
        bi_g.setColor(new Color(220, 220, 220));
        bi_g.fillRect(0, 0, getWidth(), getHeight());
        bi_g.dispose();
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new BresenhamLineDrawer(pd);

        drawAxes(ld);
        for(Line l : lines) {
            drawLine(ld, l);
        }
        if(currentLine != null)
            drawLine(ld, currentLine);
        g.drawImage(bi, 0, 0 , null);
    }

    private void drawLine(LineDrawer ld, Line l) {
        ld.drawLine(sc.r2s(l.getP1()), sc.r2s(l.getP2()));
    }

    private void drawGrid(LineDrawer ld) {
        ld.setColor(new Color(227, 227, 255, 255 * 7 / 10));
        double step = sc.getW() / 12;
        for(double i = sc.getX(); i < sc.getX() + sc.getH(); i += step) {

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
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks > 0 ? 0.5 : 2;
        for(int i = 0; i < Math.abs(clicks); i++)
            scale *= coef;

        this.scale *= scale;
        sc.setW(sc.getW() * scale);
        sc.setH(sc.getH() * scale);
        ScreenPoint mouse = new ScreenPoint(e.getX(), e.getY());
        sc.setX((sc.getX() + sc.s2r(mouse).getX()) * scale);
        sc.setY((sc.getY() + sc.s2r(mouse).getY()) * scale);
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
