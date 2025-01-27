package brushes;

import managers.StrokeData;

import java.awt.*;

public class FreeHandBrush extends AbstractBrush {
    public FreeHandBrush() {
        super("FreeHand");
    }

    @Override
    public StrokeData updateStroke(Point currentPoint, StrokeData currentStroke) {
        currentStroke.addPoint(currentPoint);
        return currentStroke;
    }
}
