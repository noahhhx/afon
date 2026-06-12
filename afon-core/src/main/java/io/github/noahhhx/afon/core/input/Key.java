package io.github.noahhhx.afon.core.input;

import java.util.Map;

public enum Key {
    UP, DOWN, LEFT, RIGHT,
    HOME, END, PAGE_UP, PAGE_DOWN,
    ENTER, TAB, ESC, BACKSPACE, DELETE,
    F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12;
    
    public static final Map<Integer, Key> KEY_MAP = Map.ofEntries(
          Map.entry(9, TAB),
          Map.entry(13, ENTER),
          Map.entry(127, BACKSPACE)
    );
}
