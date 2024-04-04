package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TODO использовать терминальные узлы
public class LsCommand implements Command {

    private HashSet<String> options;

    // цветовые настройки текста консоли
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    static final Consumer<Object> println = System.out::println;

    private static final Comparator<Path> comparatorAO = Comparator.comparing(Path::getFileName);

    private static final Comparator<Path> comparatorLM = (o1, o2) -> {
        try {
            return Files.getLastModifiedTime(o1).compareTo(Files.getLastModifiedTime(o2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    //Команда Unix ls
    public void lsCommand(Path path, String spacing) {

        List<Path> files = fileExtractor(path);

        lsSort(files);

        directoryOutput(files, path, spacing);

    }

    private List<Path> fileExtractor (Path path) {

        try(Stream<Path> paths = Files.list(path)) {
            return paths.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void lsSort(List<Path> files) {

        // -X сортировка по алфавиту
        // -r обратный порядок
        if (this.options.contains("-X") && !this.options.contains("-r")) {
            files.sort(comparatorAO);
            return;
        } else if (this.options.contains("-X") && this.options.contains("-r")) {
            files.sort(Collections.reverseOrder(comparatorAO));
            return;
        }

        // -с сортировка по времени модификации
        // -r обратный порядок
        if (this.options.contains("-c") && !this.options.contains("-r")) {
            files.sort(comparatorLM);
        } else if (this.options.contains("-X") && this.options.contains("-r")) {
            files.sort(Collections.reverseOrder(comparatorLM));
        }
    }

    private void directoryOutput(List<Path> files, Path path, String spacing) {

        for (Path file : files) {

            if (Files.isRegularFile(file)) {
                printFile(spacing, ANSI_GREEN, file.getFileName());
                continue;
            }

            if (Files.isDirectory(file)) {
                printFile(spacing, ANSI_BLUE, file.getFileName());

                // -R рекурсивный показ файлов в подкаталогах
                if (this.options.contains("-R")) {
                    lsCommand(path.resolve(file.getFileName()), spacing + "\t");
                }
            }
        }
    }

    private void printFile (String spacing, String color, Path filename) {
        println.accept(spacing + color + filename + ANSI_RESET);
    }

    @Override
    public void executeCommand(HashSet<String> options, ArrayList<String> arguments) {

        this.options = options;
        final var path = State.getInstance().getPath().resolve(arguments.isEmpty() ? "" : arguments.get(0));

        if (Files.isDirectory(path)) {
            lsCommand(path, "");
        } else {
            System.out.println("Not a directory");
        }
    }

}
