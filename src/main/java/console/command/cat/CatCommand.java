package console.command.cat;

import console.command.Command;

import java.util.List;
import java.util.Set;

public class CatCommand implements Command {

    private static CatCommand INSTANCE;

    public static CatCommand getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CatCommand();
        }
        return INSTANCE;
    }

    @Override
    public void executeCommand(Set<String> options, List<String> arguments) {

        new CatRuntime(options, arguments).execute();

    }
}
