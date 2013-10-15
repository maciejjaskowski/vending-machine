package jaskowski.vendingMachine;

import jaskowski.vendingMachine.money.Price;

public interface Display {
    void productNotAvailable();

    void remainsToPay(Price price);

    void chooseProduct();

    void cantChange();

    void productBought();

    void cancelled();

    void invalidSlotChosen();
}
