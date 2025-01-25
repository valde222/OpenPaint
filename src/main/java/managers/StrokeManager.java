package managers;

import java.awt.*;
import java.util.ArrayList;

public class StrokeManager {

    private static StrokeManager instance;

    private final ArrayList<StrokeData> strokes = new ArrayList<>();
    private StrokeData currentStroke;

    private final StrokeProperty defaultStrokeProperty = new StrokeProperty();

    private String brushType = "FREEHAND";

    private Point temporaryEndPoint;

    private StrokeManager() {}

    public static StrokeManager getInstance() {
        if (instance == null) {
            instance = new StrokeManager();
        }
        return instance;
    }

    public void setBrushType(String brushType) {
        this.brushType = brushType;
    }
    public String getBrushType() {
        return brushType;
    }

    public void startStroke(Point initialPoint) {
        currentStroke = new StrokeData(new ArrayList<>(), new StrokeProperty(
                defaultStrokeProperty.getLineThickness(),
                defaultStrokeProperty.getStrokeColor()
        ));
        currentStroke.addPoint(initialPoint);
        strokes.add(currentStroke);

        temporaryEndPoint = null;
    }

    public void updateTemporaryEndPoint(Point currentPoint) {
        if ("STRAIGHT_LINE".equals(brushType)) {
            temporaryEndPoint = currentPoint;
        }
    }

    public void endStroke(Point endPoint) {
        if ("STRAIGHT_LINE".equals(brushType) && currentStroke != null) {
            currentStroke.addPoint(endPoint);
        }
        currentStroke = null;
        temporaryEndPoint = null;
    }

    public void addPointToStroke(Point point) {
        if ("FREEHAND".equals(brushType)) {
            if (currentStroke != null) {
                currentStroke.addPoint(point);
            }
        }
    }

    public void undoLastStroke() {
        if (!strokes.isEmpty()) {
            strokes.removeLast();
        }
    }

    public void drawStrokes(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        for (StrokeData stroke : strokes) {
            StrokeProperty props = stroke.getStrokeProperty();
            g2d.setColor(props.getStrokeColor());
            g2d.setStroke(new BasicStroke(
                    props.getLineThickness(),
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND
            ));

            java.util.List<Point> points = stroke.getPoints();
            if (points.size() > 1) {
                for (int i = 1; i < points.size(); i++) {
                    Point p1 = points.get(i - 1);
                    Point p2 = points.get(i);
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

        if ("STRAIGHT_LINE".equals(brushType) && currentStroke != null && temporaryEndPoint != null) {
            StrokeProperty props = currentStroke.getStrokeProperty();
            g2d.setColor(props.getStrokeColor());
            g2d.setStroke(new BasicStroke(
                    props.getLineThickness(),
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND
            ));

            Point startPoint = currentStroke.getPoints().getFirst();
            g2d.drawLine(startPoint.x, startPoint.y, temporaryEndPoint.x, temporaryEndPoint.y);
        }
    }

    public void setLineThickness(int lineThickness) {
        defaultStrokeProperty.setLineThickness(lineThickness);
    }

    public void setStrokeColor(Color strokeColor) {
        defaultStrokeProperty.setStrokeColor(strokeColor);
    }
}