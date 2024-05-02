package console.command.help;

import console.command.Command;
import console.utility.CommandType;
import lombok.Getter;

import java.util.List;
import java.util.Set;

public class HelpCommand implements Command {

    @Getter
    private final String name = "help";

    @Getter
    private final String description = "Выводит информацию о командах." +
            "Структура:\n" +
            "help";

    @Override
    public void executeCommand(Set<String> options, List<String> arguments) {

        CommandType[] commandTypes = CommandType.values();
        for (CommandType command : commandTypes) {
            System.out.println("\n-----[ " + command + " ]");
            System.out.println(command.getDescription());
            System.out.println("-----\n");
        }
        System.out.println("\n-----[ exit ]");
        System.out.println("Завершает работу приложения");
        System.out.println("-----\n");
    }
}
