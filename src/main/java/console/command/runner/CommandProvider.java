package console.command.runner;

import console.command.Command;
import console.command.cat.CatCommand;
import console.command.cd.CdCommand;
import console.command.echo.EchoCommand;
import console.command.help.HelpCommand;
import console.command.ls.LsCommand;
import console.command.prev.PrevCommand;
import console.utility.CommandType;

public class CommandProvider {

    public static Command provideCommand(String[] args) {

        CommandType commandType;
        try {
            commandType = CommandType.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
        return switch (commandType) {
            case CAT -> new CatCommand();
            case LS -> new LsCommand();
            case ECHO -> new EchoCommand();
            case HELP -> new HelpCommand();
            case CD -> new CdCommand();
            case PREV -> new PrevCommand();
        };
    }
}
