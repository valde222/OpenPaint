package Interfaces;

import managers.StrokeData;

import java.awt.*;

public interface Brush {
    String getName();
    StrokeData updateStroke(Point currentPoint, StrokeData currentStroke);
}
