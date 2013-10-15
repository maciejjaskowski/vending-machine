package jaskowski.vendingMachine.money;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import static java.util.Arrays.asList;

public class Coins implements Iterable<Coin> {

    private Collection<Coin> coins;

    public Coins(Collection<Coin> coins) {
        this.coins = new ArrayList<Coin>(coins);
    }

    public Coins(Coin ...coins) {
        this(asList(coins));
    }

    public void add(Coin coin) {
        coins.add(coin);
    }

    public Iterator<Coin> iterator() {
        return new ArrayList<Coin>(coins).iterator();
    }

    public Money sum() {
        Money sum = new Money(0);
        for (Coin coin : this) {
            sum = sum.add(coin.asMoney());
        }
        return sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coins coins1 = (Coins) o;

        if (!coins.equals(coins1.coins)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return coins.hashCode();
    }

    @Override
    public String toString() {
        return "Coins{" + coins + '}';
    }
}
