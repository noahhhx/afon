package io.github.noahhhx.afon.core.geometry;

public record Rect(int x, int y, int width, int height) {

    public static Rect of(int x, int y, int w, int h) {
        return new Rect(x, y, w, h);
    }
}
