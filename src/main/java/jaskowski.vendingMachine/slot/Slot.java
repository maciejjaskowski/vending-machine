package jaskowski.vendingMachine.slot;

import jaskowski.vendingMachine.money.Money;
import jaskowski.vendingMachine.money.Price;

import java.util.LinkedList;
import java.util.Queue;

import static java.util.Arrays.asList;

public class Slot {
    private final Price price;
    private final Queue<Product> products;

    public Slot(Price price, Product ...products) {
        this.price = price;
        this.products = new LinkedList<Product>(asList(products));
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean hasPriceLowerOrEqualThen(Money money) {
        return price.isEnough(money);
    }

    public void release(ProductDispenser productDispenser) {
        productDispenser.release(products.poll());
    }

    public Money remainsToPay(Money money) {

        return zeroIfLessThenZero(price.asMoney().minus(money));
    }

    public Money overPaid(Money money) {
        return zeroIfLessThenZero(money.minus(price.asMoney()));
    }

    private Money zeroIfLessThenZero(Money difference) {
        if (difference.lessOrEqual(new Money(0))) {
            return new Money(0);
        }
        return difference;
    }

    public boolean productAvailable() {
        return ! products.isEmpty();
    }
}
