package org.example;

import java.util.ArrayList;

public class ArgumentsExtractor {

    public static int pos;

    public static ArrayList<String> findArguments(String[] args) {

        ArrayList<String> arguments = new ArrayList<>();

        for (pos = 1; pos < args.length; pos++) {

            if (!args[pos].startsWith("-")) {
                arguments.add(args[pos]);
                continue;
            }
            if (args[pos].startsWith("\"")) {
                arguments.add(createComplexArg(args));
                if (pos >= args.length) {
                    break;
                }
            }
        }
        return arguments;
    }

    public static String createComplexArg (String[] args) {

        final var complexArg = new StringBuilder();
        while (!args[pos].endsWith("\"")) {
            complexArg.append(args[pos]).append(" ");
            pos++;
        }
        complexArg.append(args[pos]);
        pos++;
        return complexArg.toString().replaceAll("\"","");
    }
}


