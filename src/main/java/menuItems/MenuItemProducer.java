package menuItems;

import javax.swing.*;

public interface MenuItemProducer {
    /**
     * Method that produces a JPanel representing the menu item.
     * @return The JPanel representing this menu item.
     */
    JPanel createMenuPanel();
}
