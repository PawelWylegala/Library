package pl.wylegala.library.handlers;

import pl.wylegala.library.input.UserInputCommand;

public class QuiteCommandHandler extends BaseCommandHandler {

    public static final String COMMAND_NAME = "quite";


    @Override
    protected String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void handle(UserInputCommand command) {
        throw new QuiteLibraryApplicationException();
    }
}
