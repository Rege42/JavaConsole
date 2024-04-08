package org.example.utility;

import java.nio.file.Path;
import java.nio.file.Paths;

public  class State {

    private static State INSTANCE;
    private Path path;
    String[] prevArgs;

    private State() {
        this.path = Paths.get(".");
        this.prevArgs = null;
    }

    public static State getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new State();
        }
        return INSTANCE;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void setPrevArgs(String[] prevArgs) {
        this.prevArgs = prevArgs;
    }

    public Path getPath() {
        return path;
    }

    public String[] getPrevArgs() {
        return prevArgs;
    }
}
