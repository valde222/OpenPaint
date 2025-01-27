package brushes;

import managers.StrokeData;

import java.awt.*;

public class StraightLineBrush extends AbstractBrush {

    public StraightLineBrush() {
        super("Straight Line");
    }

    @Override
    public StrokeData updateStroke(Point currentPoint, StrokeData currentStroke) {
        if (currentStroke.getPoints().size() == 1) {
            currentStroke.addPoint(currentPoint);
        } else {
            currentStroke.getPoints().set(1, currentPoint);
        }
        return currentStroke;
    }
}
