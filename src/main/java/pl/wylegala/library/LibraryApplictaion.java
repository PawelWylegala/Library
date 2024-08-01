package pl.wylegala.library;

import pl.wylegala.library.handlers.*;
import pl.wylegala.library.input.UserInputCommand;
import pl.wylegala.library.input.UserInputManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LibraryApplictaion {
    public static void main(String[] args) {
        new LibraryApplictaion().start();
    }

    private void start() {
        System.out.println("Library app...");

        UserInputManager userInputManager = new UserInputManager();
        boolean applicationLoop = true;

        List<CommandHandler> handlers = new ArrayList<>();
        handlers.add(new HelpCommandHendler());
        handlers.add(new QuiteCommandHandler());
        handlers.add(new CategoryCommandHandler());
        handlers.add(new BookCommandHandler());
        handlers.add(new MovieCommandHandler());


        while (applicationLoop) {
            try {
                UserInputCommand userInputCommand = userInputManager.nextCommand();
//                System.out.println(userInputCommand);

                Optional<CommandHandler> currentHandler = Optional.empty();
                for (CommandHandler handler : handlers) {
                    if (handler.supports(userInputCommand.getCommand())) {
                        currentHandler = Optional.of(handler);
                        break;
                    }
                }
                currentHandler
                        .orElseThrow(() -> new IllegalArgumentException("Unknown handler: " + userInputCommand.getCommand()))
                        .handle(userInputCommand);

            } catch (QuiteLibraryApplicationException e) {
                System.out.println("Quite...");
                applicationLoop = false;

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
