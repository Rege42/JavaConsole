package console.command.ls;

import console.command.Command;
import console.utility.PathResolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class LsCommand implements Command {

    private HashSet<String> options;

    // цветовые настройки текста консоли
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    //Команда Unix ls
    public void lsBuildCommand(Path path, Node root) {

        List<Path> files = fileExtractor(path);

        lsSort(files);

        constructTree(files, path, root);

    }

    private List<Path> fileExtractor(Path path) {

        try(Stream<Path> paths = Files.list(path)) {
            return paths.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void lsSort(List<Path> files) {

        boolean reverseOrder = this.options.contains("-r");

        alphabetSort(files, reverseOrder);

        dateOfCreationSort(files, reverseOrder);

    }

    // -X сортировка по алфавиту
    private void alphabetSort(List<Path> files, boolean reverseOrder) {

        final Comparator<Path> comparator = Comparator.comparing(Path::getFileName);

        if (this.options.contains("-X") && reverseOrder) {
            files.sort(Collections.reverseOrder(comparator));   // -r обратный порядок
        } else if (this.options.contains("-X")) {
            files.sort(comparator);
        }
    }

    // -с сортировка по времени модификации
    private void dateOfCreationSort(List<Path> files, boolean reverseOrder) {

        final Comparator<Path> comparator = (o1, o2) -> {
            try {
                return Files.getLastModifiedTime(o1).compareTo(Files.getLastModifiedTime(o2));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        if (this.options.contains("-c") && reverseOrder) {
            files.sort(Collections.reverseOrder(comparator));   // -r обратный порядок
        } else if (this.options.contains("-c")) {
            files.sort(comparator);
        }
    }

    private void constructTree(List<Path> files, Path path, Node root) {

        for (Path file : files) {

            if (Files.isRegularFile(file)) {
                constructNode(ANSI_GREEN, file, root);
                continue;
            }

            if (Files.isDirectory(file)) {
                final var node = constructNode(ANSI_BLUE, file, root);

                // -R рекурсивный показ файлов в подкаталогах
                if (this.options.contains("-R")) {
                    lsBuildCommand(path.resolve(file.getFileName()), node);
                }
            }
        }
    }

    private Node constructNode(String color, Path file, Node root) {

        final var filename = color + file.getFileName() + ANSI_RESET;
        final var node = new Node(filename);
        root.addChild(node);
        return node;
    }

    @Override
    public void executeCommand(Set<String> options, List<String> arguments) {

        this.options = (HashSet<String>) options;
        final var path = new PathResolver().resolvePath(arguments.isEmpty() ? "" : arguments.get(0));
        final var root = new Node("root");

        if (!Files.isDirectory(path)) {
            System.out.println("Not a directory");
        } else {
            lsBuildCommand(path, root);

            // -p вывод дерева файлов используя визуальное оформление
            if (this.options.contains("-p")) {
                OutputTree.printTree(root, "-p");
            } else {
                OutputTree.printTree(root, "");
            }
        }

    }

}
