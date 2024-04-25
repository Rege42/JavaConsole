package console.command.ls;

import console.command.Command;
import console.command.ls.printer.BasicTreePrinter;
import console.command.ls.printer.LineTreePrinter;
import console.utility.PathResolver;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class LsCommand implements Command {

    private Set<String> options;

    // цветовые настройки текста консоли
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public Node lsBuildCommand(Path path) {

        final var root = new Node("root");

        // -R рекурсивный показ файлов в подкаталогах
        constructTree(path, root, this.options.contains("-R"));

        Comparator<Node> nodeComparator = getComparator();
        if (nodeComparator != null) {
            sortTree(root, nodeComparator);
        }

        return root;
    }

    private void sortTree(Node node, Comparator<Node> nodeComparator) {

        node.getChildren().sort(nodeComparator);
        node.getChildren().forEach(each -> sortTree(each, nodeComparator));
    }

    private Comparator<Node> getComparator() {

        Comparator<Node> nodeComparator = null;

        if (this.options.contains("-X")) {
            nodeComparator = Comparator.comparing(Node::getData);
        }

        if (this.options.contains("-r")) {
            return Collections.reverseOrder(nodeComparator);
        }

        return nodeComparator;
    }

    private void constructTree(Path path, Node root, boolean recursion) {

        try (DirectoryStream<Path> dirPaths = Files.newDirectoryStream(path)) {
            for (Path file : dirPaths) {

                if (Files.isRegularFile(file)) {
                    constructNode(ANSI_GREEN, file, root);
                    continue;
                }

                final var node = constructNode(ANSI_BLUE, file, root);

                if (recursion) {
                    constructTree(path.resolve(file.getFileName()), node, true);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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

        this.options = options;
        final var path = new PathResolver().resolvePath(arguments.isEmpty() ? "" : arguments.get(0));

        if (!Files.isDirectory(path)) {
            System.out.println("Not a directory");
        } else {
            final var root = lsBuildCommand(path);

            // -p вывод дерева файлов используя визуальное оформление
            if (this.options.contains("-p")) {
                new LineTreePrinter().printTree(root, "");
                return;
            }
            new BasicTreePrinter().printTree(root, " ");
        }

    }

}
