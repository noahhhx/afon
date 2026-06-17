package io.github.noahhhx.afon.core.buffer;

import io.github.noahhhx.afon.core.terminal.AfonTerminal;
import java.util.Arrays;

public class ScreenBuffer {
    
    private int rows, cols;
    private Cell[][] front; // Last frame flushed to terminal
    private Cell[][] back; // Current frame being built
    private boolean[] dirty; // mark rows as dirty (changed)
    
    public ScreenBuffer(int rows, int cols) {
        this.front = CellGrid.filled(rows, cols, CellStyle.DEFAULT);
        this.back = CellGrid.filled(rows, cols, CellStyle.DEFAULT);
        this.dirty = new boolean[rows];
        this.rows = rows;
        this.cols = cols;
    }
    
    public void set(int x, int y, Cell cell) {
        if (x < 0 || x >= cols || y < 0 || y >= rows) {
            return;
        }
        back[y][x] = cell;
        dirty[y] = true;
    }
    
    /** Write a string of styled text at position */
    public void writeStr(int x, int y, String text, CellStyle style) {
        for (int i = 0; i < text.length(); i++) {
            set(x + i, y, new Cell(text.charAt(i), style));
        }
    }
    
    public void flush(AfonTerminal terminal) {
        terminal.write("\033[H"); // reset cursor
        if (!terminal.supportsSyncUpdate()) {
            flushSimple(terminal);
            return;
        }
        
        terminal.beginSynchronizedUpdate();
        
        // TODO - Implement better flush (less flicker/tearing)
        flushSimple(terminal);
        
        terminal.hideCursor();
        terminal.endSynchronizedUpdate();
        terminal.flush();
    }

    private void flushSimple(AfonTerminal terminal) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = back[r][c];
                terminal.write(String.valueOf(cell.ch()));
            }
        }
    }
    
    public void clear() {
        for (int y = 0; y < rows; y++) {
            if (dirty[y]) {
                Arrays.fill(back[y], Cell.BLANK);
            }
        }
        Arrays.fill(dirty, false);
    }
    
    public int rows() {
        return rows;
    }
    
    public int cols() {
        return cols;
    }

}
