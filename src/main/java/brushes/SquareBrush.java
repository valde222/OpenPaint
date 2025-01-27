package brushes;

import managers.StrokeData;

import java.awt.*;

public class SquareBrush extends AbstractBrush {
    private final String name = "Square";

    public SquareBrush() {
        super("Square");
    }

    @Override
    public StrokeData updateStroke(Point currentPoint, StrokeData currentStroke) {
        if (currentStroke.getPoints().isEmpty()) {
            currentStroke.addPoint(currentPoint);
            return currentStroke;
        } else {
            Point startPoint = currentStroke.getPoints().getFirst();
            currentStroke.getPoints().clear();
            currentStroke.addPoint(startPoint);
            currentStroke.addPoint(new Point(currentStroke.getPoints().getFirst().x, currentPoint.y));
            currentStroke.addPoint(currentPoint);
            currentStroke.addPoint(new Point(currentPoint.x, currentStroke.getPoints().getFirst().y));
            currentStroke.addPoint(startPoint);
        }
        return currentStroke;
    }

    @Override
    public String getName() {
        return name;
    }
}
