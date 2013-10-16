package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.money.Money;
import jaskowski.vendingMachine.money.Price;

public class ImmediatelyReleasingCoinBag extends RegularCoinBag {
    public ImmediatelyReleasingCoinBag(EnoughMoneyInserted enoughMoneyInserted) {
        super(fakeDisplay(), new Transaction(alwaysEnoughMoney(), enoughMoneyInserted));
    }

    private static RemainsToPayDisplay fakeDisplay() {
        return new RemainsToPayDisplay() {
            @Override
            public void remainsToPay(Price price) {}
        };
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
