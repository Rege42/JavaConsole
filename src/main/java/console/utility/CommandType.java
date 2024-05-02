package console.utility;

import console.command.cat.CatCommand;
import console.command.cd.CdCommand;
import console.command.echo.EchoCommand;
import console.command.help.HelpCommand;
import console.command.ls.LsCommand;
import console.command.prev.PrevCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CommandType {

    CAT (CatCommand.getInstance().getName(), CatCommand.getInstance().getDescription()),
    CD (new CdCommand().getName(), new CdCommand().getDescription()),
    ECHO (new EchoCommand().getName(), new EchoCommand().getDescription()),
    HELP (new HelpCommand().getName(), new HelpCommand().getDescription()),
    LS (new LsCommand().getName(), new LsCommand().getDescription()),
    PREV (new PrevCommand().getName(), new PrevCommand().getDescription());

    private final String name;

    @Getter
    private final String description;

    @Override
    public String toString() {
        return name;
    }
}
