package io.github.noahhhx.afon.core.render;

import io.github.noahhhx.afon.core.buffer.CellStyle;
import io.github.noahhhx.afon.core.buffer.ScreenBuffer;
import io.github.noahhhx.afon.core.geometry.Rect;
import io.github.noahhhx.afon.core.widget.Widget;

public class Canvas {

    private final ScreenBuffer buffer;
    private final Rect bounds;
    private int cursorX, cursorY;
    private CellStyle style = CellStyle.DEFAULT;

    public Canvas(ScreenBuffer buffer, Rect bounds) {
        this.buffer = buffer;
        this.bounds = bounds;
    }
    
    public void write(String text) {
        buffer.writeStr(bounds.x() + cursorX, bounds.y() + cursorY, text, style);
        cursorX += text.length();
    }
    
    public void renderWidget(Widget widget) {
        widget.render(this);
    }
    
    public int cursorX() {
        return this.cursorX;
    }
    
    public void setCursorX(int x) {
        this.cursorX = x;
    }
    
    public int cursorY() {
        return this.cursorY;
    }

    public void setCursorY(int y) {
        this.cursorY = y;
    }

    public Rect bounds() {
        return this.bounds;
    }
    
    public void setStyle(CellStyle style) {
        this.style = style;
    }
    
    public CellStyle style() {
        return style;
    }
}
