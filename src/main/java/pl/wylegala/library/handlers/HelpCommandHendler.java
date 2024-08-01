package pl.wylegala.library.handlers;

import pl.wylegala.library.input.UserInputCommand;

public class HelpCommandHendler extends BaseCommandHandler {

    public static final String COMMAND_NAME = "help";

    @Override
    public void handle(UserInputCommand command) {
        System.out.println("Help...");
        System.out.println("Allowed comands help, quite, category, book, movie");
        System.out.println("Command pattern: <command> <action> <param1> <param2>");
        System.out.println("Example: category add CategoryName");
        System.out.println("Example: movie add MovieName CategoryName");
        System.out.println("Example: movie del MovieName CategoryName");
    }


    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }
}
