package managers;

import java.awt.*;
import java.util.ArrayList;

public class StrokeManager {

    private static StrokeManager instance;

    private final ArrayList<ArrayList<Point>> strokes = new ArrayList<>();
    private ArrayList<Point> currentStroke;
    private int lineThickness = 3;

    private StrokeManager() {}

    public static StrokeManager getInstance() {
        if (instance == null) {
            instance = new StrokeManager();
        }
        return instance;
    }

    public void startStroke(Point initialPoint) {
        currentStroke = new ArrayList<>();
        currentStroke.add(new Point(lineThickness, 0));
        currentStroke.add(initialPoint);
        strokes.add(currentStroke);
    }

    public void endStroke() {
        currentStroke = null;
    }

    public void addPointToStroke(Point point) {
        if (currentStroke != null) {
            currentStroke.add(point);
        }
    }

    public void undoLastStroke() {
        if (!strokes.isEmpty()) {
            strokes.removeLast();
        }
    }

    public void drawStrokes(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);

        for (ArrayList<Point> stroke : strokes) {
            g2d.setStroke(new BasicStroke(
                    stroke.getFirst().x,
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND
            ));
            for (int i = 2; i < stroke.size(); i++) {
                Point p1 = stroke.get(i - 1);
                Point p2 = stroke.get(i);
                g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
            }
        }
    }

    public void setLineThickness(int lineThickness) {
        this.lineThickness = lineThickness;
    }

}
