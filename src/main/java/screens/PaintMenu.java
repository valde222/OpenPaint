package screens;

import menuItems.MenuItemProducer;
import menuItems.ColorPickerMenuItem;
import menuItems.LineThicknessMenuItem;
import menuItems.BrushSelectorMenuItem;
import menuItems.MoveToolMenuMenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PaintMenu {

    private static final int MENU_ITEM_GAP = 10;
    private static final int MENU_HEIGHT = 100;

    JPanel menuPanel;
    JPanel parentPanel;

    public PaintMenu() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS));
        menuPanel.setPreferredSize(new Dimension(0, MENU_HEIGHT));
        menuPanel.setMinimumSize(new Dimension(0, MENU_HEIGHT));
        menuPanel.setBackground(Color.LIGHT_GRAY);

        List<MenuItemProducer> menuItems = List.of(
                new LineThicknessMenuItem(),
                new ColorPickerMenuItem(),
                new BrushSelectorMenuItem(),
                new MoveToolMenuMenuItem()
        );

        for (MenuItemProducer menuItem : menuItems) {
            menuPanel.add(Box.createHorizontalStrut(MENU_ITEM_GAP));
            menuPanel.add(menuItem.createMenuPanel());
        }

        parentPanel = new JPanel(new BorderLayout());
        parentPanel.add(menuPanel, BorderLayout.NORTH);
    }

    public JPanel getMenuPanel() {
        return parentPanel;
    }

}
