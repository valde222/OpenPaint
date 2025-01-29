package tools;

import strokes.StrokeData;
import strokes.StrokeManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EditingTool extends AbstractTool {


    public EditingTool() {
        super("Editing Tool");
    }

    private StrokeData selectedStroke;
    private ArrayList<Point> selectedPoints = new ArrayList<>();
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
                selectedPoints.clear();
                for (Point p : stroke.getPoints()) {
                    if (isPointNearLine(point, p, p, 5)) {
                        selectedPoints.add(p);
                        initialPoints.add(new Point(p));
                    }
                }
                strokeManager.setSelectedStroke(stroke);
                return;
            } else {
                selectedStroke = null;
                initialPoint = null;
                initialPoints = null;
                selectedPoints.clear();
                strokeManager.setSelectedStroke(null);
            }
        }
    }

    @Override
    public void onMouseDrag(Point point) {
        if (selectedStroke != null && initialPoint != null) {
            int pointDifferenceX = point.x - initialPoint.x;
            int pointDifferenceY = point.y - initialPoint.y;
            for (int i = 0; i < selectedPoints.size(); i++) {
                Point initialP = initialPoints.get(i);
                selectedPoints.get(i).setLocation(initialP.x + pointDifferenceX, initialP.y + pointDifferenceY);
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
        selectedPoints.clear();
    }

    public StrokeData getSelectedStroke() {
        return selectedStroke;
    }
}
