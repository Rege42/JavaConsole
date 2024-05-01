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

}