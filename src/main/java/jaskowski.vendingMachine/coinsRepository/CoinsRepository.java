package jaskowski.vendingMachine.coinsRepository;

import jaskowski.vendingMachine.coinsDispenser.CoinsDispenser;
import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Money;

public class CoinsRepository {

    private Coins coins = new Coins();
    private CoinsChangerFactory coinsChangerFactory;

    public CoinsRepository(CoinsChangerFactory coinsChangerFactory) {
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


}
