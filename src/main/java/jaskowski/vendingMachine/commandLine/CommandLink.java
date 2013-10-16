package jaskowski.vendingMachine.commandLine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLink {
    private final Pattern pattern;
    private final Command command;
    private final String explanation;

    public CommandLink(Pattern pattern, Command command) {
        this(pattern, command, pattern.toString());
    }

    public CommandLink(Pattern pattern, Command command, String explanation) {
        this.pattern = pattern;
        this.command = command;
        this.explanation = explanation;
    }

    public boolean tryInvoke(String line) {
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            if (matcher.groupCount() == 0) {
                command.command(null);
                return true;
            }
            command.command(matcher.group(1));
            return true;
        }
        return false;
    }

    public String explain() {
        return explanation;
    }
}
