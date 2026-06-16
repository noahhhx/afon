import io.github.noahhhx.afon.core.input.InputEvent;
import io.github.noahhhx.afon.core.input.InputEvent.KeyPress;
import io.github.noahhhx.afon.core.input.Key;
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
            case IncrementCounter i -> new Model(i.i() + 1, model.focus());
            case DecrementCounter d -> new Model(d.i() - 1, model.focus());
            case NextFocus n -> new Model(model.count(), model.focus().next());
            case PrevFocus p -> new Model(model.count(), model.focus().prev());
            default -> new Model(model.count(), model.focus());
        };
    }

    @Override
    public void view(Model model, View<Msg> view) {
        view.verticalStack(
              v -> v.text(""), // blank to just put things centrally sort of
              v -> v.horizontalStack(
                    h -> h.text(""),
                    h -> h.text("Press '+' to increment: "),
                    h -> h.horizontalSpacer(5),
                    h -> h.text(String.valueOf(model.count())),
                    h -> h.text("")
              ),
              v -> view.horizontalStack(
                    h -> h.text(""),
                    h -> h.button("inc", "[+]", new IncrementCounter(model.count()),
                          Key.ENTER, model.focus() instanceof Focus.Inc , this::swapButton),
                    h -> h.button("desc", "[-]", new DecrementCounter(model.count()),
                          Key.ENTER, model.focus() instanceof Focus.Desc, this::swapButton),
                    h -> h.text("")
              ),
              v -> v.text("")
        );
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Afon.run(new BasicTui());
    }

    // Example of how we can "simply" have custom events
    private Msg swapButton(InputEvent event) {
        if (event instanceof KeyPress(var k) && k == Key.TAB) {
            return new NextFocus();
        }
        if (event instanceof KeyPress(var k1) && k1 == Key.BACKSPACE) {
            return new PrevFocus();
        }
        return null;
    }
}

record Model(int count, Focus focus) {

    static Model initial() {
        return new Model(0, Focus.initial());
    }
}

sealed interface Msg { }

record IncrementCounter(int i) implements Msg { }

record DecrementCounter(int i) implements Msg { }

record NextFocus() implements Msg { }
record PrevFocus() implements Msg { }

sealed interface Focus permits Focus.Inc, Focus.Desc {
    record Inc() implements Focus {}
    record Desc() implements Focus {}
    
    static Focus initial() {
        return new Inc();
    }
    
    default Focus next() {
        return switch (this) {
            case Inc i -> new Desc();
            case Desc d -> new Inc();
        };
    }
    
    default Focus prev() {
        return next();
    }
}
