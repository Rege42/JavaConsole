package console.utility;

import java.nio.file.Path;

public class PathResolver {

    private Path path;

    public PathResolver() {
        this.path = State.getInstance().getPath();
    }

    public Path resolvePath(String addToPath) {
        return path.resolve(addToPath);
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
        State.getInstance().setPath(path);
    }
}
