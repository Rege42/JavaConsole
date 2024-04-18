package console.command;

import java.util.ArrayList;
import java.util.HashSet;

public interface Command {

    void executeCommand(HashSet<String> options, ArrayList<String> arguments);
}
