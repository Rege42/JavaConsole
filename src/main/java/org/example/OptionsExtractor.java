package org.example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class OptionsExtractor {

    public static HashSet<String> extractOptions(String[] args) {

        return (HashSet<String>) Arrays.stream(args)
                .skip(1)
                .filter(arg -> arg.startsWith("-"))
                .collect(Collectors.toSet());
    }
}
