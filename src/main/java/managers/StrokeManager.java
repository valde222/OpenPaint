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

    private Point temporaryEndPoint;

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
    public BrushType getBrushType() {
        return brushType;
    }

    public void startStroke(Point initialPoint) {
        currentStroke = new StrokeData(new ArrayList<>(), new StrokeProperty(
                defaultStrokeProperty.getLineThickness(),
                defaultStrokeProperty.getStrokeColor()
        ));
        currentStroke.addPoint(initialPoint);
        System.out.println("Start Stroke");
    }

    public void endStroke(Point endPoint) {
        if (currentStroke != null) {
            currentStroke.addPoint(endPoint);
            addStroke(currentStroke);
            System.out.println("End Stroke");
            System.out.println(currentStroke.getPoints().size());
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
        if (currentStroke != null) {
            StrokeProperty props = currentStroke.getStrokeProperty();
            g2d.setColor(props.getStrokeColor());
            g2d.setStroke(new BasicStroke(
                    props.getLineThickness(),
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND
            ));

            java.util.List<Point> points = currentStroke.getPoints();

            if (points.size() > 1) {
                for (int i = 1; i < points.size(); i++) {
                    Point p1 = points.get(i - 1);
                    Point p2 = points.get(i);
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }
    }

    public void setLineThickness(int lineThickness) {
        defaultStrokeProperty.setLineThickness(lineThickness);
    }

    public void setStrokeColor(Color strokeColor) {
        defaultStrokeProperty.setStrokeColor(strokeColor);
    }
}