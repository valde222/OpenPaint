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
        this.brushType.getBrush().startStroke(initialPoint, defaultStrokeProperty);
    }

    public void endStroke(Point endPoint) {
        this.brushType.getBrush().endStroke(endPoint);
        System.out.println(strokes.size());
    }

    public void updateStroke(Point currentPoint) {
        this.brushType.getBrush().updateStroke(currentPoint);
    }

    public void addStroke(StrokeData stroke) {
        System.out.println("Adding stroke");
        System.out.println(stroke.getPoints().size());
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
    }

    public void setLineThickness(int lineThickness) {
        defaultStrokeProperty.setLineThickness(lineThickness);
    }

    public void setStrokeColor(Color strokeColor) {
        defaultStrokeProperty.setStrokeColor(strokeColor);
    }
}