import io.github.noahhhx.afon.core.runtime.Afon;
import io.github.noahhhx.afon.core.runtime.Tui;
import io.github.noahhhx.afon.core.widget.View;
import java.io.IOException;

class BasicTui implements Tui<Model, Msg> {

    @Override
    public Model init() {
        return Model.initial();
    }

    @Override
    public Model update(Model model, Msg msg) {
        return switch (msg) {
            case ChangeText() -> new Model("t");
            case UpdateText ut -> new Model(String.valueOf(ut.c()));
        };
    }

    @Override
    public void view(Model model, View<Msg> view) {
        view.text(model.name());
        view.onAnyChar(UpdateText::new);
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        Afon.run(new BasicTui());
    }
}

record Model(String name) {
    static Model initial() {
        return new Model("test");
    }
}

sealed interface Msg {}
record ChangeText() implements Msg {}
record UpdateText(char c) implements Msg {}
