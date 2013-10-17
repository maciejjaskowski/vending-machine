package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.money.Money;

public interface ExpectedMoney {
    boolean isEnough(Money money);

    Money lacks(Money sum);
}
