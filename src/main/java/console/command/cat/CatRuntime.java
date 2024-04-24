package console.command.cat;

import console.utility.PathResolver;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

public class CatRuntime {

    private final Set<String> options;
    private final List<String> arguments;
    private int lineCount = 0;
    private boolean writeLine = true;
    private boolean numerateOnlyNotNull = false;
    private String lastLine = "initLine";

    public CatRuntime(Set<String> options, List<String> arguments) {
        this.options = options;
        this.arguments =  arguments;
    }

    public void execute() {

        Reader reader;
        try {
            reader = getReader();
            catBuffered(reader);
        } catch (NullPointerException e) {
            System.out.println("Cannot execute cat command");
        }
    }

    private Reader getReader() {

        if (this.arguments.isEmpty()) {
            return new InputStreamReader(System.in);
        }

        final var path = new PathResolver().resolvePath(this.arguments.get(0));
        if (Files.isRegularFile(path)) {
            try {
                return new FileReader(path.toFile());
            } catch (FileNotFoundException e) {
                System.out.println("File is not readable");
            }
        }

        return null;
    }

    private void catBuffered(Reader reader) {
        try {
            final var bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {        // цикл чтения данных из файла
                catOutput(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void catOutput(String line) {

        final var finalOutputLine = buildPrefix(line) + buildLine(line) + buildPostfix();
        this.lastLine = line;

        if (this.writeLine) {
            System.out.println(finalOutputLine);
        } else {
            this.writeLine = true;
        }
    }

    private String buildPrefix(String line) {

        StringBuilder prefix = new StringBuilder();

        numerateNotNull(prefix, line);

        numerateAll(prefix, line);

        return prefix.toString();
    }

    private String buildLine(String line) {

        line = replaceTabs(line);

        excludeRepeatNull(line);

        return line;
    }

    private String buildPostfix() {

        StringBuilder postfix = new StringBuilder();

        addEndLineSymbol(postfix);

        return postfix.toString();
    }

    // -t замена табуляции на набор символов
    private String replaceTabs(String line) {

        if (this.options.contains("-t")) {
            return line.replaceAll("\t", "tab");
        }
        return line;
    }

    // -b нумерация непустых строк
    private void numerateNotNull(StringBuilder prefix, String line) {

        if (this.options.contains("-b")) {
            this.numerateOnlyNotNull = true;
            if (line.equals("")) {
                return;
            }
            this.lineCount++;
            prefix.append("\t").append(lineCount).append(" ");
        }

    }

    // -n нумерация всех строк
    private void numerateAll(StringBuilder prefix, String line) {

        if (this.options.contains("-n")) {
            if (this.numerateOnlyNotNull && line.equals("")) {
                return;
            }
            this.lineCount++;
            prefix.append("\t").append(lineCount).append(" ");
        }
    }

    // -e добавление в конец строки символа $
    private void addEndLineSymbol(StringBuilder postfix) {

        if (this.options.contains("-e")) {
            postfix.append("$");
        }
    }

    // -s удаление повторяющихся пустых строк
    private void excludeRepeatNull(String line) {

        if (this.options.contains("-s") && line.equals("") && this.lastLine.equals("")){
            this.writeLine = false;
        }
    }
}
