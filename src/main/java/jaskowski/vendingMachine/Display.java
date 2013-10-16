package jaskowski.vendingMachine;

import jaskowski.vendingMachine.coinBag.RemainsToPayDisplay;
import jaskowski.vendingMachine.money.Price;

public interface Display extends RemainsToPayDisplay {
    void productNotAvailable();

    void remainsToPay(Price price);

    void chooseProduct();

    void cantChange();

    void productBought();

    void cancelled();

    void invalidSlotChosen();
}
