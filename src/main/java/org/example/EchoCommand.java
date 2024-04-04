package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

//TODO создать среду выполнения
public class EchoCommand implements Command{

    @Override
    public void executeCommand(HashSet<String> options, ArrayList<String> arguments) {

        if (arguments.size() == 1) {
            System.out.println(arguments.get(0));
        } else if (arguments.size() == 2) {
            echoAppend(arguments.get(0), arguments.get(1));
        } else {
            System.out.println("echo command summoned incorrectly");
        }
    }

    static public void echoAppend(String filename, String message) {

        final var file = CdCommand.getPath().resolve(filename).toFile();
        final FileWriter writer;
        try {
            writer = new FileWriter(file, true);
            writer.write(message+"\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
