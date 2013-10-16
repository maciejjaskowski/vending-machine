package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.money.Money;

public interface IsEnoughMoney {
    boolean enough(Money money);

    Money lacks(Money sum);
}
