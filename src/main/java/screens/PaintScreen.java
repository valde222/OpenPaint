package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import managers.KeystrokeManager;
import managers.StrokeManager;

public class PaintScreen extends JFrame {

    private final JPanel drawingPanel;
    StrokeManager strokeManager = StrokeManager.getInstance();
    KeystrokeManager keystrokeManager = KeystrokeManager.getInstance();

    public PaintScreen() {
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                strokeManager.drawStrokes(g);
            }
        };

        addDrawingListeners(drawingPanel);
    }


    private void addDrawingListeners(JPanel drawingPanel) {
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                strokeManager.addPointToStroke(e.getPoint());
                strokeManager.addPointToStroke(e.getPoint());
                strokeManager.startStroke(e.getPoint());
                drawingPanel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                strokeManager.endStroke();
                drawingPanel.repaint();
            }
        });

        drawingPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                strokeManager.addPointToStroke(e.getPoint());
                drawingPanel.repaint();
            }
        });

        drawingPanel.setFocusable(true);
        drawingPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keystrokeManager.onKeyDown(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keystrokeManager.onKeyUp(e);
            }
        });
    }

    public JPanel getDrawingPanel() {
        return drawingPanel;
    }
}