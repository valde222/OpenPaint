package brushes;

import managers.StrokeData;
import managers.StrokeProperty;

import java.awt.*;
import java.util.List;

public interface Brush {
    String getName();
    void startStroke(Point initialPoint, StrokeProperty strokeProperty);
    void updateStroke(Point currentPoint);
    void endStroke(Point endPoint);
    void drawStroke(Graphics2D g2d, List<Point> points, StrokeProperty strokeProperty);
}
