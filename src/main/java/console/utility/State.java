package console.utility;

import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.nio.file.Paths;
public  class State {

    private static State INSTANCE;

    @Getter
    @Setter
    private Path path;

    @Getter
    @Setter
    private CommandArgs commandArgs;

    private State() {
        this.path = Paths.get(".");
    }

    public static State getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new State();
        }
        return INSTANCE;
    }

}
