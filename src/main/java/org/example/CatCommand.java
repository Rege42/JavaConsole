package org.example;

import java.util.ArrayList;
import java.util.HashSet;

//TODO убрать класс после изменения интерфейса
public class CatCommand implements Command {

    @Override
    public void executeCommand(HashSet<String> options, ArrayList<String> arguments) {         // значение предыдущей строки для команды -s

        new CatRunEnv(options, arguments);

    }
}

