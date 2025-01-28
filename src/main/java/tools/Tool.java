package tools;

import strokes.StrokeData;

import java.awt.*;
import java.util.List;

public interface Tool {
    void onMousePress(Point point);
    void onMouseDrag(Point point);
    void onMouseRelease(Point point);
    String getName();
    boolean isPointOnStroke(Point point, List<StrokeData> strokes);
}
