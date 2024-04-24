package console.command.ls;

public class OutputTree {

    public static void printTree(Node tree, String type) {
        if ("-p".equals(type)) {
            treeOutputPlus(tree, "");
        } else {
            treeOutput(tree, " ");
        }
    }

    private static void treeOutput(Node node, String spacing) {
        System.out.println(spacing + node.getData());
        node.getChildren().forEach(each ->  treeOutput(each, spacing + spacing));
    }

    private static void treeOutputPlus(Node node, String spacing) {

        System.out.println(spacing+"|");
        System.out.println(spacing+"+--"+node.getData());

        if (!node.getChildren().isEmpty()) {
            spacing += "|  ";
        }

        String finalSpacing = spacing;
        node.getChildren().forEach(each ->  treeOutputPlus(each, finalSpacing));
    }

}
