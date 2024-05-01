package console.command.runner;

public class CommandRunner {

    public void run() {

        while(true) {

            String[] args = ArgsReader.readInputStream();

            if(args[0].equals("exit")) {
                break;
            }

            CommandExecutor.execute(args);
        }
    }

}

