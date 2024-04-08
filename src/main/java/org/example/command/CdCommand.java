package org.example.command;

import org.example.utility.State;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;

public class CdCommand implements Command {

    @Override
    public void executeCommand(HashSet<String> options, ArrayList<String> arguments) {

        if (arguments.isEmpty()) {
            State.getInstance().setPath(Paths.get("."));
            return;
        }
        final var currentPath = State.getInstance().getPath().resolve(arguments.get(0));
        if (Files.isDirectory(currentPath)) {
            State.getInstance().setPath(currentPath);
        } else {
            System.out.println("Directory is not found");
        }

    }
}
