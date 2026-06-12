package io.github.noahhhx.afon.core.runtime;

import io.github.noahhhx.afon.core.input.InputEvent;
import io.github.noahhhx.afon.core.input.InputEvent.KeyChar;
import io.github.noahhhx.afon.core.input.InputEvent.KeyPress;
import io.github.noahhhx.afon.core.terminal.AfonTerminal;
import java.io.IOException;
import java.util.List;

/**
 * The main TuiRuntime event loop.
 * 
 * @param <Model>
 * @param <Msg>
 */
class TuiRuntime<Model, Msg> {

    private final Tui<Model, Msg> tui;
    private final AfonTerminal terminal;
    
    private Model model;
    private boolean running = true;
    
    TuiRuntime(Tui<Model, Msg> tui) throws IOException {
        this.tui = tui;
        this.terminal = new AfonTerminal();
    }
    
    void run() throws InterruptedException, IOException {
        // Some stuff with terminal setup
        terminal.enterRawMode();
        
        model = tui.init();
        while (running) {
            System.out.println("We are running..");
            
            // Read input events
            InputEvent event = terminal.pollEvents();
            if (event == null) {
                continue;
            }
            switch (event) {
                case KeyChar ch -> {
                    terminal.writeLine(String.valueOf(ch.ch()));
                }
                case KeyPress press -> {
                    terminal.writeLine(String.valueOf(press));
                }
                default -> System.out.println("nothing");
            }
            
            // Build view
            
            
            // Flush to terminal
            
            
            // Process messages??
            
            
            // Ever a need for sleep?
            // For now, we sleep just for testing
            Thread.sleep(100);
        }
    }
}
