package tools;

import strokes.StrokeData;
import strokes.StrokeManager;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class MoveTool extends AbstractTool {

    public MoveTool() {
        super("Move Tool");
    }

    private StrokeData selectedStroke;
    private Point initialPoint;
    private List<Point> initialPoints;

    @Override
    public void onMousePress(Point point) {
        StrokeManager strokeManager = StrokeManager.getInstance();
        for (StrokeData stroke : strokeManager.getStrokes()) {
            if (isPointOnSpecificStroke(point, stroke)) {
                selectedStroke = stroke;
                initialPoint = point;
                initialPoints = new ArrayList<>();
                strokeManager.setSelectedStroke(stroke);
                for (Point p : stroke.getPoints()) {
                    initialPoints.add(new Point(p));
                }
                return;
            } else {
                selectedStroke = null;
                initialPoint = null;
                initialPoints = null;
                strokeManager.setSelectedStroke(null);
            }
        }
    }

    @Override
    public void onMouseDrag(Point point) {
        if (selectedStroke != null && initialPoint != null) {
            int pointDifferenceX = point.x - initialPoint.x;
            int pointDifferenceY = point.y - initialPoint.y;
            List<Point> points = selectedStroke.getPoints();
            for (int i = 0; i < points.size(); i++) {
                Point initialP = initialPoints.get(i);
                points.get(i).setLocation(initialP.x + pointDifferenceX, initialP.y + pointDifferenceY);
            }
        }
    }

    @Override
    public void onMouseRelease(Point point) {
        StrokeManager strokeManager = StrokeManager.getInstance();
        if (selectedStroke != null || initialPoint == null) {
            return;
        }
        strokeManager.updateSpecificStroke(selectedStroke);
        selectedStroke = null;
        initialPoint = null;
        initialPoints = null;
    }

}
