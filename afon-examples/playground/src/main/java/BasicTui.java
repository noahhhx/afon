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
            case IncrementCounter i -> new Model(i.i() + 1);
            case DecrementCounter d -> new Model(d.i() - 1);
            default -> new Model(model.count());
        };
    }

    @Override
    public void view(Model model, View<Msg> view) {
        view.onChar('+', new IncrementCounter(model.count()));
        view.onChar('-', new DecrementCounter(model.count()));
        view.verticalStack(
              v -> v.text(""), // blank to just put things centrally sort of
              v -> v.horizontalStack(
                    h -> h.text(""),
                    h -> h.text("Press '+' to increment: "),
                    h -> h.text(String.valueOf(model.count())),
                    h -> h.text("")
              ),
              v-> v.text("")
        );
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        Afon.run(new BasicTui());
    }
}

record Model(int count) {
    static Model initial() {
        return new Model(0);
    }
}

sealed interface Msg {}
record IncrementCounter(int i) implements Msg { }
record DecrementCounter(int i) implements Msg { }
