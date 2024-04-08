package org.example.utility;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private String data;

    private final List<Node> children = new ArrayList<>();

    private Node parent = null;

    public Node(String data) {
        this.data = data;
    }

    public void addChild(Node child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void addChildren(List<Node> children) {
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    public List<Node> getChildren() {
        return children;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getParent() {
        return parent;
    }

}