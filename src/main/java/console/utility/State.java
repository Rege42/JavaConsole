package console.utility;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public  class State {

    private static State INSTANCE;

    @Getter
    @Setter
    private Path path;
    @Getter
    @Setter
    private String command;
    @Getter
    @Setter
    private ArrayList<String> arguments;
    @Getter
    @Setter
    private Set<String> options;

    private State() {
        this.path = Paths.get(".");
        this.arguments = new ArrayList<>();
        this.options = new HashSet<>();
    }

    public static State getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new State();
        }
        return INSTANCE;
    }

}
