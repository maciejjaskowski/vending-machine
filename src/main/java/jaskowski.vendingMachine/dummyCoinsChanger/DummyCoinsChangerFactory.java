package jaskowski.vendingMachine.dummyCoinsChanger;

import jaskowski.vendingMachine.coinsStorage.CoinsChanger;
import jaskowski.vendingMachine.coinsStorage.CoinsChangerFactory;
import jaskowski.vendingMachine.money.Coins;

public class DummyCoinsChangerFactory implements CoinsChangerFactory {
    @Override
    public CoinsChanger create(Coins coins) {
        return new DummyCoinsChanger(coins);
    }
}
