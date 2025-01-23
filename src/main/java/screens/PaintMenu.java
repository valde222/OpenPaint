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

        JLabel lineThicknessLabel = new JLabel("Line Thickness: 3");
        lineThicknessLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JSlider lineThicknessChooser = getLineThicknessSlider(lineThicknessLabel);
        JPanel lineThicknessPanel = new JPanel();
        lineThicknessPanel.setLayout(new BoxLayout(lineThicknessPanel, BoxLayout.Y_AXIS));
        lineThicknessPanel.add(lineThicknessLabel);
        lineThicknessPanel.add(Box.createVerticalStrut(5));
        lineThicknessPanel.add(lineThicknessChooser);

        return lineThicknessPanel;
    }

    private JSlider getLineThicknessSlider(JLabel lineThicknessLabel) {

        int LINE_THICKNESS_MIN = 1;
        int LINE_THICKNESS_MAX = 50;
        int LINE_THICKNESS_INIT = 3;

        final JSlider lineThicknessChooser = new JSlider(SwingConstants.HORIZONTAL, LINE_THICKNESS_MIN, LINE_THICKNESS_MAX, LINE_THICKNESS_INIT);

        lineThicknessChooser.setAlignmentX(Component.CENTER_ALIGNMENT);
        lineThicknessChooser.setMinorTickSpacing(1);
        lineThicknessChooser.setMajorTickSpacing(10);
        lineThicknessChooser.setPaintTicks(true);
        lineThicknessChooser.setMinimumSize(lineThicknessChooser.getPreferredSize());

        lineThicknessChooser.addChangeListener(e -> {
            int selectedThickness = lineThicknessChooser.getValue();
            strokeManager.setLineThickness(selectedThickness);
            lineThicknessLabel.setText("Line Thickness: " + selectedThickness);
        });
        return lineThicknessChooser;
    }

    public JPanel getMenuPanel() {
        return parentPanel;
    }

}
