package managers;

import brushes.BrushType;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class StrokeManager {

    private static StrokeManager instance;

    private final ArrayList<StrokeData> strokes = new ArrayList<>();
    private StrokeData currentStroke;

    private final StrokeProperty defaultStrokeProperty = new StrokeProperty();

    private BrushType brushType = BrushType.FREEHAND;

    private Boolean MoveToolActive = false;
    private StrokeData selectedStroke;
    private Point initialPoint;
    private List<Point> initialPoints;

    private StrokeManager() {}

    public static StrokeManager getInstance() {
        if (instance == null) {
            instance = new StrokeManager();
        }
        return instance;
    }

    public void setBrushType(BrushType brushType) {
        this.brushType = brushType;
    }

    public void startStroke(Point initialPoint) {
        currentStroke = new StrokeData(new ArrayList<>(), new StrokeProperty(
                defaultStrokeProperty.getLineThickness(),
                defaultStrokeProperty.getStrokeColor()
        ));
        currentStroke.addPoint(initialPoint);
    }

    public void endStroke() {
        if (currentStroke != null) {
            addStroke(currentStroke);
            currentStroke = null;
        }
    }

    public void updateStroke(Point currentPoint) {
        if (currentStroke != null) {
            this.currentStroke = this.brushType.getBrush().updateStroke(currentPoint, currentStroke);
        }

    }

    public void addStroke(StrokeData stroke) {
        strokes.add(stroke);
    }

    public void undoLastStroke() {
        if (!strokes.isEmpty()) {
            strokes.removeLast();
        }
    }

    public void drawStrokes(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (StrokeData stroke : strokes) {
            drawStroke(g2d, stroke);
        }

        if (currentStroke != null) {
            drawStroke(g2d, currentStroke);
        }

        if (selectedStroke != null) {
            drawSelectionBox(g2d, selectedStroke);
        }
    }

    private void drawStroke(Graphics2D g2d, StrokeData stroke) {
        StrokeProperty props = stroke.getStrokeProperty();
        g2d.setColor(props.getStrokeColor());
        g2d.setStroke(new BasicStroke(
                props.getLineThickness(),
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND
        ));

        java.util.List<Point> points = stroke.getPoints();
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    private void drawSelectionBox(Graphics2D g2d, StrokeData stroke) {
        List<Point> points = stroke.getPoints();
        if (points.isEmpty()) return;

        int minX = points.stream().mapToInt(p -> p.x).min().orElse(0);
        int minY = points.stream().mapToInt(p -> p.y).min().orElse(0);
        int maxX = points.stream().mapToInt(p -> p.x).max().orElse(0);
        int maxY = points.stream().mapToInt(p -> p.y).max().orElse(0);

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setStroke(new BasicStroke(1));
        g2d.drawRect(minX - 5, minY - 5, (maxX - minX) + 10, (maxY - minY) + 10);
    }

    public void moveStrokeStart(Point point) {
        for (StrokeData stroke : strokes) {
            if (isPointOnStroke(point, stroke)) {
                selectedStroke = stroke;
                initialPoint = point;
                initialPoints = new ArrayList<>();
                for (Point p : stroke.getPoints()) {
                    initialPoints.add(new Point(p));
                }
                return;
            }
        }
    }

    public void moveStroke(Point point) {
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

    public void moveStrokeEnd() {
        selectedStroke = null;
        initialPoint = null;
        initialPoints = null;
    }

    public boolean isPointOnStroke(Point point) {
        for (StrokeData stroke : strokes) {
            if (isPointOnStroke(point, stroke)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPointOnStroke(Point point, StrokeData stroke) {
        int threshold = 10;
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

    private boolean isPointNearLine(Point p, Point p1, Point p2, int threshold) {
        double distance = Line2D.ptSegDist(p1.x, p1.y, p2.x, p2.y, p.x, p.y);
        return distance <= threshold;
    }

    public void setLineThickness(int lineThickness) {
        defaultStrokeProperty.setLineThickness(lineThickness);
    }

    public void setStrokeColor(Color strokeColor) {
        defaultStrokeProperty.setStrokeColor(strokeColor);
    }

    public void setMoveToolActive(Boolean MoveToolActive) {
        this.MoveToolActive = MoveToolActive;
    }

    public Boolean getMoveToolActive() {
        return MoveToolActive;
    }
}