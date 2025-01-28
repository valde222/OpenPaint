package tools;

import strokes.StrokeData;
import strokes.StrokeManager;
import strokes.StrokeProperty;

import java.awt.*;
import java.util.ArrayList;

public class DrawingTool extends AbstractTool {

    StrokeData currentStroke;


    public DrawingTool() {
        super("Drawing Tool");
    }

    @Override
    public void onMousePress(Point point) {
        StrokeManager strokeManager = StrokeManager.getInstance();
        currentStroke = new StrokeData(new ArrayList<>(), new StrokeProperty(
                strokeManager.getDefaultStrokeProperty().getLineThickness(),
                strokeManager.getDefaultStrokeProperty().getStrokeColor()
        ));
        currentStroke.addPoint(point);
        strokeManager.setCurrentStroke(currentStroke);
    }

    @Override
    public void onMouseDrag(Point point) {
        StrokeManager strokeManager = StrokeManager.getInstance();
        strokeManager.setCurrentStroke(currentStroke);
        if (currentStroke != null) {
            this.currentStroke = strokeManager.getBrushType().getBrush().updateStroke(point, currentStroke);
            strokeManager.setCurrentStroke(currentStroke);
        }
    }

    @Override
    public void onMouseRelease(Point point) {
        StrokeManager strokeManager = StrokeManager.getInstance();
        if (currentStroke != null) {
            strokeManager.addStroke(currentStroke);
            currentStroke = null;
            strokeManager.setCurrentStroke(null);
        }
    }
}
