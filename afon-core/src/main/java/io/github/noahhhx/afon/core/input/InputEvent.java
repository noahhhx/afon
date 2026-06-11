package io.github.noahhhx.afon.core.input;

public sealed interface InputEvent {

    /** A printable character was pressed */
    record KeyChar(char ch) implements InputEvent {}
}
