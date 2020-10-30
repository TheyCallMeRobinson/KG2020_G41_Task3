package com.company;

import com.company.drawers.DDALineDrawer;
import com.company.drawers.Line;
import com.company.drawers.LineDrawer;
import com.company.functions.Function_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
    private ArrayList<Line> lines = new ArrayList<>();
    private ScreenConverter sc = new ScreenConverter(-2, 2, 4, 4, 800, 600);
    private Line yAxis = new Line(0, -1, 0, 1);
    private Line xAxis = new Line(-1, 0, 1, 0);
    private ScreenPoint prevDrag;
    private Line currentLine;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int clicks = e.getWheelRotation();
        double scale = 1;
        double coef = clicks > 0 ? 0.9 : 1.1;
        for(int i = 0; i < Math.abs(clicks); i++) {
            scale *= coef;
        }
        sc.setW(sc.getW() * scale);
        sc.setH(sc.getH() * scale);
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

    public DrawPanel() {
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(
                getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR
        );
        sc.setScreenW(getWidth());
        sc.setScreenH(getHeight());
        Graphics bi_g = bi.getGraphics();
        bi_g.setColor(Color.WHITE);
        bi_g.fillRect(0, 0, getWidth(), getHeight());
        bi_g.dispose();
        PixelDrawer pd = new BufferedImagePixelDrawer(bi);
        LineDrawer ld = new DDALineDrawer(pd);

        drawLine(ld, xAxis);
        drawLine(ld, yAxis);
        Function_1 f1 = new Function_1();
        ArrayList<RealPoint> points = f1.execute(-2, 2);
        RealPoint r1 = new RealPoint(-2, 1);
        RealPoint r2 = new RealPoint(-1.999, 1);
        drawLine(ld, new Line(r1, r2));
        for(RealPoint i : points) {
            ScreenPoint real1 = sc.r2s(i);
            pd.setPixel(real1.getX(), real1.getY(), new Color(0,0,0));
//            RealPoint j = new RealPoint(i.getX(),i.getY() + 0.001);
////            ScreenPoint real2 = sc.r2s(i);
//            drawLine(ld, new Line(i, j));
////            pd.setPixel(r.getX(), r.getY(), new Color(0,0,0));
        }
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
}
