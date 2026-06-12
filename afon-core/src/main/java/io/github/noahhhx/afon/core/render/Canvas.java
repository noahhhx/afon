package io.github.noahhhx.afon.core.render;

import io.github.noahhhx.afon.core.buffer.CellStyle;
import io.github.noahhhx.afon.core.buffer.ScreenBuffer;
import io.github.noahhhx.afon.core.geometry.Rect;

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
        buffer.writeStr(cursorX, cursorY, text, style);
        cursorX += text.length();
    }
    
    public void setStyle(CellStyle style) {
        this.style = style;
    }
    
    public CellStyle style() {
        return style;
    }
}
