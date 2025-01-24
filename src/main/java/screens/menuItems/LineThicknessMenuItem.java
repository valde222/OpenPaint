package screens.menuItems;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class LineThicknessMenuItem extends AbstractMenuItem {

    @Override
    public JPanel createMenuPanel() {

        JLabel lineThicknessLabel = new JLabel("Line Thickness: 3");
        lineThicknessLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JSlider lineThicknessChooser = getLineThicknessSlider(lineThicknessLabel);
        JPanel lineThicknessPanel = createBasePanel();

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

        lineThicknessChooser.setUI(getSliderThumb());

        lineThicknessChooser.addChangeListener(e -> {
            int selectedThickness = lineThicknessChooser.getValue();
            strokeManager.setLineThickness(selectedThickness);
            lineThicknessLabel.setText("Line Thickness: " + selectedThickness);
        });
        return lineThicknessChooser;
    }

    private static BasicSliderUI getSliderThumb() {
        return new BasicSliderUI() {
            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();

                int[] xPoints = {
                        thumbRect.x + thumbRect.width / 2,  // Top/middle point (center horizontally)
                        thumbRect.x,                        // Bottom-left corner
                        thumbRect.x + thumbRect.width       // Bottom-right corner
                };

                int[] yPoints = {
                        thumbRect.y + thumbRect.height,     // Bottom/middle (point down)
                        thumbRect.y,                        // Top-left
                        thumbRect.y                         // Top-right
                };

                Polygon triangle = new Polygon(xPoints, yPoints, 3);
                g2.setClip(triangle);
                g2.setColor(new Color(78, 78, 78));
                g2.fillPolygon(triangle);

                g2.drawPolygon(triangle);
                g2.dispose();
            }
        };
    }
}
