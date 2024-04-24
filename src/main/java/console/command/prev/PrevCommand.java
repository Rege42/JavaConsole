package console.command.prev;

import console.command.Command;
import console.command.runner.CommandExecutor;
import console.utility.State;

import java.util.List;
import java.util.Set;

public class PrevCommand implements Command {
    @Override
    public void executeCommand(Set<String> options, List<String> arguments) {

        final var commandArgs = State.getInstance().getCommandArgs();

        if (commandArgs.getCommand() instanceof PrevCommand) {
            System.out.println("Cannot cast prev command on prev");
            return;
        }

        CommandExecutor.execute(commandArgs);

    }
}
