package org.example.command;

import org.example.utility.State;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;

public class CatCommand implements Command {

    private StringBuilder prefix = new StringBuilder();
    private String line;
    private StringBuilder postfix = new StringBuilder();

    private ArrayList<String> arguments;
    private HashSet<String> options;
    private int lineCount = 0;
    private boolean writeLine = true;
    private String lastLine = "initLine";

    private void catCommand () {

        if (arguments.isEmpty()) {             // если файл не предоставлен, то данные читаются с потока ввода
            this.catBuffered(new InputStreamReader(System.in));
            return;
        }

        final var path = State.getInstance().getPath().resolve(arguments.get(0));

        if (!Files.isRegularFile(path)) {
            System.out.println("File is not readable");
            return;
        }

        final var file = path.toFile();

        try {
            this.catBuffered(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void catBuffered (Reader reader) {
        try {
            final var bufferedReader = new BufferedReader(reader);
            while ((this.line = bufferedReader.readLine()) != null) {        // цикл чтения данных из файла
                this.lastLine = this.line;
                this.catOptions();
                this.catOutput();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void catOptions() {

        // -t замена табуляцию на набор символов
        if (options.contains("-t")) {
            this.replaceTabs();
        }

        // -b нумерация непустых строк
        // -n нумерация всех строк
        if (((options.contains("-b")) && (!line.equals(""))) ||
                ((options.contains("-n")) && (!options.contains("-b")))) {
            this.numerate();
        }

        // -e добавление в конец строки символа $
        if (options.contains("-e")) {
            this.addEndLineSymbol();
        }

        // -s удаление повторяющихся пустых строк
        if (options.contains("-s") && (this.line.equals("")) && (this.lastLine.equals(""))) {
            this.writeLine = false;
        }
    }

    private void replaceTabs() {
        this.line = this.line.replaceAll("\t", "tab");
    }

    private void numerate() {
        this.lineCount++;
        prefix.append("\t").append(lineCount).append(" ");
    }

    private void addEndLineSymbol() {
        this.postfix.append("$");
    }

    private void catOutput() {

        if (this.writeLine) {
            System.out.println(this.prefix+this.line+this.postfix);
        } else {
            this.writeLine = true;
        }

        this.prefix = new StringBuilder();
        this.postfix = new StringBuilder();
    }

    @Override
    public void executeCommand(HashSet<String> options, ArrayList<String> arguments) {

        this.arguments = arguments;
        this.options = options;
        catCommand();
    }
}
