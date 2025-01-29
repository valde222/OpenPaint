package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import strokes.StrokeData;
import strokes.StrokeManager;
import tools.EditingTool;
import tools.Tool;
import tools.ToolType;

public class PaintScreen extends JFrame {

    private final JPanel drawingPanel;
    StrokeManager strokeManager = StrokeManager.getInstance();

    public PaintScreen() {
        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                strokeManager.drawStrokes(g);
                highlightPoints(g);
            }
        };

        addDrawingListeners();
    }

    private void highlightPoints(Graphics g) {
        Tool currentTool = strokeManager.getToolType().getTool();
        if (currentTool instanceof EditingTool && ((EditingTool) currentTool).getSelectedStroke() != null) {
            StrokeData stroke = ((EditingTool) currentTool).getSelectedStroke();
            g.setColor(Color.RED);
            for (Point point : stroke.getPoints()) {
                g.fillOval(point.x - 3, point.y - 3, 6, 6);
            }
        }
    }


    private void addDrawingListeners() {
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                strokeManager.onMouseClick(e.getPoint());
                drawingPanel.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                strokeManager.onMouseRelease(e.getPoint());
                drawingPanel.repaint();
            }
        });

        drawingPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                strokeManager.onMouseDrag(e.getPoint());
                drawingPanel.repaint();

            }
            @Override
            public void mouseMoved(MouseEvent e) {
                if (strokeManager.getToolType() == ToolType.MOVE_TOOL && strokeManager.isPointOnStroke(e.getPoint())) {
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