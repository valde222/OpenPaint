package strokes;

import brushes.BrushType;
import tools.MoveTool;
import tools.ToolType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StrokeManager {

    private static StrokeManager instance;
    private final List<StrokeData> strokes = new ArrayList<>();
    private StrokeData currentStroke;
    private final StrokeProperty defaultStrokeProperty = new StrokeProperty();
    private BrushType brushType = BrushType.FREEHAND;
    private ToolType toolType = ToolType.DRAWING_TOOL;
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

    public void onMouseClick(Point point) {
        toolType.getTool().onMousePress(point);
    }

    public void onMouseDrag(Point point) {
        toolType.getTool().onMouseDrag(point);
    }

    public void onMouseRelease(Point point) {
        toolType.getTool().onMouseRelease(point);
    }

    public void addStroke(StrokeData stroke) {
        strokes.add(stroke);
    }

    public void undoLastStroke() {
        if (!strokes.isEmpty()) {
            if (selectedStroke != null && strokes.getLast() == selectedStroke) {
                selectedStroke = null;
                strokes.removeLast();
            } else {
                strokes.removeLast();
            }
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
        g2d.setStroke(new BasicStroke(props.getLineThickness(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        List<Point> points = stroke.getPoints();
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

    public boolean isPointOnStroke(Point point) {
        return new MoveTool().isPointOnStroke(point, strokes);
    }

    public void deleteSelectedStroke() {
        if (selectedStroke != null) {
            strokes.remove(selectedStroke);
            selectedStroke = null;
        }
    }

    public void setLineThickness(int lineThickness) {
        defaultStrokeProperty.setLineThickness(lineThickness);
    }

    public void setStrokeColor(Color strokeColor) {
        defaultStrokeProperty.setStrokeColor(strokeColor);
    }

    public StrokeData getSelectedStroke() {
        return selectedStroke;
    }

    public List<StrokeData> getStrokes() {
        return strokes;
    }

    public void updateSpecificStroke(StrokeData stroke) {
        strokes.remove(stroke);
        strokes.add(stroke);
    }

    public void setSelectedStroke(StrokeData stroke) {
        selectedStroke = stroke;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
        selectedStroke = null;
        currentStroke = null;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public void setCurrentStroke(StrokeData currentStroke) {
        this.currentStroke = currentStroke;
    }

    public StrokeProperty getDefaultStrokeProperty() {
        return defaultStrokeProperty;
    }

    public BrushType getBrushType() {
        return brushType;
    }
}