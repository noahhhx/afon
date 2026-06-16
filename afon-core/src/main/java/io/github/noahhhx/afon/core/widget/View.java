package io.github.noahhhx.afon.core.widget;

import io.github.noahhhx.afon.core.buffer.ScreenBuffer;
import io.github.noahhhx.afon.core.geometry.Rect;
import io.github.noahhhx.afon.core.input.InputEvent;
import io.github.noahhhx.afon.core.input.InputEvent.KeyChar;
import io.github.noahhhx.afon.core.input.InputEvent.KeyPress;
import io.github.noahhhx.afon.core.input.Key;
import io.github.noahhhx.afon.core.render.Canvas;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Immediate-Mode Widget API
 */
public class View<Msg> {
    
    private final ScreenBuffer buffer;
    private final List<InputEvent> events;
    private final Queue<Msg> messages;
    private final Map<String, Object> widgetState;
    private final Canvas canvas;
    
    public View(ScreenBuffer buffer, List<InputEvent> events, Queue<Msg> messages,
          Map<String, Object> widgetState, Rect bounds) {
        this.buffer = buffer;
        this.events = events;
        this.messages = messages;
        this.widgetState = widgetState;
        this.canvas = new Canvas(buffer, bounds);
    }

    /** Split the remaining vertical space equally among children. */
    @SafeVarargs
    public final void verticalStack(Consumer<View<Msg>>... content) {
        int remainingH = canvas.bounds().height() - canvas.cursorY();
        int childCount = content.length;
        
        int childH = remainingH / childCount;
        for (int i = 0; i < childCount; i++) {
            Rect childRect = new Rect(
                  canvas.bounds().x(),
                  canvas.bounds().y() + canvas.cursorY(),
                  canvas.bounds().width(),
                  canvas.bounds().height() - childH
                  
            );
            View<Msg> child = new View<>(buffer, events, messages, widgetState, childRect);
            content[i].accept(child);
            canvas.setCursorY((i + 1) * childH);
            canvas.setCursorX(0);
        }
        canvas.setCursorX(0);
        canvas.setCursorY(canvas.bounds().height());
    };

    /** Split the remaining horizontal space equally among children. */
    @SafeVarargs
    public final void horizontalStack(Consumer<View<Msg>>... content) {
        int remainingW = canvas.bounds().width() - canvas.cursorX();
        int childCount = content.length;
        
        int childW = remainingW / childCount;
        for (int i = 0; i < childCount; i++) {
            Rect childRect = new Rect(
                  canvas.cursorX(),
                  canvas.bounds().y() + canvas.cursorY(), 
                  canvas.bounds().width() - childW,
                  canvas.bounds().height() - canvas.cursorY()
            );
            View<Msg> child = new View<>(buffer, events, messages, widgetState, childRect);
            content[i].accept(child);
            canvas.setCursorX((i + 1) * childW);
        }
        canvas.setCursorY(canvas.bounds().height());
    };

    /** Fill all remaining space with a single child. */
    public void fill(Consumer<View<Msg>> content) {
        // Calc remaining space
        int remainingW = canvas.bounds().width() - canvas.cursorX();
        int remainingH = canvas.bounds().height() - canvas.cursorY();
        if (remainingW <= 0 || remainingH <= 0) {
            // No space left
            return;
        }
        
        // Create child view with remaining space
        Rect childRect = new Rect(
              canvas.bounds().x(),                  // always left edge
              canvas.bounds().y() + canvas.cursorY(),
              canvas.bounds().width(),              // full width
              canvas.bounds().height() - canvas.cursorY()
        );
        View<Msg> child = new View<>(buffer, events, messages, widgetState, childRect);
        
        // Render child in that region
        content.accept(child);
        
        canvas.setCursorX(0);
        canvas.setCursorY(canvas.bounds().height());
    };

    /** Render plan text. */
    public void text(String text) {
        canvas.write(text);
    };
    
    /** Skip N lines of horizontal space */
    public void horizontalSpacer(int lines) {
        canvas.setCursorX(canvas.cursorX() + lines); 
    }
    
    /** Skip N lines of vertical space */
    public void verticalSpace(int lines) {
        canvas.setCursorY(canvas.cursorY() + lines);
        canvas.setCursorX(canvas.bounds().x()); // and reset X
        
    }
    
    /**
     * Create a button widget
     * 
     * @param id ID reference for the {@code WidgetState} Map.
     * @param label Text to be displayed for the button in the UI.
     * @param msg {@code Msg} to invoke when {@code Key} is pressed.
     * @param key {@code Key} to invoke the supplied {@code Msg}.
     * @param selected Should this button default to selected.
     * @param handler Optional function handler for dynamic {@code Msg} generation. Activates only
     *                on the selected button.
     */
    public void button(String id, String label, Msg msg, Key key, boolean selected, 
          Function<InputEvent, Msg> handler) {
        Button<Msg> btn = new Button<>(id, label, msg, key, selected, handler);
        widgetState.put(id, btn);
        canvas.renderWidget(btn);
        for (InputEvent ev : events) {
            Msg result = btn.tryActivate(ev);
            if (result != null) {
                messages.add(result);
                return;
            }
        }
    }
    
    /** Enqueue message if any key was pressed this frame. */
    public void onAnyKey(Msg msg) {
        for (InputEvent ev: events) {
            if (ev instanceof KeyPress || ev instanceof KeyChar) {
                messages.add(msg);
                return;
            }
        }
    }
    
    /** Enqueue msg if the given key was pressed this frame. */
    public void onKey(Key key, Msg msg) {
        for (InputEvent ev : events) {
            if (ev instanceof KeyPress(Key key1) && key1 == key) {
                messages.add(msg);
                return;
            }
        }
    }

    /** Enqueue msg if the given key was pressed this frame. */
    public void onChar(char ch, Msg msg) {
        for (InputEvent ev : events) {
            if (ev instanceof KeyChar(char ch1) && ch1 == ch) {
                messages.add(msg);
                return;
            }
        }
    }
    
    /** Enqueue msg if any char is pressed this frame. */
    public void onAnyChar(Function<Character, Msg> toMsg) {
        for (InputEvent ev: events) {
            if (ev instanceof KeyChar(char ch)) {
                messages.add(toMsg.apply(ch));
                return;
            }
        }
    }
}
