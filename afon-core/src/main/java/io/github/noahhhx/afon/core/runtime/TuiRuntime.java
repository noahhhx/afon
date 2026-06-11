package io.github.noahhhx.afon.core.runtime;

import io.github.noahhhx.afon.core.input.InputEvent;
import io.github.noahhhx.afon.core.input.InputEvent.KeyChar;
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
            List<InputEvent> events = terminal.pollEvents();
            events.forEach(event -> {
                switch (event) {
                    case KeyChar ch -> {
                        if (ch.ch() == 'q') {
                            System.exit(0);
                        } else {
                            System.out.println(ch.ch());    
                        }
                    }
                }
            });
            
            // Build view
            
            
            // Flush to terminal
            
            
            // Process messages??
            
            
            // Ever a need for sleep?
            // For now, we sleep just for testing
            Thread.sleep(100);
        }
    }
}
