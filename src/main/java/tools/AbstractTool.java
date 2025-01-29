package tools;

import strokes.StrokeData;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;

public abstract class AbstractTool implements Tool{
    private final String name;

    public AbstractTool(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isPointOnStroke(Point point, List<StrokeData> strokes) {
        for (StrokeData stroke : strokes) {
            if (isPointOnSpecificStroke(point, stroke)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isPointOnSpecificStroke(Point point, StrokeData stroke) {
        int threshold = stroke.getStrokeProperty().getLineThickness() + 10;
        List<Point> points = stroke.getPoints();
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            if (isPointNearLine(point, p1, p2, threshold)) {
                return true;
            }
        }
        return false;
    }

    boolean isPointNearLine(Point p, Point p1, Point p2, int threshold) {
        double distance = Line2D.ptSegDist(p1.x, p1.y, p2.x, p2.y, p.x, p.y);
        return distance <= threshold;
    }
}
