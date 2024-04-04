package org.example;

import java.util.ArrayList;
import java.util.HashSet;

public class PrevCommand implements Command {
    @Override
    public void executeCommand(HashSet<String> options, ArrayList<String> arguments) {

        CommandRunner.executeCommandWithArgs(State.getInstance().getPrevArgs());

    }
}
