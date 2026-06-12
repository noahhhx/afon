package io.github.noahhhx.afon.core.runtime;

import io.github.noahhhx.afon.core.buffer.ScreenBuffer;
import io.github.noahhhx.afon.core.geometry.Rect;
import io.github.noahhhx.afon.core.input.InputEvent;
import io.github.noahhhx.afon.core.terminal.AfonTerminal;
import io.github.noahhhx.afon.core.widget.View;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The main TuiRuntime event loop.
 * 
 * @param <Model>
 * @param <Msg>
 */
class TuiRuntime<Model, Msg> {

    private final Tui<Model, Msg> tui;
    private final AfonTerminal terminal;
    private final ScreenBuffer buffer;
    private final Queue<Msg> messages;
    private final Map<String, Object> widgetState = new HashMap<>();
    private Model model;
    private volatile boolean running = true;
    
    TuiRuntime(Tui<Model, Msg> tui) throws IOException {
        this.tui = tui;
        this.terminal = new AfonTerminal();
        this.buffer = new ScreenBuffer(terminal.rows(), terminal.cols());
        this.messages = new ConcurrentLinkedQueue<>();
    }
    
    void run() throws InterruptedException, IOException {
        // Some stuff with terminal setup
        terminal.enterAlternateScreen();
        terminal.enterRawMode();
        terminal.probeSynchronizedOutput();
        terminal.hideCursor();
        terminal.flush();
        
        model = tui.init();
        while (running) {
            
            // Read input events
            List<InputEvent> events = new ArrayList<>();
            InputEvent event;
            while ((event = terminal.pollEvents()) != null) {
                events.add(event);
            }
            
            // Build view
            buffer.clear();
            Rect fullScreen = new Rect(0, 0, terminal.cols(), terminal.rows());
            View<Msg> view = new View<>(buffer, events, messages, widgetState, fullScreen);
            tui.view(model, view);

            
            // Flush to terminal
            buffer.flush(terminal);
            
            // Process messages??
            Msg msg;
            while ((msg = messages.poll()) != null) {
                model = tui.update(model, msg);
            }
            
            // Ever a need for sleep?
            // For now, we sleep just for testing
            Thread.sleep(10);
        }
        
        terminal.leaveAlternateScreen();
    }
}
