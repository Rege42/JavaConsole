package org.example;

import java.util.HashSet;

public class OptionsExtractor {

    public static HashSet<String> extractOptions(String[] args) {

        HashSet<String> options = new HashSet<>();

        for (int i = 1; i < args.length; i++) {                     // поиск в введенной строке опций и аргументов
            if (args[i].startsWith("-")) {
                options.add(args[i]);
            }
        }

        return options;
    }
}
