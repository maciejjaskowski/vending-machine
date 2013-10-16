package jaskowski.vendingMachine.commandLine;

public interface Command<T> {

    void command(T param);
}
