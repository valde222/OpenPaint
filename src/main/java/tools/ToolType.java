package tools;

public enum ToolType {
    MOVE_TOOL(new MoveTool()),
    DRAWING_TOOL(new DrawingTool()),
    EDIT_TOOL(new EditingTool());

    private final Tool tool;

    ToolType(Tool tool) {
        this.tool = tool;
    }

    public Tool getTool() {
        return tool;
    }

    public String getName() {
        return tool.getName();
    }
}
