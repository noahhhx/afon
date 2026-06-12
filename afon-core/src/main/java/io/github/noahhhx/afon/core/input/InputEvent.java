package io.github.noahhhx.afon.core.input;

public sealed interface InputEvent {

    /** A printable character was pressed */
    record KeyChar(char ch) implements InputEvent {}
    
    /** Non-character was pressed (e.g. Enter, Up Arrow) */
    record KeyPress(Key key) implements InputEvent {}
}
