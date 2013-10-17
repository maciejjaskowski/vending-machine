package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.money.Money;

public interface Transaction {
    void commit(Money moneyInserted) throws TransactionException;
    void rollback();
}
