package menuItems;

import javax.swing.*;
import java.awt.*;

public class MoveToolMenuMenuItem extends AbstractMenuItem {

    @Override
    public JPanel createMenuPanel() {
        JPanel panel = createBasePanel();

        JButton moveToolButton = new JButton("Move Tool: off");
        moveToolButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(moveToolButton);

        moveToolButton.addActionListener(e -> {
            if (strokeManager.getMoveToolActive()) {
                strokeManager.setMoveToolActive(false);
                moveToolButton.setText("Move Tool: off");
            } else {
                strokeManager.setMoveToolActive(true);
                moveToolButton.setText("Move Tool: on");
            }
        });

        return panel;
    }
}
