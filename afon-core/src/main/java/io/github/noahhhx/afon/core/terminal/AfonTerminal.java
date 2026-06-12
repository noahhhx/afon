package io.github.noahhhx.afon.core.terminal;

import static io.github.noahhhx.afon.core.input.Key.KEY_MAP;

import io.github.noahhhx.afon.core.input.InputEvent;
import io.github.noahhhx.afon.core.input.InputEvent.KeyChar;
import io.github.noahhhx.afon.core.input.InputEvent.KeyPress;
import io.github.noahhhx.afon.core.input.Key;
import java.io.Closeable;
import java.io.IOException;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal.SignalHandler;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

/**
 * A JLine Terminal Wrapper.
 */
public class AfonTerminal implements Closeable {

    private final Terminal terminal;

    public AfonTerminal() throws IOException {
        this.terminal = TerminalBuilder.builder()
              .system(true)
              .signalHandler(SignalHandler.SIG_IGN) // Ctrl+C will not exit
              .build();
    }


    /**
     * Reads input from the JLine terminal, and parses it to an Afon {@link InputEvent}.
     *
     * @return the single input event, or null if not recognised.
     */
    public InputEvent pollEvents() throws IOException {
        int key = terminal.reader().read();
        if (key < 0) {
            return null;
        }

        if (key == 3 || key == 4) {
            // Ctrl+c/d - exit
            System.exit(0);
        }

        if (key == 27) {
            // Handle arrows, etc
            return parseEscapeSequence();
        }

        if (Character.isISOControl(key)) {
            Key mapped = KEY_MAP.get(key);
            return mapped != null ? new KeyPress(mapped) : null;
        }
        return new KeyChar((char) key);
    }

    private InputEvent parseEscapeSequence() throws IOException {
        NonBlockingReader reader = terminal.reader();
        int next = reader.peek(20);
        if (next < 0) {
            return new KeyPress(Key.ESC);
        }
        if (next == '[') {
            reader.read();
            return parseBracketSeq(reader);
        }
        if (next == 'O') {
            reader.read();
            return parseFnStyle(reader);
        }
        return null;
    }

    private InputEvent parseBracketSeq(NonBlockingReader reader) throws IOException {
        int c = reader.read();
        if (c < 0) {
            return null;
        }

        if (c >= '0' && c <= '9') {
            return parseTildeSeq(reader, c - '0');
        }

        return switch (c) {
            case 'A' -> new KeyPress(Key.UP);
            case 'B' -> new KeyPress(Key.DOWN);
            case 'C' -> new KeyPress(Key.RIGHT);
            case 'D' -> new KeyPress(Key.LEFT);
            case 'H' -> new KeyPress(Key.HOME);
            case 'F' -> new KeyPress(Key.END);
            default -> null;
        };
    }

    private InputEvent parseTildeSeq(NonBlockingReader reader, int firstDigit) throws IOException {
        int value = firstDigit;
        int c;
        while ((c = reader.peek(50)) >= '0' && c <= '9') {
            reader.read();
            value = value * 10 + (c - '0');
        }
        if (reader.read() != '~') {
            return null;
        }

        return switch (value) {
            case 1, 7 -> new KeyPress(Key.HOME);
            case 2 -> null; // Insert, not mapped
            case 3 -> new KeyPress(Key.DELETE);
            case 4, 8 -> new KeyPress(Key.END);
            case 5 -> new KeyPress(Key.PAGE_UP);
            case 6 -> new KeyPress(Key.PAGE_DOWN);
            case 11 -> new KeyPress(Key.F1);
            case 12 -> new KeyPress(Key.F2);
            case 13 -> new KeyPress(Key.F3);
            case 14 -> new KeyPress(Key.F4);
            case 15 -> new KeyPress(Key.F5);
            case 17 -> new KeyPress(Key.F6);
            case 18 -> new KeyPress(Key.F7);
            case 19 -> new KeyPress(Key.F8);
            case 20 -> new KeyPress(Key.F9);
            case 21 -> new KeyPress(Key.F10);
            case 23 -> new KeyPress(Key.F11);
            case 24 -> new KeyPress(Key.F12);
            default -> null;
        };
    }

    private InputEvent parseFnStyle(NonBlockingReader reader) throws IOException {
        int c = reader.read();
        return switch (c) {
            case 'P' -> new KeyPress(Key.F1);
            case 'Q' -> new KeyPress(Key.F2);
            case 'R' -> new KeyPress(Key.F3);
            case 'S' -> new KeyPress(Key.F4);
            case 'A' -> new KeyPress(Key.UP);
            case 'B' -> new KeyPress(Key.DOWN);
            case 'C' -> new KeyPress(Key.RIGHT);
            case 'D' -> new KeyPress(Key.LEFT);
            case 'H' -> new KeyPress(Key.HOME);
            case 'F' -> new KeyPress(Key.END);
            default -> null;
        };
    }

    public void writeLine(String str) {
        terminal.writer().println(str);
        terminal.writer().flush();
    }

    public void enterRawMode() {
        terminal.enterRawMode();
    }

    @Override
    public void close() throws IOException {
        terminal.close();
    }
}
