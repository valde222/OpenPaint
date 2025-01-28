package menuItems;

import tools.ToolType;

import javax.swing.*;
import java.awt.*;

public class ToolMenuMenuItem extends AbstractMenuItem {

    @Override
    public JPanel createMenuPanel() {
        JPanel panel = createBasePanel();

        JLabel toolLabel = new JLabel("Tool:");
        toolLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        String[] tools = new String[ToolType.values().length];
        for (int i = 0; i < ToolType.values().length; i++) {
            tools[i] = ToolType.values()[i].getName();
        }
        JComboBox<String> toolSelector = new JComboBox<>(tools);
        toolSelector.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        toolSelector.setPreferredSize(new Dimension(120, 30));
        toolSelector.setMaximumSize(new Dimension(120, 30));
        panel.setMaximumSize(new Dimension(150, 60));

        toolSelector.setSelectedItem("Drawing Tool");

        toolSelector.addActionListener(e -> {
            String selectedTool = (String) toolSelector.getSelectedItem();
            for (ToolType toolType : ToolType.values()) {
                if (toolType.getName().equals(selectedTool)) {
                    strokeManager.setToolType(toolType);
                    break;
                }
            }
        });

        panel.add(toolLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(toolSelector);

        return panel;
    }
}
