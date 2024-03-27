package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class LsCommand implements Command {

    // цветовые настройки текста консоли
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";

    //Команда Unix ls
    public static void lsCommand(ArrayList<String> options, String path, String spacing) {

        File folder = new File(path);

        File[] listOfFiles = folder.listFiles();                // извлечение файлов из каталога
        if (listOfFiles == null) {
            System.out.println(path);
            return;
        } else {
            directoryOutput(options, listOfFiles, path, spacing);
        }
    }

    public static void directoryOutput(ArrayList<String> options, File[] listOfFiles, String path, String spacing) {

        // -Х сортировка по алфавиту
        // -r сортировка в обратном порядке
        if ((options.contains("-X")) && (!options.contains("-r"))) {
            Arrays.sort(listOfFiles);
        } else if ((options.contains("-X")) && (options.contains("-r"))) {
            Arrays.sort(listOfFiles, Collections.<File>reverseOrder());
        }

        for (File listOfFile : listOfFiles) {
            if (listOfFile.isDirectory() && !listOfFile.isHidden()) {
                System.out.println(spacing + ANSI_BLUE + listOfFile.getName() + ANSI_RESET);
                // -R рекурсивный показ файлов в подкаталогах
                if (options.contains("-R")) {
                    lsCommand(options, path + "/" + listOfFile.getName(), spacing + "\t");
                }
            } else if (listOfFile.isFile() && !listOfFile.isHidden()) {
                System.out.println(spacing + ANSI_GREEN + listOfFile.getName() + ANSI_RESET);
            }
        }
    }

    @Override
    public void executeCommand(ArrayList<String> options, ArrayList<String> arguments) {
        String spacing = "";
        String path;
        if (arguments.isEmpty()) {
            path = ".";
        } else {
            path = arguments.get(0);
        }
            lsCommand(options, path, spacing);
    }
}
