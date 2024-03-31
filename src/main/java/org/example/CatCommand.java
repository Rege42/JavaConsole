package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

//TODO убрать класс после изменения интерфейса
public class CatCommand implements Command {

    @Override
    public void executeCommand(HashSet<String> options, ArrayList<String> arguments) {         // значение предыдущей строки для команды -s

        new CatRunEnv(options, arguments);

    }
}

