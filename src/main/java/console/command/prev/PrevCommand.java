package console.command.prev;

import console.command.Command;
import console.command.runner.CommandExecutor;
import console.utility.State;
import lombok.Getter;

import java.util.List;
import java.util.Set;

public class PrevCommand implements Command {

    @Getter
    private final String name = "prev";

    @Getter
    private final String description = "Выполняет предыдущую команду. Команда prev не может быть вызвана как предыдущая.\n" +
            "Структура:\n" +
            "prev";

    @Override
    public void executeCommand(Set<String> options, List<String> arguments) {

        final var commandArgs = State.getInstance().getCommandCall();

        if (commandArgs.getCommand() instanceof PrevCommand) {
            System.out.println("Cannot cast prev command on prev");
            return;
        }

        CommandExecutor.execute(commandArgs);

    }
}
