package jaskowski.vendingMachine.money;

public class Price {
    private int value;

    public Price(int price) {
        this.value = price;
    }

    public boolean isEnough(Money money) {
        return new Money(value).lessOrEqual(money);
    }

    public Money asMoney() {
        return new Money(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (value != price.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "Price{" + value + '}';
    }
}
