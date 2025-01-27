package brushes;

import managers.StrokeData;
import managers.StrokeProperty;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FreeHandBrush extends AbstractBrush {
    private final String name = "FreeHand";

    public FreeHandBrush() {
        super("FreeHand");
    }

    @Override
    public StrokeData updateStroke(Point currentPoint, StrokeData currentStroke) {
        currentStroke.addPoint(currentPoint);
        return currentStroke;
    }

    @Override
    public String getName() {
        return name;
    }
}
