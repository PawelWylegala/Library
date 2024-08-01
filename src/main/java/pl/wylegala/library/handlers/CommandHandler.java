package pl.wylegala.library.handlers;

import pl.wylegala.library.input.UserInputCommand;

public interface CommandHandler {
    void handle(UserInputCommand command);

    boolean supports(String name);
}
