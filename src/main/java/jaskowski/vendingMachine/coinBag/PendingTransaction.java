package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.money.Money;

public class PendingTransaction {

    private final ExpectedMoney expectedMoney;
    private final Transaction transaction;

    public PendingTransaction(final ExpectedMoney expectedMoney, final Transaction transaction) {

        this.expectedMoney = expectedMoney;
        this.transaction = transaction;
    }

    public void tryCommit(Money money) {
        if (expectedMoney.isEnough(money)) {
            try {
                transaction.commit(money);
            } catch (TransactionException ignore) {
                transaction.rollback();
            }
        }
    }

    public Money lacks(Money money) {
        return expectedMoney.lacks(money);
    }
}
