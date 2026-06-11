# Afon

TUI framework written in Java.

There already exists the greats, [Charm](https://github.com/charmbracelet/bubbletea) &
[Ratatui](https://github.com/ratatui/ratatui), but I like TUIs and want to learn.

## Requirements

- Java 21
- Maven
- GraalVM ?? (Should support)


## Examples

When using any examples or writing code yourself, make sure to use a "real" terminal. Things
like IntelliJ's run console don't work nicely with JLine Terminal. \
TO help with this the [playground](./afon-examples/playground/src/main/java/BasicTui.java) 
example can be run via a make target `make run-playground`. Just copy the `exec-maven-plugin` for
your own projects.