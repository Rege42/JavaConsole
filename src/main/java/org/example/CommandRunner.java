package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class CommandRunner {

    static Scanner sc = new Scanner(System.in);

    void run(String[] args) {

        String newLine = "";
        while (!newLine.equals("exit")) {
            final var commandStructure = new CommandStructure(args[0],
                    OptionsExtractor.findOptions(args),
                    ArgumentsExtractor.findArguments(args));


            final var chosenCommand = findCommand(commandStructure.getType());

            if (chosenCommand == null) {
                System.out.println("Unknown command");
            } else {
                runCommand(chosenCommand, commandStructure.getOptions(), commandStructure.getArguments());
            }

            newLine = sc.nextLine();
            args = newLine.split(" ");
        }
    }

    private Command findCommand (String commandType) {

        return switch (commandType) {
            case "cat" -> new CatCommand();
            case "ls" -> new LsCommand();
            case "echo" -> new EchoCommamd();
            default -> null;
        };
    }

    private void runCommand(Command command, ArrayList<String> options, ArrayList<String> arguments) {

        command.executeCommand(options, arguments);
    }
}

