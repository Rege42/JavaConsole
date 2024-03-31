package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class CatRunEnv{

    private StringBuilder prefix = new StringBuilder();
    private String line;
    private StringBuilder postfix = new StringBuilder();

    ArrayList<String> arguments;
    HashSet<String> options;
    private int lineCount = 0;
    private boolean writeLine = true;
    private String lastLine = "initLine";

    public CatRunEnv(HashSet<String> options, ArrayList<String> arguments) {

        this.arguments = arguments;
        this.options = options;

        if (arguments.isEmpty()) {             // если файл не предоставлен, то данные читаются с потока ввода
            this.catEmpty();
        } else {
            this.catFull();
        }

    }

    private void catEmpty() {

        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {                              // цикл обеспечиваеь конец работы при нажатии Ctrl + D
            this.line = sc.nextLine();
            this.catOptions();
            this.lastLine = this.line;
            this.catOutput();
        }
    }

    public void catFull() {

        final var path = CdCommand.getPath().resolve(arguments.get(0));

        if (!Files.isRegularFile(path)) {
           System.out.println("File is not readable");
        } else {
            final var file = path.toFile();
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                while ((this.line = bufferedReader.readLine()) != null) {        // цикл чтения данных из файла
                    this.catOptions();
                    this.lastLine = this.line;
                    this.catOutput();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void catOptions() {

        // -t замена табуляцию на набор символов
        if (options.contains("-t")) {
            this.replaceTabs();
        }

        // -b нумерация непустых строк
        if ((options.contains("-b")) && (!line.equals(""))) {
            this.numerate();
        }

        // -n нумерация всех строк
        if ((options.contains("-n")) && (!options.contains("-b"))) {
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

}
