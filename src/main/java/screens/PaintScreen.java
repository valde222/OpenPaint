package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import managers.StrokeManager;

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

        addDrawingListeners(drawingPanel);
    }


    private void addDrawingListeners(JPanel drawingPanel) {
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                strokeManager.startStroke(e.getPoint());
                if ("FREEHAND".equals(strokeManager.getBrushType())) {
                    strokeManager.addPointToStroke(e.getPoint());
                }
                drawingPanel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                strokeManager.endStroke(e.getPoint());
                drawingPanel.repaint();
            }
        });

        drawingPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if ("FREEHAND".equals(strokeManager.getBrushType())) {
                    strokeManager.addPointToStroke(e.getPoint());
                } else if ("STRAIGHT_LINE".equals(strokeManager.getBrushType())) {
                    // Update the temporary endpoint for line preview
                    strokeManager.updateTemporaryEndPoint(e.getPoint());
                }
                drawingPanel.repaint();
            }
        });

        drawingPanel.setFocusable(true);
    }

    public JPanel getDrawingPanel() {
        return drawingPanel;
    }
}