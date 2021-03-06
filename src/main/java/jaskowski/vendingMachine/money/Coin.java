package jaskowski.vendingMachine.money;

public class Coin implements Comparable<Coin> {

    private static Coin coin1 = new Coin(1);
    private static Coin coin2 = new Coin(2);
    private static Coin coin5 = new Coin(5);
    private final int value;

    public Coin(int value) {
        this.value = value;
    }

    public static Coin coin5() {
        return coin5;
    };

    public static Coin coin2() {
        return coin2;
    }

    public static Coin coin1() {
        return coin1;
    }

    public Money asMoney() {
        return new Money(value);
    }

    @Override
    public int compareTo(Coin o) {
        if (value < o.value) {
            return -1;
        }
        if (value > o.value) {
            return 1;
        }
        return 0;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coin coin = (Coin) o;

        if (value != coin.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "Coin{" + value + '}';
    }


}
