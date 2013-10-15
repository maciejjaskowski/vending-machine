package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.money.Money;

public class AlwaysReleasingCoinBag extends CoinBag {
    public AlwaysReleasingCoinBag(EnoughMoneyInserted enoughMoneyInserted) {
        super(new IsEnoughMoney() {
            @Override
            public boolean enough(Money money) {
                return true;
            }

            @Override
            public Money lacks(Money sum) {
                return new Money(0);
            }
        }, enoughMoneyInserted);
    }
}
