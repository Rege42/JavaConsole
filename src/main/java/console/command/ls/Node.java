package console.command.ls;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Node {

    private final String data;
    private final List<Node> children;

    public Node(String data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void printTree(String type) {
        if ("-p".equals(type)) {
            treeOutputPlus(this, "");
        } else {
            treeOutput(this, " ");
        }
    }

    private void treeOutput(Node node, String spacing) {
        System.out.println(spacing + node.getData());
        node.getChildren().forEach(each ->  treeOutput(each, spacing + spacing));
    }

    private void treeOutputPlus(Node node, String spacing) {

        System.out.println(spacing+"|");
        System.out.println(spacing+"+--"+node.getData());

        if (!node.getChildren().isEmpty()) {
            spacing += "|  ";
        }

        String finalSpacing = spacing;
        node.getChildren().forEach(each ->  treeOutputPlus(each, finalSpacing));
    }

}