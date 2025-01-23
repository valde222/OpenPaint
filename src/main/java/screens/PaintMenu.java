package screens;

import managers.StrokeManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class PaintMenu {

    private static final int SCREEN_WIDTH = 1600;
    private static final int MAX_LINE_THICKNESS = 30;
    private static final int MIN_LINE_THICKNESS = 1;
    private final int lineThickness = 3;

    StrokeManager strokeManager = StrokeManager.getInstance();

    JPanel menuPanel;

    public PaintMenu() {
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, 100));

        JTextArea lineThicknessTextArea = createLineThicknessTextArea();
        JScrollPane scrollPane = new JScrollPane(lineThicknessTextArea);

        menuPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private JTextArea createLineThicknessTextArea() {
        JTextArea lineThicknessTextArea = new JTextArea();
        lineThicknessTextArea.setLineWrap(true);
        lineThicknessTextArea.setWrapStyleWord(true);
        lineThicknessTextArea.setFont(new Font("Arial", Font.PLAIN, 14));

        addLineThicknessDocumentListener(lineThicknessTextArea);

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
                strokeManager.setLineThickness(newThickness);
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

    public JPanel getMenuPanel() {
        return menuPanel;
    }

}
