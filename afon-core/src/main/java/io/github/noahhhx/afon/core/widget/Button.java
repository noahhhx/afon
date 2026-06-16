package io.github.noahhhx.afon.core.widget;

import io.github.noahhhx.afon.core.input.InputEvent;
import io.github.noahhhx.afon.core.input.InputEvent.KeyPress;
import io.github.noahhhx.afon.core.input.Key;
import io.github.noahhhx.afon.core.render.Canvas;
import java.util.function.Function;

public class Button<Msg> implements Widget {

    private final boolean selected;
    private final String id;
    private final String label;
    private final Msg message;
    private final Key key;
    private final Function<InputEvent, Msg> handler;
    
    public Button(String id, String label, Msg message, Key key, boolean selected, 
          Function<InputEvent, Msg> handler) {
        this.label = label;
        this.id = id;
        this.message = message;
        this.key = key;
        this.selected = selected;
        this.handler = handler;
    }

    /**
     * This can return null, if a button is not active
     * 
     * @return Message when button clicked
     */
    public Msg tryActivate(InputEvent ev) {
        if (!selected) {
            return null;
        }
        // handler first (Tab swapping etc)
        if (handler != null) {
            Msg result = handler.apply(ev);
            if (result != null) {
                return result;
            }
        }
        // activation key
        if (ev instanceof KeyPress(var pressed) && pressed == key) {
            return message;
        }
        return null;
    }
    
    public void render(Canvas canvas) {
        String renderText = label;
        if (selected) {
            renderText = "> " + label;
        }
        canvas.write(renderText);
    }
}
