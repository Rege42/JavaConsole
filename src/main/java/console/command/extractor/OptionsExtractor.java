package console.command.extractor;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class OptionsExtractor {

    public static Set<String> extractOptions(String[] args) {

        return  Arrays.stream(args)
                .skip(1)
                .filter(arg -> arg.startsWith("-"))
                .collect(Collectors.toSet());
    }
}
