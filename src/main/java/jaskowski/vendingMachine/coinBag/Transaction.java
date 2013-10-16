package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.money.Money;

public class Transaction {

    private final IsEnoughMoney isEnoughMoney;
    private final EnoughMoneyInserted enoughMoneyInserted;

    public Transaction(final IsEnoughMoney isEnoughMoney, final EnoughMoneyInserted enoughMoneyInserted) {

        this.isEnoughMoney = isEnoughMoney;
        this.enoughMoneyInserted = enoughMoneyInserted;
    }

    public void tryCommit(Money money) {
        if (isEnoughMoney.enough(money)) {
            enoughMoneyInserted.fire(money);
        }
    }

    public Money lacks(Money money) {
        return isEnoughMoney.lacks(money);
    }
}
