package console.command.cd;

import console.command.Command;
import console.utility.PathResolver;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class CdCommand implements Command {

    @Override
    public void executeCommand(Set<String> options, List<String> arguments) {

        final var pathResolver = new PathResolver();

        if (arguments.isEmpty()) {
            pathResolver.setPath(Paths.get("."));
            return;
        }
        final var currentPath = pathResolver.resolvePath(arguments.get(0));
        if (Files.isDirectory(currentPath)) {
            pathResolver.setPath(currentPath);
        } else {
            System.out.println("Directory is not found");
        }

    }
}
