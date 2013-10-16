package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.money.Money;

public interface EnoughMoneyInserted {
    void fire(Money moneyInserted);
}
