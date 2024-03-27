package org.example;

import java.util.ArrayList;

public interface Command {

    void executeCommand(ArrayList<String> options, ArrayList<String> arguments);
}
