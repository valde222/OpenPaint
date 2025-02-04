package managers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class KeystrokeManager {

    private static KeystrokeManager instance;
    StrokeManager strokeManager = StrokeManager.getInstance();

    private KeystrokeManager() {}

    public static KeystrokeManager getInstance() {
        if (instance == null) {
            instance = new KeystrokeManager();
        }
        return instance;
    }

    public void setupUndoShortcut(JPanel drawingPanel) {
        InputMap inputMap = drawingPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = drawingPanel.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "undo");
        actionMap.put("undo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                strokeManager.undoLastStroke();
                drawingPanel.repaint();
            }
        });
    }
}
