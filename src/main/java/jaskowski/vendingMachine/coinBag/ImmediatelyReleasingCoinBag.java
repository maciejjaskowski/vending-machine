package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.money.Money;
import jaskowski.vendingMachine.money.Price;

public class ImmediatelyReleasingCoinBag extends RegularCoinBag {
    public ImmediatelyReleasingCoinBag(Transaction transaction) {
        super(fakeDisplay(), new PendingTransaction(alwaysEnoughMoney(), transaction));
    }

    private static RemainsToPayDisplay fakeDisplay() {
        return new RemainsToPayDisplay() {
            @Override
            public void remainsToPay(Price price) {}
        };
    }

    private static ExpectedMoney alwaysEnoughMoney() {
        return new AbstractExpectedMoney() {

            @Override
            public Money lacks(Money sum) {
                return new Money(0);
            }
        };
    }
}
