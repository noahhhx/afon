package io.github.noahhhx.afon.core.buffer;

public class CellGrid {

    private CellGrid() {}
    
    /** Fills the Cell grid with blank cells */
    public static Cell[][] filled(int rows, int cols, CellStyle style) {
        Cell[][] grid = new Cell[rows][cols];
        Cell cell = Cell.BLANK.withStyle(style);
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                grid[y][x] = cell;
            }
        }
        return grid;
    }
}
