package screens;

import managers.KeystrokeManager;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JFrame{
    private static final int SCREEN_WIDTH = 1600;
    private static final int SCREEN_HEIGHT = 1000;

    KeystrokeManager keystrokeManager = KeystrokeManager.getInstance();

    public MainPanel() {
        super("OpenPaint");

        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel menuPanel = new PaintMenu().getMenuPanel();
        JPanel drawingPanel = new PaintScreen().getDrawingPanel();

        mainPanel.add(menuPanel, BorderLayout.NORTH);
        mainPanel.add(drawingPanel, BorderLayout.CENTER);

        keystrokeManager.setupShortcuts(drawingPanel);

        setContentPane(mainPanel);
        setVisible(true);
        drawingPanel.requestFocusInWindow();
    }

}
