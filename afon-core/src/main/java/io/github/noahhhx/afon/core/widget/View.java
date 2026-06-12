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

    /** Render plan text. */
    public void text(String text) {
        canvas.write(text);
    };
    
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
