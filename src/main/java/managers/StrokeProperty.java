package managers;

import java.awt.*;

public class StrokeProperty {

    private int lineThickness;
    private Color strokeColor;

    public StrokeProperty() {
        this.lineThickness = 3;
        this.strokeColor = Color.BLACK;
    }

    public StrokeProperty(int lineThickness, Color strokeColor) {
        this.lineThickness = lineThickness;
        this.strokeColor = strokeColor;
    }

    // Getters and Setters
    public int getLineThickness() {
        return lineThickness;
    }

    public void setLineThickness(int lineThickness) {
        this.lineThickness = lineThickness;
    }

    public Color getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }
}
