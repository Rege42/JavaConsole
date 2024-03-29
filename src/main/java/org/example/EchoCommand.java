package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EchoCommand implements Command{

    @Override
    public void executeCommand(ArrayList<String> options, ArrayList<String> arguments) {

        if (arguments.size() == 1) {
            System.out.println(arguments.get(0));
        } else if (arguments.size() == 2) {
            echoAppend(arguments.get(0), arguments.get(1));
        } else {
            System.out.println("echo command summoned incorrectly");
        }
    }

    static public void echoAppend(String filename, String message) {

        final var file = new File(filename);
        final FileWriter writer;
        try {
            writer = new FileWriter(filename, true);
            writer.write(message+"\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
