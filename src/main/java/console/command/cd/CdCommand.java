package console.command.cd;

import console.command.Command;
import console.utility.PathResolver;
import lombok.Getter;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class CdCommand implements Command {

    @Getter
    private final String name = "cd";

    @Getter
    private final String description = "Устанавливает корневую директорию.\n" +
            "Структура:\n" +
            "cd <путь>\n" +
            "Доступные варианты аргументов:\n" +
            "-путь к существующей директории\n" +
            "-пустой аргумент (возврат к корневой директории)";

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
