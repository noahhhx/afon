package io.github.noahhhx.afon.core.runtime;

import io.github.noahhhx.afon.core.widget.View;

/**
 * The TUI App Interface.
 * 
 * @param <Model>
 * @param <Msg>
 */
public interface Tui<Model, Msg> {

    Model init();
    Model update(Model model, Msg msg);
    void view(Model model, View<Msg> view);
}
