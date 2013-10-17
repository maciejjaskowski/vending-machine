package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.money.Money;

public abstract class AbstractExpectedMoney implements ExpectedMoney {

    @Override
    public boolean isEnough(Money money) {
        return lacks(money).lessOrEqual(new Money(0));
    }
}
