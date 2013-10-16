package jaskowski.vendingMachine.commandLine;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CommandChainWithHelp implements CommandChain {

    private final List<CommandLink> commandChain;

    public CommandChainWithHelp(List<CommandLink> commandLinks, PrintStream printStream ) {
        this.commandChain = new ArrayList<CommandLink>(commandLinks);
        this.commandChain.add(new CommandLink(Pattern.compile("help"), new HelpCommand(printStream)));
    }

    @Override
    public void interpret(String line) throws UnknownCommandException {
        for (CommandLink commandLink : commandChain) {
            if (commandLink.tryInvoke(line)) {
                return;
            }
        }
        throw new UnknownCommandException();
    }


    private class HelpCommand implements Command {
        private PrintStream stream;

        private HelpCommand(PrintStream stream) {
            this.stream = stream;
        }

        @Override
        public void command(Object param) {
            for (CommandLink commandLink : commandChain) {
                stream.println(commandLink.explain());
            }
        }
    }
}
