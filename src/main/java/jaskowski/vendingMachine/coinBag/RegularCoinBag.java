package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.coinsDispenser.CoinsDispenser;
import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Price;

public class RegularCoinBag implements CoinBag {

    private final RemainsToPayDisplay display;
    private final PendingTransaction pendingTransaction;
    private Coins coins;

    public RegularCoinBag(RemainsToPayDisplay display, final PendingTransaction pendingTransaction) {
        this.display = display;
        this.pendingTransaction = pendingTransaction;
        this.coins = new Coins();
        display.remainsToPay(remainsToPay());
    }

    @Override
    public void putCoin(Coin coin) {
        coins.add(coin);
        display.remainsToPay(remainsToPay());
        pendingTransaction.tryCommit(coins.sum());
    }

    @Override
    public void releaseCoins(CoinsDispenser coinsDispenser) {
        Coins tmpCoins = coins;
        coins = new Coins();
        coinsDispenser.release(tmpCoins);
    }


    private Price remainsToPay() {
        return pendingTransaction.lacks(coins.sum()).asPrice();
    }
}
