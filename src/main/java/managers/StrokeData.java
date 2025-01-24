package managers;

import java.awt.*;
import java.util.List;

public class StrokeData {

    private final List<Point> points;
    private final StrokeProperty strokeProperty;

    public StrokeData(List<Point> points, StrokeProperty strokeProperty) {
        this.points = points;
        this.strokeProperty = strokeProperty;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public StrokeProperty getStrokeProperty() {
        return strokeProperty;
    }
}
