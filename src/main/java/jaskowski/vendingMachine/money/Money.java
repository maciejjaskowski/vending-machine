package jaskowski.vendingMachine.money;

public class Money {
    private int value;

    public Money(int value) {
        this.value = value;
    }

    public boolean lessOrEqual(Money money) {
        return value <= money.value;
    }

    public boolean less(Money money) {
        return value < money.value;
    }

    public Money add(Money money) {
        return new Money(value + money.value);
    }

    public Money minus(Money money) {
        return new Money(value - money.value);
    }

    public Price asPrice() {
        return new Price(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (value != money.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return "Money{" + value + '}';
    }

}
