package console.command;

import java.util.List;
import java.util.Set;

public interface Command {

    default String getName() {
        return "no name";
    }

    default String getDescription() {
        return "no description";
    }

    void executeCommand(Set<String> options, List<String> arguments);
}
