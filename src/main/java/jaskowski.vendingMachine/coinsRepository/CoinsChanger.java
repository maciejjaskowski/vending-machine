package jaskowski.vendingMachine.coinsRepository;

import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Money;

public interface CoinsChanger {
    Coins findChange(Money moneyToRelease) throws ChangeCannotBeReturnedException;
}
