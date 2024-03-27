package org.example;

import java.util.ArrayList;

public class OptionsExtractor {

    public static ArrayList<String> findOptions(String[] args) {

        ArrayList<String> options = new ArrayList<>();

        for (int i = 1; i < args.length; i++) {                     // поиск в введенной строке опций и аргументов
            if (args[i].startsWith("-")) {
                options.add(args[i]);
            }
        }

        return options;
    }
}
