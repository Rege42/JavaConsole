package console.command.ls.printer;

import console.command.ls.Node;
import console.command.ls.TreePrinter;

public class BasicTreePrinter implements TreePrinter {

    @Override
    public void printTree(Node node, String spacing) {
        System.out.println(spacing + node.getData());
        node.getChildren().forEach(each ->  printTree(each, spacing + " "));
    }
}
