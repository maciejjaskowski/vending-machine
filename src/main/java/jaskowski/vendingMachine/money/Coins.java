package jaskowski.vendingMachine.money;


import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Coins implements Iterable<Coin> {

    private List<Coin> coins;

    public Coins(Collection<Coin> coins) {
        this.coins = new ArrayList<Coin>(coins);
        Collections.sort(this.coins);
    }

    public Coins(Coin ...coins) {
        this(asList(coins));
    }

    public Coins(Coins coins) {
        this(newArrayList(coins));
    }

    public void add(Coin coin) {
        coins.add(coin);
        Collections.sort(coins);
    }

    public void addAll(Coins coins) {
        this.coins.addAll(newArrayList(coins));
        Collections.sort(this.coins);
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
