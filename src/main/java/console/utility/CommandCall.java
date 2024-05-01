package console.utility;

import console.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class CommandCall {

    private final Command command;
    private final Set<String> options;
    private final List<String> arguments;

}
