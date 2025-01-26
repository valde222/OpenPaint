package brushes;

import managers.StrokeData;
import managers.StrokeManager;
import managers.StrokeProperty;

import java.awt.*;
import java.util.ArrayList;

public abstract class AbstractBrush implements Brush {
    private String name;

    StrokeManager strokeManager = StrokeManager.getInstance();
    public StrokeData currentStroke;

    public AbstractBrush(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void startStroke(Point initialPoint, StrokeProperty strokeProperty) {
        currentStroke = new StrokeData(new ArrayList<>(), new StrokeProperty(
                strokeProperty.getLineThickness(),
                strokeProperty.getStrokeColor()
        ));
        currentStroke.addPoint(initialPoint);
        System.out.println("Start Stroke");
    }

    @Override
    public void endStroke(Point endPoint) {
        if (currentStroke != null) {
            currentStroke.addPoint(endPoint);
            strokeManager.addStroke(currentStroke);
            System.out.println("End Stroke");
            System.out.println(currentStroke.getPoints().size());
            currentStroke = null;
        }
    }
}
