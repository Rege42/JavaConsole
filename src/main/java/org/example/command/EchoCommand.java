package org.example.command;

import org.example.utility.State;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Consumer;

public class EchoCommand implements Command {

    static final Consumer<Object> println = System.out::println;

    public void echoAppend(String filename, String message) {

        final var file = State.getInstance().getPath().resolve(filename).toFile();
        final FileWriter writer;
        try {
            writer = new FileWriter(file, true);
            writer.write(message+"\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void executeCommand(HashSet<String> options, ArrayList<String> arguments) {

        if (arguments.size() == 1) {
            println.accept(arguments.get(0));
            return;
        } else if (arguments.size() == 2) {
            echoAppend(arguments.get(0), arguments.get(1));
            return;
        }

        println.accept("echo command summoned incorrectly");

    }
}
