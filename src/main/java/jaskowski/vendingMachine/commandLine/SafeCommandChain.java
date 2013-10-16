package jaskowski.vendingMachine.commandLine;


public class SafeCommandChain implements CommandChain {


    private CommandChain commandChain;

    public SafeCommandChain(CommandChain commandChain) {
        this.commandChain = commandChain;
    }

    @Override
    public void interpret(String line) {
        try {
            commandChain.interpret(line);
        } catch (UnknownCommandException e) {
            System.out.println("Unknown command: " + line);
        }
    }
}
