package console.command.cat;

import java.util.HashSet;
import java.util.Set;

public class CatRuntime {

    private static CatRuntime INSTANCE;

    private Set<String> options;
    private int lineCount = 0;
    private boolean writeLine;
    private boolean numerateOnlyNotNull;
    private String lastLine;
    private CatRuntime() {

        this.options = new HashSet<>();
        this.writeLine = true;
        this.lastLine = "initLine";
        this.numerateOnlyNotNull = false;
    }

    public static CatRuntime getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CatRuntime();
        }
        return INSTANCE;
    }

    public void prepareNewLine(HashSet<String> options, String line) {
        this.options = options;
        catOutput(line);
        new CatRuntime();
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

        // -b нумерация непустых строк
        if (this.options.contains("-b")) {
            numerate(prefix, line, true);
        }
        // -n нумерация всех строк
        if (this.options.contains("-n")) {
            numerate(prefix, line, this.numerateOnlyNotNull);
        }

        return prefix.toString();
    }

    private String buildLine(String line) {

        // -t замена табуляции на набор символов
        if (this.options.contains("-b")) {
            line = replaceTabs(line);
        }
        // -s удаление повторяющихся пустых строк
        if (this.options.contains("-b")) {
            excludeRepeatNull(line);
        }

        return line;
    }

    private String buildPostfix() {

        StringBuilder postfix = new StringBuilder();

        // -e добавление в конец строки символа $
        if (this.options.contains("-e")) {
            addEndLineSymbol(postfix);
        }

        return postfix.toString();
    }

    private String replaceTabs(String line) {
        return line.replaceAll("\t", "tab");
    }

    private void numerate(StringBuilder prefix, String line, boolean notNull) {

        this.numerateOnlyNotNull = notNull;
        if (this.numerateOnlyNotNull && line.equals("")) {
            return;
        }
        this.lineCount++;
        prefix.append("\t").append(lineCount).append(" ");
    }

    private void addEndLineSymbol(StringBuilder postfix) {
        postfix.append("$");
    }

    private void excludeRepeatNull(String line) {
        if (line.equals("") && this.lastLine.equals("")) {
            this.writeLine = false;
        }
    }
}
