// File: src/main/java/Command.java
public enum Command {
    LIST("list"),
    BYE("bye"),
    MARK("mark"),
    UNMARK("unmark"),
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event"),
    DELETE("delete"),
    UNKNOWN("unknown");

    private final String commandWord;

    Command(String commandWord) {
        this.commandWord = commandWord;
    }

    public String getCommandWord() {
        return commandWord;
    }

    public static Command fromString(String text) {
        for (Command command : Command.values()) {
            if (command.commandWord.equalsIgnoreCase(text)) {
                return command;
            }
        }
        return UNKNOWN;
    }

    public static Command parseCommand(String input) {
        String firstWord = input.split(" ")[0].toLowerCase().trim();
        return fromString(firstWord);
    }
}