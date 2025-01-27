package brushes;

import managers.StrokeData;
import managers.StrokeProperty;

import java.awt.*;
import java.util.List;

public class StraightLineBrush extends AbstractBrush {
    private final String name = "Straight Line";


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

    @Override
    public String getName() {
        return name;
    }
}
