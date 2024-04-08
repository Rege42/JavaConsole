package org.example.command;

import org.example.utility.Node;
import org.example.utility.State;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    public void lsBuildCommand(Path path, Node<String> root) {

        List<Path> files = fileExtractor(path);

        lsSort(files);

        directoryOutputTree(files, path, root);

    }

    private List<Path> fileExtractor(Path path) {

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

    private void directoryOutputTree(List<Path> files, Path path, Node<String> root) {

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

    private Node<String> constructNode(String color, Path file, Node<String> root) {
        final var filename = color + file.getFileName() + ANSI_RESET;
        final var node = new Node<>(filename);
        root.addChild(node);
        return node;
    }

    private static <T> void printTree(Node<T> node, String appender) {

        if (node.getData().equals("root")) {
            println.accept(node.getData());
        } else {
            println.accept(appender + node.getData());
        }
        node.getChildren().forEach(each ->  printTree(each, appender + appender));
    }

    private static <T> void printTreePlus(Node<T> node, String appender) {

        if (node.getData().equals("root")) {
            println.accept(node.getData());
        } else {
            println.accept(appender+"|");
            println.accept(appender+"+--"+node.getData());
        }

        if ((!node.getChildren().isEmpty())&&(!node.getData().equals("root"))) {
            appender += "|  ";
        }

        String finalAppender = appender;
        node.getChildren().forEach(each ->  printTreePlus(each, finalAppender));
    }

    @Override
    public void executeCommand(HashSet<String> options, ArrayList<String> arguments) {

        this.options = options;
        final var path = State.getInstance().getPath().resolve(arguments.isEmpty() ? "" : arguments.get(0));
        Node<String> root = new Node<>("root");

        if (!Files.isDirectory(path)) {
            System.out.println("Not a directory");
        } else {
            lsBuildCommand(path, root);

            // -+ вывод дерева файлов используя визуальное оформление
            if (this.options.contains("-+")) {
                printTreePlus(root, "");
            } else {
                printTree(root, " ");
            }
        }

    }







}
