package org.example;

import java.util.ArrayList;

public class CommandStructure {

    private String type;
    private ArrayList<String> options;
    private ArrayList<String> arguments;

    public CommandStructure(String type, ArrayList<String> options, ArrayList<String> arguments) {
        this.type = type;
        this.options = options;
        this.arguments = arguments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public ArrayList<String> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayList<String> arguments) {
        this.arguments = arguments;
    }

}
