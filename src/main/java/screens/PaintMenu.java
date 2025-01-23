package screens;

import managers.StrokeManager;

import javax.swing.*;
import java.awt.*;

public class PaintMenu {

    private static final int SCREEN_WIDTH = 1600;
    private static final int MENU_HEIGHT = 100;

    StrokeManager strokeManager = StrokeManager.getInstance();

    JPanel menuPanel;
    JPanel parentPanel;

    public PaintMenu() {
        menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        menuPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, MENU_HEIGHT));

        menuPanel.add(createLineThicknessPanel());
        parentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = getGridBagConstraints();
        parentPanel.setBackground(Color.LIGHT_GRAY);
        
        parentPanel.add(menuPanel, gridBagConstraints);
    }

    private GridBagConstraints getGridBagConstraints() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 20, 0, 0);
        return gridBagConstraints;
    }

    private JPanel createLineThicknessPanel() {
        final JComboBox<Integer> lineThicknessChooser = getLineThicknessDropdown();
        JLabel lineThicknessLabel = new JLabel("Line Thickness:");
        lineThicknessLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel lineThicknessPanel = new JPanel();
        lineThicknessPanel.setLayout(new BoxLayout(lineThicknessPanel, BoxLayout.Y_AXIS));
        lineThicknessPanel.add(lineThicknessLabel);
        lineThicknessPanel.add(Box.createVerticalStrut(5));
        lineThicknessPanel.add(lineThicknessChooser);

        return lineThicknessPanel;
    }

    private JComboBox<Integer> getLineThicknessDropdown() {
        Integer[] choices = { 1, 2, 3, 4, 6, 8, 10, 12, 16, 20, 24, 28, 32 };

        final JComboBox<Integer> lineThicknessChooser = new JComboBox<>(choices);

        Dimension preferredSize = lineThicknessChooser.getPreferredSize();
        lineThicknessChooser.setMaximumSize(preferredSize);
        lineThicknessChooser.setPreferredSize(preferredSize);
        lineThicknessChooser.setAlignmentX(Component.CENTER_ALIGNMENT);

        lineThicknessChooser.addActionListener(e -> {
            Integer selectedThickness = (Integer) lineThicknessChooser.getSelectedItem();
            if (selectedThickness != null) {
                strokeManager.setLineThickness(selectedThickness);
            }
        });
        return lineThicknessChooser;
    }

    public JPanel getMenuPanel() {
        return parentPanel;
    }

}
