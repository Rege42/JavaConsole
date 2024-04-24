package console.utility;

import console.command.Command;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CommandArgs {

    private Command command;
    private Set<String> options;
    private List<String> arguments;

    public CommandArgs(Command command, Set<String> options, List<String> arguments) {
        this.command = command;
        this.options = options;
        this.arguments = arguments;
    }
}
