package jaskowski.vendingMachine.greedyCoinsChanger;

import jaskowski.vendingMachine.coinsStorage.CoinsChanger;
import jaskowski.vendingMachine.coinsStorage.CoinsChangerFactory;
import jaskowski.vendingMachine.money.Coins;

public class GreedyCoinsChangerFactory implements CoinsChangerFactory {
    @Override
    public CoinsChanger create(Coins coins) {
        return new GreedyCoinsChanger(coins);
    }
}
