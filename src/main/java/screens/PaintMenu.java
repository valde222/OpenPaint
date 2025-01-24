package screens;

import Interfaces.MenuItemProducer;
import screens.menuItems.LineThicknessMenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PaintMenu {

    private static final int SCREEN_WIDTH = 1600;
    private static final int MENU_HEIGHT = 100;

    JPanel menuPanel;
    JPanel parentPanel;

    public PaintMenu() {
        menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        menuPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, MENU_HEIGHT));

        List<MenuItemProducer> menuItems = List.of(
                new LineThicknessMenuItem()
        );

        for (MenuItemProducer menuItem : menuItems) {
            menuPanel.add(menuItem.createMenuPanel());
        }

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

    public JPanel getMenuPanel() {
        return parentPanel;
    }

}
