package console.command.ls.printer;

import console.command.ls.Node;
import console.command.ls.TreePrinter;

public class LineTreePrinter implements TreePrinter {

    @Override
    public void printTree(Node node, String spacing) {

        System.out.println(spacing+"|");
        System.out.println(spacing+"+--"+node.getData());

        if (!node.getChildren().isEmpty()) {
            spacing += "|  ";
        }

        String finalSpacing = spacing;
        node.getChildren().forEach(each ->  printTree(each, finalSpacing));
    }
}
