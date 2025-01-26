package brushes;

import managers.StrokeProperty;

import java.awt.*;
import java.util.List;

public class FreeHandBrush extends AbstractBrush {
    private final String name = "FreeHand";

    public FreeHandBrush() {
        super("FreeHand");
    }

    @Override
    public void updateStroke(Point currentPoint) {
        super.currentStroke.addPoint(currentPoint);
    }

    @Override
    public void drawStroke(Graphics2D g2d, List<Point> points, StrokeProperty strokeProperty) {
        g2d.setColor(strokeProperty.getStrokeColor());
        g2d.setStroke(new BasicStroke(strokeProperty.getLineThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    @Override
    public String getName() {
        return name;
    }
}
