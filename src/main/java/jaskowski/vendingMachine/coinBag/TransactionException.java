package jaskowski.vendingMachine.coinBag;

public class TransactionException extends Exception {
    public TransactionException() {
        super();
    }

    public TransactionException(Exception e) {
        super(e);
    }
}
