package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Price;

public class CoinBag {

    private final EnoughMoneyInserted enoughMoneyInserted;
    private final IsEnoughMoney isEnoughMoney;
    private Coins coins;

    public CoinBag(IsEnoughMoney isEnoughMoney) {
        this(isEnoughMoney, new EnoughMoneyInserted() {
            @Override public void fire(Coins coins) { }
        });
    }

    public CoinBag(IsEnoughMoney isEnoughMoney, EnoughMoneyInserted enoughMoneyInserted) {
        this.isEnoughMoney = isEnoughMoney;
        this.enoughMoneyInserted = enoughMoneyInserted;
        this.coins = new Coins();
    }

    public void putCoin(Coin coin) {
        coins.add(coin);

        if (isEnoughMoney.enough(coins.sum())) {
            enoughMoneyInserted.fire(coins);
        }
    }

    public void releaseCoins(CoinsDispenser coinsDispenser) {
        Coins tmpCoins = coins;
        coins = new Coins();
        coinsDispenser.release(tmpCoins);
    }

    public Price remainsToPay() {
        return isEnoughMoney.lacks(coins.sum()).asPrice();
    }
}
