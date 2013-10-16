package jaskowski.vendingMachine.coinsStorage;

import jaskowski.vendingMachine.coinsDispenser.CoinsDispenser;
import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Money;

public class CoinsStorage {

    private Coins coins = new Coins();
    private CoinsChangerFactory coinsChangerFactory;

    public CoinsStorage(CoinsChangerFactory coinsChangerFactory) {
        this.coinsChangerFactory = coinsChangerFactory;
    }

    public void addAll(Coins coins) {
        this.coins.addAll(coins);
    }

    public boolean hasTheSameCoins(Iterable<Coin> coins) {
        return coins.equals(coins);
    }

    public void releaseChange(Money moneyToRelease, CoinsDispenser coinsDispenser) throws ChangeCannotBeReturnedException {
        Coins coinsToRelease = coinsChangerFactory.create(new Coins(coins)).findChange(moneyToRelease);

        coinsDispenser.release(coinsToRelease);
    }


    public String accept(CoinsStorageVisitor coinsStorageVisitor) {
        return coinsStorageVisitor.visitCoin(coins.toString());
    }
}
