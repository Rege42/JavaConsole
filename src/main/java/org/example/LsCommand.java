package org.example;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

//TODO создать среду выполнения
public class LsCommand implements Command {

    // цветовые настройки текста консоли
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    private static final Comparator<Path> comparatorAO = Comparator.comparing(Path::getFileName);

    private static final Comparator<Path> comparatorLM = (o1, o2) -> {
        try {
            return Files.getLastModifiedTime(o1).compareTo(Files.getLastModifiedTime(o2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    //Команда Unix ls
    public static void lsCommand(HashSet<String> options, Path path, String spacing) {

        List<Path> files = new ArrayList<>();
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
            for(Path file : directoryStream) {
                files.add(file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        lsSort(options, files);

        directoryOutput(options, files, path, spacing);

    }

    private static void lsSort(HashSet<String> options, List<Path> files) {

        // -X сортировка по алфавиту
        // -r обратный порядок
        if (options.contains("-X") && !options.contains("-r")) {
            files.sort(comparatorAO);
        } else if (options.contains("-X") && options.contains("-r")) {
            files.sort(Collections.reverseOrder(comparatorAO));
        }

        // -с сортировка по времени модификации
        // -r обратный порядок
        if (options.contains("-c") && !options.contains("-r")) {
            files.sort(comparatorLM);
        } else if (options.contains("-X") && options.contains("-r")) {
            files.sort(Collections.reverseOrder(comparatorLM));
        }
    }

    public static void directoryOutput(HashSet<String> options, List<Path> files, Path path, String spacing) {

        for (Path file : files) {
            if (Files.isDirectory(file)) {
                System.out.println(spacing + ANSI_BLUE + file.getFileName() + ANSI_RESET);
                // -R рекурсивный показ файлов в подкаталогах
                if (options.contains("-R")) {
                    lsCommand(options, path.resolve(file.getFileName()), spacing + "\t");
                }
            } else {
                System.out.println(spacing + ANSI_GREEN + file.getFileName() + ANSI_RESET);
            }
        }
    }

    @Override
    public void executeCommand(HashSet<String> options, ArrayList<String> arguments) {
        final var path = CdCommand.getPath().resolve(arguments.isEmpty() ? "" : arguments.get(0));
        lsCommand(options, path, "");
    }
}
