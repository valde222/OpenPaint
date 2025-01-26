package brushes;

import managers.StrokeProperty;

import java.awt.*;
import java.util.List;

public class StraightLineBrush extends AbstractBrush {
    private final String name = "Straight Line";
    private Point startPoint;


    public StraightLineBrush() {
        super("Straight Line");
    }

    @Override
    public void updateStroke(Point currentPoint) {
        super.currentStroke.getPoints().set(1, currentPoint);
    }

    @Override
    public void drawStroke(Graphics2D g2d, List<Point> points, StrokeProperty strokeProperty) {
        g2d.setColor(strokeProperty.getStrokeColor());
        g2d.setStroke(new BasicStroke(strokeProperty.getLineThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        if (points.size() > 1) {
            Point p1 = points.get(0);
            Point p2 = points.get(1);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
