package io.github.noahhhx.afon.core.buffer;

public record Cell(char ch, CellStyle style) {

    public static final Cell BLANK = new Cell(' ', CellStyle.DEFAULT);
    
    public Cell withChar(char c) {
        return new Cell(c, style);
    }
    
    public Cell withStyle(CellStyle style) {
        return new Cell(ch, style);
    }
}
