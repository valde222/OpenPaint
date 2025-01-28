package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import strokes.StrokeManager;

public class PaintScreen extends JFrame {

    private final JPanel drawingPanel;
    StrokeManager strokeManager = StrokeManager.getInstance();

    public PaintScreen() {
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                strokeManager.drawStrokes(g);
            }
        };

        addDrawingListeners();
    }


    private void addDrawingListeners() {
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (strokeManager.getMoveToolActive()) {
                    strokeManager.moveStrokeStart(e.getPoint());
                    drawingPanel.repaint();
                    return;
                }
                strokeManager.startStroke(e.getPoint());
                strokeManager.updateStroke(e.getPoint());
                drawingPanel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (strokeManager.getMoveToolActive()) {
                    drawingPanel.repaint();
                    return;
                }
                strokeManager.endStroke();
                drawingPanel.repaint();
            }
        });

        drawingPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (strokeManager.getMoveToolActive()) {
                    strokeManager.moveStroke(e.getPoint());
                    drawingPanel.repaint();
                    return;
                }
                strokeManager.updateStroke(e.getPoint());
                drawingPanel.repaint();

            }
            @Override
            public void mouseMoved(MouseEvent e) {
                if (strokeManager.getMoveToolActive() && strokeManager.isPointOnStroke(e.getPoint())) {
                    drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    drawingPanel.setCursor(Cursor.getDefaultCursor());
                }
            }
        });


        drawingPanel.setFocusable(true);
    }

    public JPanel getDrawingPanel() {
        return drawingPanel;
    }
}