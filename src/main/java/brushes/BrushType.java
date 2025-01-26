package brushes;

public enum BrushType {
    FREEHAND(new FreeHandBrush()),
    STRAIGHT_LINE(new StraightLineBrush());

    private final Brush brush;

    BrushType(Brush brush) {
        this.brush = brush;
    }

    public Brush getBrush() {
        return brush;
    }

    public String getName() {
        return brush.getName();
    }
}