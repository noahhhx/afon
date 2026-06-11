package io.github.noahhhx.afon.core.terminal;

import io.github.noahhhx.afon.core.input.InputEvent;
import io.github.noahhhx.afon.core.input.InputEvent.KeyChar;
import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.jline.terminal.Terminal;
import org.jline.terminal.Terminal.Signal;
import org.jline.terminal.Terminal.SignalHandler;
import org.jline.terminal.TerminalBuilder;

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
     * Reads input from the JLine terminal, and parses it to an Afon
     * {@link InputEvent}.
     */
    public List<InputEvent> pollEvents() throws IOException {
        int key;
        while ((key = terminal.reader().read()) != -1 && key != 4) {
            terminal.writer().println("You pressed: " + (char) key);
            terminal.writer().flush();
            return Collections.singletonList(new KeyChar((char) key));
        }
        return Collections.singletonList(new KeyChar((char) key));
    }
    
    public void enterRawMode() {
        terminal.enterRawMode();
    }
    
    @Override
    public void close() throws IOException {
        terminal.close();
    }
}
