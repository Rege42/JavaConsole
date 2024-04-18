package console.command.runner;

import console.command.Command;
import console.command.extractor.ArgumentsExtractor;
import console.command.extractor.OptionsExtractor;
import console.utility.State;

import java.util.ArrayList;
import java.util.HashSet;

public class CommandExecutor {

    public static void execute(String[] args) {

        final var chosenCommand = CommandProvider.provideCommand(args[0]);
        final var options = (HashSet<String>) OptionsExtractor.extractOptions(args);
        final var arguments = ArgumentsExtractor.extractArguments(args);

        execute(chosenCommand, options, arguments);

        State.getInstance().setCommand(args[0]);
        State.getInstance().setOptions(options);
        State.getInstance().setArguments(arguments);
    }

    public static void execute(Command command, HashSet<String> options, ArrayList<String> arguments) {
        try {
            command.executeCommand(options, arguments);
        } catch (NullPointerException e) {
            System.out.println("Unknown command");
        }
    }

}
