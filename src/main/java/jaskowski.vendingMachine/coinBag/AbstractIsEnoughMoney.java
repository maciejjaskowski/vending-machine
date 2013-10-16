package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.money.Money;

public abstract class AbstractIsEnoughMoney implements IsEnoughMoney {

    @Override
    public boolean enough(Money money) {
        return lacks(money).lessOrEqual(new Money(0));
    }
}
