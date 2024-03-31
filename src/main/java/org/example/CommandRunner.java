package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class CommandRunner {

    static Scanner sc = new Scanner(System.in);

    void run(String[] args) {

        do {
            final var chosenCommand = findCommand(args[0]);

            try {
                runCommand(chosenCommand, OptionsExtractor.extractOptions(args), ArgumentsExtractor.findArguments(args));
            } catch (NullPointerException e) {
                System.out.println("Unknown command");
            }

            System.out.print(CdCommand.getPath() + "> ");
            args = sc.nextLine().split(" ");
        } while (!args[0].equals("exit"));
    }

    //TODO абстрактный интерфейс?
    private Command findCommand (String commandType) {

        return switch (commandType) {
            case "cat" -> new CatCommand();
            case "ls" -> new LsCommand();
            case "echo" -> new EchoCommand();
            case "cd" -> new CdCommand();
            default -> null;
        };
    }


    private void runCommand(Command command, HashSet<String> options, ArrayList<String> arguments) {

        command.executeCommand(options, arguments);
    }
}

