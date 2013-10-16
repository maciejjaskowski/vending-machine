package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.money.Money;

public class ImmediatelyReleasingCoinBag extends RegularCoinBag {
    public ImmediatelyReleasingCoinBag(EnoughMoneyInserted enoughMoneyInserted) {
        super(alwaysEnoughMoney(), enoughMoneyInserted);
    }

    private static IsEnoughMoney alwaysEnoughMoney() {
        return new AbstractIsEnoughMoney() {

            @Override
            public Money lacks(Money sum) {
                return new Money(0);
            }
        };
    }
}
