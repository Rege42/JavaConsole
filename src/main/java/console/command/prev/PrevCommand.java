package console.command.prev;

import console.command.Command;
import console.command.runner.CommandExecutor;
import console.command.runner.CommandProvider;
import console.utility.State;

import java.util.ArrayList;
import java.util.HashSet;

public class PrevCommand implements Command {
    @Override
    public void executeCommand(HashSet<String> options, ArrayList<String> arguments) {

        final var command = State.getInstance().getCommand();
        options = (HashSet<String>) State.getInstance().getOptions();
        arguments = State.getInstance().getArguments();

        if (command.equals("prev")) {
            System.out.println("Cannot cast prev command on prev");
            return;
        }

        final var chosenCommand = CommandProvider.provideCommand(command);
        CommandExecutor.execute(chosenCommand, options, arguments);

    }
}
