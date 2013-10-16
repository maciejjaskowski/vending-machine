package jaskowski.vendingMachine.slot;

import jaskowski.vendingMachine.money.Price;

public class SlotBuilder {
    private Price price = new Price(100);
    private Product[] products = new Product[0];

    public Slot build() {
        Slot slot = new Slot(price, products);
        return slot;
    }

    public SlotBuilder withPrice(int price) {
        this.price = new Price(price);
        return this;
    }

    public SlotBuilder withProducts(Product ...products) {
        this.products = products;
        return this;
    }
}
