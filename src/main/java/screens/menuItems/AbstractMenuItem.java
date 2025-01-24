package screens.menuItems;

import Interfaces.MenuItemProducer;
import managers.StrokeManager;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractMenuItem implements MenuItemProducer {

    StrokeManager strokeManager = StrokeManager.getInstance();

    /**
     * Creates a base panel with vertical layout.
     * Child classes can build upon this structure.
     */
    protected JPanel createBasePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }
}
