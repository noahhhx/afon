package io.github.noahhhx.afon.core.runtime;

import java.io.IOException;

/**
 * The entry point.
 */
public class Afon {
    
    private Afon() {}
    
    public static <M, S> void run(Tui<M, S> tui) throws InterruptedException, IOException {
        TuiRuntime<M, S> runtime = new TuiRuntime<>(tui);
        runtime.run();
    }
}
