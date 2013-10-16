package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.coinsDispenser.CoinsDispenser;
import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Price;

public class RegularCoinBag implements CoinBag {

    private final EnoughMoneyInserted enoughMoneyInserted;
    private final IsEnoughMoney isEnoughMoney;
    private Coins coins;

    public RegularCoinBag(IsEnoughMoney isEnoughMoney, EnoughMoneyInserted enoughMoneyInserted) {
        this.isEnoughMoney = isEnoughMoney;
        this.enoughMoneyInserted = enoughMoneyInserted;
        this.coins = new Coins();
    }

    @Override
    public void putCoin(Coin coin) {
        coins.add(coin);

        if (isEnoughMoney.enough(coins.sum())) {
            enoughMoneyInserted.fire(coins.sum());
        }
    }

    @Override
    public void releaseCoins(CoinsDispenser coinsDispenser) {
        Coins tmpCoins = coins;
        coins = new Coins();
        coinsDispenser.release(tmpCoins);
    }

    @Override
    public Price remainsToPay() {
        return isEnoughMoney.lacks(coins.sum()).asPrice();
    }
}
