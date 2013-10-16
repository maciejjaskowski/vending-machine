package jaskowski.vendingMachine.greedyCoinsChanger;

import jaskowski.vendingMachine.coinsStorage.ChangeCannotBeReturnedException;
import jaskowski.vendingMachine.coinsStorage.CoinsChanger;
import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Money;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class GreedyCoinsChanger implements CoinsChanger {
    private final List<Coin> availableCoins;
    private final Coins coinsToRelease;

    public GreedyCoinsChanger(Coins coins) {
        this.availableCoins = newArrayList(coins);
        this.coinsToRelease = new Coins();
    }

    @Override
    public Coins findChange(Money moneyToRelease) throws ChangeCannotBeReturnedException {
        if (new Money(0).less(moneyToRelease)) {
            Coin newCoinToRelease = coinNoBiggerThen(moneyToRelease);
            availableCoins.remove(newCoinToRelease);
            coinsToRelease.add(newCoinToRelease);
            findChange(moneyToRelease.minus(newCoinToRelease.asMoney()));
        }
        return coinsToRelease;
    }

    private Coin coinNoBiggerThen(Money money) throws ChangeCannotBeReturnedException {
        Coin biggestCoin = null;
        for (Coin coin : availableCoins) {
            Money coinValue = coin.asMoney();
            if (coinValue.lessOrEqual(money) && (biggestCoin == null || (biggestCoin.asMoney().less(coinValue)))) {
                biggestCoin = coin;
            }
        }
        if (biggestCoin == null) {
            throw new ChangeCannotBeReturnedException();
        }
        return biggestCoin;
    }
}

