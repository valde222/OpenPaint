package menuItems;

import brushes.BrushType;

import javax.swing.*;
import java.awt.*;

public class BrushSelectorMenuItem extends AbstractMenuItem {
    @Override
    public JPanel createMenuPanel() {
        JPanel panel = createBasePanel();

        JLabel brushLabel = new JLabel("Brush:");
        brushLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        String[] brushes = new String[BrushType.values().length];
        for (int i = 0; i < BrushType.values().length; i++) {
            brushes[i] = BrushType.values()[i].getName();
        }
        JComboBox<String> brushSelector = new JComboBox<>(brushes);
        brushSelector.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        brushSelector.setPreferredSize(new Dimension(120, 30));
        brushSelector.setMaximumSize(new Dimension(120, 30));
        panel.setMaximumSize(new Dimension(150, 60));

        brushSelector.setSelectedItem("Freehand");

        brushSelector.addActionListener(e -> {
            String selectedBrush = (String) brushSelector.getSelectedItem();
            for (BrushType brushType : BrushType.values()) {
                if (brushType.getName().equals(selectedBrush)) {
                    strokeManager.setBrushType(brushType);
                    break;
                }
            }
        });

        panel.add(brushLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(brushSelector);

        return panel;
    }
}
