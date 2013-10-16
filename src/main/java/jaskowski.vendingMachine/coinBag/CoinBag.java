package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.coinsDispenser.CoinsDispenser;
import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Price;

public interface CoinBag {
    void putCoin(Coin coin);

    void releaseCoins(CoinsDispenser coinsDispenser);

    Price remainsToPay();
}
