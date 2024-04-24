package console.command;

import java.util.List;
import java.util.Set;

public interface Command {

    void executeCommand(Set<String> options, List<String> arguments);
}
