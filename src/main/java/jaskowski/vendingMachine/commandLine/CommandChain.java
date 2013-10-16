package jaskowski.vendingMachine.commandLine;

/**
 * Created with IntelliJ IDEA.
 * User: pragmatists
 * Date: 10/16/13
 * Time: 7:09 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CommandChain {
    void interpret(String line) throws UnknownCommandException;
}
