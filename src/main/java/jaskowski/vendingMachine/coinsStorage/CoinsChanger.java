package jaskowski.vendingMachine.coinsStorage;

import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Money;

public interface CoinsChanger {
    Coins findChange(Money moneyToRelease) throws ChangeCannotBeReturnedException;
}
