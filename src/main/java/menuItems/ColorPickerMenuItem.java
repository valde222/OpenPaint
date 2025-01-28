package menuItems;

import javax.swing.*;
import java.awt.*;

public class ColorPickerMenuItem extends AbstractMenuItem {

    @Override
    public JPanel createMenuPanel() {

        JPanel panel = createBasePanel();

        JButton colorPickerButton = new JButton("Color Picker");
        colorPickerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(colorPickerButton);

        JColorChooser colorChooser = new JColorChooser(Color.BLACK);
        colorChooser.getSelectionModel().addChangeListener(e -> {
            Color selectedColor = colorChooser.getColor();
            strokeManager.setStrokeColor(selectedColor);
        });

        colorPickerButton.addActionListener(e -> {
            JDialog dialog = JColorChooser.createDialog(null, "Choose a Color", true, colorChooser, null, null);
            dialog.setVisible(true);
        });

        return panel;
    }

}
