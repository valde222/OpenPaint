package screens;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;

public class paintScreen extends JFrame{
    
    private final ArrayList<ArrayList<Point>> strokes = new ArrayList<>();
    private ArrayList<Point> currentStroke = null;
    private final HashSet<Integer> keyStates = new HashSet<>();
    
    private static final int SCREEN_WIDTH = 1600;
    private static final int SCREEN_HEIGHT = 1000;
    
    public paintScreen() {
        super("OpenPaint");

        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                for (ArrayList<Point> stroke : strokes) {
                    for (int i = 1; i < stroke.size(); i++) {
                        Point p1 = stroke.get(i - 1);
                        Point p2 = stroke.get(i);
                        g.drawLine(p1.x, p1.y, p2.x, p2.y);
                    }
                }
            }
        };
        setContentPane(panel);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentStroke = new ArrayList<>(); // Start a new stroke
                currentStroke.add(e.getPoint());
                strokes.add(currentStroke);
                panel.repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                currentStroke = null; // Add a break point
                panel.repaint();
            }

        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentStroke != null) {
                    currentStroke.add(e.getPoint()); // Add points to the current stroke
                }
                panel.repaint();
            }
        });

        panel.setFocusable(true);
        panel.requestFocusInWindow();

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyDown(e);
                checkShortcuts();
            }
            @Override
            public void keyReleased(KeyEvent e) {
                keyUp(e);
            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                panel.repaint();
            }
        }, 0, 16);

        setVisible(true);
    }

    private void keyDown(KeyEvent e) {
        keyStates.add(e.getKeyCode());
    }

    private void keyUp(KeyEvent e) {
        keyStates.remove(e.getKeyCode());
    }

    private void checkShortcuts() {
        if (keyStates.contains(KeyEvent.VK_CONTROL) && keyStates.contains(KeyEvent.VK_Z) && !strokes.isEmpty()) {
            strokes.remove(strokes.getLast());
        }
    }


}
