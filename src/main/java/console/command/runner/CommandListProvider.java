package console.command.runner;

import console.utility.CommandType;

public class CommandListProvider {

    public static void provideCommandList() {

        CommandType[] commandTypes = CommandType.values();
        System.out.println("\nAvailable commands:");
        for (CommandType command : commandTypes) {
            System.out.println("-< " + command);
        }
        System.out.println("-< exit\n");
    }

}
