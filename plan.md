# Plan

Initial ideas and notes on what I want to achieve, this will grow and change and be ticked
over time I guess.

---

## Initial concept

- Immediate mode: UI is snapshot of state. New concept to me, worth exploring.
- Elm Architecture: Basically stolen from charm, but it seems nice to work with.
- Java 17: Atleast initially, modern enough while still supporting LTS.
- Terminal I/O: Think Jline is the only real option, can look into doing myself but ye...

## Structure

Maven modules:
- core
- widget (Button, List, etc)
- layout? (does this come under style idk)
- style
- examples


---

## Core

- Runtime
- Widget API surface
- Terminal
- Render
- Input
- Colour (Maybe we don't need here, it's just an interface the style module uses?)

## Widget

- Button
- TextInput
- Label
- List
- Table
- ProgressBar

## Layout

- Grids?

## Style


## Examples

- Playground for implementing above
- Shopping list
- Counter


---
