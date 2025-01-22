package screens;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;

public class PaintScreen extends JFrame {

    private static final int SCREEN_WIDTH = 1600;
    private static final int SCREEN_HEIGHT = 1000;
    private static final int MAX_LINE_THICKNESS = 30;
    private static final int MIN_LINE_THICKNESS = 1;

    private int lineThickness = 3;
    private final StrokeManager strokeManager = new StrokeManager();
    private final HashSet<Integer> keyStates = new HashSet<>();

    public PaintScreen() {
        super("OpenPaint");

        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel menuPanel = initializeMenuPanel();
        JPanel drawingPanel = initializeDrawingArea();

        mainPanel.add(menuPanel, BorderLayout.NORTH);
        mainPanel.add(drawingPanel, BorderLayout.CENTER);

        setupUndoShortcut(drawingPanel);

        setContentPane(mainPanel);
        setVisible(true);
        drawingPanel.requestFocusInWindow();
    }

    private JPanel initializeMenuPanel() {
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, 100));

        JTextArea lineThicknessTextArea = createLineThicknessTextArea();
        JScrollPane scrollPane = new JScrollPane(lineThicknessTextArea);

        menuPanel.add(scrollPane, BorderLayout.CENTER);
        return menuPanel;
    }

    private JTextArea createLineThicknessTextArea() {
        JTextArea lineThicknessTextArea = new JTextArea();
        lineThicknessTextArea.setLineWrap(true);
        lineThicknessTextArea.setWrapStyleWord(true);
        lineThicknessTextArea.setFont(new Font("Arial", Font.PLAIN, 14));

        addLineThicknessDocumentListener(lineThicknessTextArea);

        lineThicknessTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                getContentPane().getComponent(1).requestFocusInWindow();
            }
        });

        return lineThicknessTextArea;
    }

    private void addLineThicknessDocumentListener(JTextArea textArea) {
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateLineThickness(textArea);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateLineThickness(textArea);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateLineThickness(textArea);
            }
        });
    }

    private void updateLineThickness(JTextArea textArea) {
        String text = textArea.getText().trim();
        if (text.isEmpty()) {
            return;
        }

        try {
            int newThickness = Integer.parseInt(text);
            if (newThickness >= MIN_LINE_THICKNESS && newThickness <= MAX_LINE_THICKNESS) {
                lineThickness = newThickness;
            } else {
                restorePreviousLineThickness(textArea);
            }
        } catch (NumberFormatException e) {
            restorePreviousLineThickness(textArea);
        }
    }

    private void restorePreviousLineThickness(JTextArea textArea) {
        SwingUtilities.invokeLater(() -> textArea.setText(String.valueOf(lineThickness)));
    }

    private JPanel initializeDrawingArea() {
        JPanel drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                strokeManager.drawStrokes(g);
            }
        };

        addDrawingListeners(drawingPanel);
        return drawingPanel;
    }

    private void addDrawingListeners(JPanel drawingPanel) {
        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                strokeManager.startStroke(e.getPoint(), lineThickness);
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
                onKeyDown(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                onKeyUp(e);
            }
        });
    }

    private void setupUndoShortcut(JPanel drawingPanel) {
        InputMap inputMap = drawingPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = drawingPanel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "undo");
        actionMap.put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                strokeManager.undoLastStroke();
                drawingPanel.repaint();
            }
        });
    }

    private void onKeyDown(KeyEvent e) {
        keyStates.add(e.getKeyCode());
    }

    private void onKeyUp(KeyEvent e) {
        keyStates.remove(e.getKeyCode());
    }

    /**
     * Manages stroke creation, rendering, and undo functionality.
     */
    private static class StrokeManager {
        private final ArrayList<ArrayList<Point>> strokes = new ArrayList<>();
        private ArrayList<Point> currentStroke;

        public void startStroke(Point initialPoint, int thickness) {
            currentStroke = new ArrayList<>();
            currentStroke.add(new Point(thickness, 0));
            currentStroke.add(initialPoint);
            strokes.add(currentStroke);
        }

        public void endStroke() {
            currentStroke = null;
        }

        public void addPointToStroke(Point point) {
            if (currentStroke != null) {
                currentStroke.add(point);
            }
        }

        public void undoLastStroke() {
            if (!strokes.isEmpty()) {
                strokes.removeLast();
            }
        }

        public void drawStrokes(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.BLACK);

            for (ArrayList<Point> stroke : strokes) {
                g2d.setStroke(new BasicStroke(
                        stroke.getFirst().x,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND
                ));
                for (int i = 2; i < stroke.size(); i++) {
                    Point p1 = stroke.get(i - 1);
                    Point p2 = stroke.get(i);
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }
    }
}