package console.command.runner;

import console.command.extractor.ArgumentsExtractor;
import console.command.extractor.OptionsExtractor;
import console.utility.CommandCall;
import console.utility.State;

public class CommandExecutor {

    public static void execute(String[] args) {

        final var commandArgs = new CommandCall(CommandProvider.provideCommand(args),
                                                OptionsExtractor.extractOptions(args),
                                                ArgumentsExtractor.extractArguments(args));

        execute(commandArgs);
    }

    public static void execute(CommandCall commandArgs) {
        try {
            commandArgs.getCommand().executeCommand(commandArgs.getOptions(), commandArgs.getArguments());
            State.getInstance().setCommandCall(commandArgs);
        } catch (NullPointerException e) {
            System.out.println("Unknown command");
        }
    }

}
