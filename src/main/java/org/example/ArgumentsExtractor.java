package org.example;

import java.util.ArrayList;

public class ArgumentsExtractor {

    public static ArrayList<String> findArguments(String[] args) {

        ArrayList<String> arguments = new ArrayList<>();
        final var complexArg = new StringBuilder();

        for (int i = 1; i < args.length; i++) {                     // поиск в введенной строке опций и аргументов
            if (args[i].startsWith("\"")) {
                while (!args[i].endsWith("\"")) {
                    complexArg.append(args[i]).append(" ");
                    i++;
                }
                complexArg.append(args[i]);
                String bufferStr = complexArg.toString();
                bufferStr = bufferStr.replaceAll("\"","");
                arguments.add(bufferStr);
                i++;
                if (i >= args.length) {
                    break;
                }
            }
            if (!args[i].startsWith("-")) {
                arguments.add(args[i]);
            }
        }

        return arguments;
    }

    //public static String createComplexArg ()
}


