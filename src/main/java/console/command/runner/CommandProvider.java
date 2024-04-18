package console.command.runner;

import console.command.Command;
import console.command.cat.CatCommand;
import console.command.cd.CdCommand;
import console.command.echo.EchoCommand;
import console.command.ls.LsCommand;
import console.command.prev.PrevCommand;

public class CommandProvider {

    public static Command provideCommand (String commandType) {

        return switch (commandType) {
            case "cat" -> new CatCommand();
            case "ls" -> new LsCommand();
            case "echo" -> new EchoCommand();
            case "cd" -> new CdCommand();
            case "prev" -> new PrevCommand();
            default -> null;
        };
    }
}
