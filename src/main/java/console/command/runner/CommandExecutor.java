package console.command.runner;

import console.command.Command;
import console.command.extractor.ArgumentsExtractor;
import console.command.extractor.OptionsExtractor;
import console.utility.CommandArgs;
import console.utility.State;

public class CommandExecutor {

    public static void execute(String[] args) {

        final var commandArgs = new CommandArgs(CommandProvider.provideCommand(args),
                                                OptionsExtractor.extractOptions(args),
                                                ArgumentsExtractor.extractArguments(args));

        execute(commandArgs);
        
        State.getInstance().setCommandArgs(commandArgs);
    }

    public static void execute(CommandArgs commandArgs) {
        try {
            commandArgs.getCommand().executeCommand(commandArgs.getOptions(), commandArgs.getArguments());
        } catch (NullPointerException e) {
            System.out.println("Unknown command");
        }
    }

}
