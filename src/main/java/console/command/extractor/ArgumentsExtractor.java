package console.command.extractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ArgumentsExtractor {

    public static ArrayList<String> extractArguments(String[] args) {

        ArrayList<String> arguments = new ArrayList<>();

        final var iterator = Arrays.stream(args).skip(1).iterator();
        while (iterator.hasNext()) {
            final var elem = iterator.next();
            if (elem.startsWith("\"")) {
                arguments.add(createComplexArg(elem, iterator));
                continue;
            }
            if (!elem.startsWith("-")) {
                arguments.add(elem);
            }
        }

        return arguments;
    }

    public static String createComplexArg (String firstWord, Iterator<String> iterator) {

        final var complexArg = new StringBuilder().append(firstWord).append(" ");
        while (iterator.hasNext()) {
            final var elem = iterator.next();
            complexArg.append(elem).append(" ");
            if (elem.endsWith("\"")) {
                break;
            }
        }
        return complexArg.toString().replaceAll("\"","");
    }

}


