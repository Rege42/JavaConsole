package org.example;

import org.example.runner.CommandRunner;

public class App {

    public static void main(String[] args) {

        final var commandRunner = new CommandRunner();
        commandRunner.run(args);
    }
}

