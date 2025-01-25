package screens.menuItems;

import javax.swing.*;
import java.awt.*;

public class BrushSelectorMenuItem extends AbstractMenuItem {
    @Override
    public JPanel createMenuPanel() {
        JPanel panel = createBasePanel();

        JLabel brushLabel = new JLabel("Brush:");
        brushLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        String[] brushes = { "Freehand", "Straight Line" };
        JComboBox<String> brushSelector = new JComboBox<>(brushes);
        brushSelector.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        brushSelector.setPreferredSize(new Dimension(120, 30));
        brushSelector.setMaximumSize(new Dimension(120, 30));
        panel.setMaximumSize(new Dimension(150, 60));

        brushSelector.setSelectedItem("Freehand");

        brushSelector.addActionListener(e -> {
            String selectedBrush = (String) brushSelector.getSelectedItem();
            if ("Freehand".equals(selectedBrush)) {
                strokeManager.setBrushType("FREEHAND");
            } else if ("Straight Line".equals(selectedBrush)) {
                strokeManager.setBrushType("STRAIGHT_LINE");
            }
        });

        panel.add(brushLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(brushSelector);

        return panel;
    }
}
