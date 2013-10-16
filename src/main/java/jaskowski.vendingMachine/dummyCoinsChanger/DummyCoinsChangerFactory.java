package jaskowski.vendingMachine.dummyCoinsChanger;

import jaskowski.vendingMachine.coinsRepository.CoinsChanger;
import jaskowski.vendingMachine.coinsRepository.CoinsChangerFactory;
import jaskowski.vendingMachine.money.Coins;

public class DummyCoinsChangerFactory implements CoinsChangerFactory {
    @Override
    public CoinsChanger create(Coins coins) {
        return new DummyCoinsChanger(coins);
    }
}
