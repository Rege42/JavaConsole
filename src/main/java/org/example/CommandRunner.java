package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.function.Consumer;

public class CommandRunner {

    static final Scanner scanner = new Scanner(System.in);
    static final Consumer<Object> print = System.out::print;

    void run(String[] args) {

        do {
            executeCommandWithArgs(args);

            args = nextArgs();

        } while (!args[0].equals("exit"));
    }

    //TODO CommandProvider
    private static Command findCommand (String commandType) {

        return switch (commandType) {
            case "cat" -> new CatCommand();
            case "ls" -> new LsCommand();
            case "echo" -> new EchoCommand();
            case "cd" -> new CdCommand();
            case "prev" -> new PrevCommand();
            default -> null;
        };
    }

    private static void runCommand(Command command, HashSet<String> options, ArrayList<String> arguments) {
        try {
            command.executeCommand(options, arguments);
        } catch (NullPointerException e) {
            print.accept("Unknown command+\n");
        }
    }

    private String[] nextArgs() {
        print.accept(State.getInstance().getPath() + "> ");
        return scanner.nextLine().split(" ");
    }

    public static void executeCommandWithArgs(String[] args) {

        final var chosenCommand = findCommand(args[0]);

        runCommand(chosenCommand, OptionsExtractor.extractOptions(args), ArgumentsExtractor.findArguments(args));

        State.getInstance().setPrevArgs(args);
    }
}

