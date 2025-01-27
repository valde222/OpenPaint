package managers;

import brushes.BrushType;

import java.awt.*;
import java.util.ArrayList;

public class StrokeManager {

    private static StrokeManager instance;

    private final ArrayList<StrokeData> strokes = new ArrayList<>();
    private StrokeData currentStroke;

    private final StrokeProperty defaultStrokeProperty = new StrokeProperty();

    private BrushType brushType = BrushType.FREEHAND;

    private Boolean MoveToolActive = false;
    private StrokeData selectedStroke;

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

    public void moveStrokeStart(Point point) {
        int threshold = 10;
        for (StrokeData stroke : strokes) {
            for (Point p : stroke.getPoints()) {
                if (p.distance(point) <= threshold) {
                    System.out.println("Stroke moved");
                    selectedStroke = stroke;
                    return;
                }
            }
        }
    }

    public void moveStroke(Point point) {
        if (selectedStroke != null) {
            for (Point p : selectedStroke.getPoints()) {
                int pointDifferenceX = point.x - p.x;
                int pointDifferenceY = point.y - p.y;
                System.out.println("Point difference: " + pointDifferenceX + ", " + pointDifferenceY);
                p.setLocation(p.x + pointDifferenceX, p.y + pointDifferenceY);
            }
        }
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