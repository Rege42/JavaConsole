package console.command.cat;

import console.command.Command;
import console.utility.PathResolver;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;

public class CatCommand implements Command {

    private void catBuffered(HashSet<String> options, Reader reader) {
        try {
            final var bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {        // цикл чтения данных из файла
                CatRuntime.getInstance().prepareNewLine(options, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Reader getReader(ArrayList<String> arguments) {

        if (arguments.isEmpty()) {
            return new InputStreamReader(System.in);
        }

        final var path = new PathResolver().resolvePath(arguments.get(0));
        if (Files.isRegularFile(path)) {
            try {
                return new FileReader(path.toFile());
            } catch (FileNotFoundException e) {
                System.out.println("File is not readable");
            }
        }

        return null;
    }

    @Override
    public void executeCommand(HashSet<String> options, ArrayList<String> arguments) {

        Reader reader = null;
        try {
            reader = getReader(arguments);
        } catch (NullPointerException e) {
            System.out.println("Cannot execute cat command");
        }

        catBuffered(options, reader);

    }
}
