package brushes;

import Interfaces.Brush;

public abstract class AbstractBrush implements Brush {
    private final String name;

    public AbstractBrush(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
