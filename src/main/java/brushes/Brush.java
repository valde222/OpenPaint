package brushes;

import managers.StrokeData;
import managers.StrokeProperty;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface Brush {
    String getName();
    StrokeData updateStroke(Point currentPoint, StrokeData currentStroke);
}
