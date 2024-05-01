package console.command.runner;

import console.utility.PathResolver;

import java.util.Scanner;

public class ArgsReader {

    static final Scanner scanner = new Scanner(System.in);

    static public String[] readInputStream() {
        System.out.print(new PathResolver().getPath() + "> ");
        return scanner.nextLine().split(" ");
    }

}
