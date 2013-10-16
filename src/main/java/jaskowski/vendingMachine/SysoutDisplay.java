package jaskowski.vendingMachine;

import jaskowski.vendingMachine.money.Price;

public class SysoutDisplay implements Display {

    @Override
    public void productNotAvailable() {
        System.out.println("Display: product not available!");
    }

    @Override
    public void remainsToPay(Price price) {
        System.out.println("Display: " + price);
    }

    @Override
    public void chooseProduct() {
        System.out.println("Display: Choose product first!");
    }

    @Override
    public void cantChange() {
        System.out.println("Display: Can't release change!");
    }

    @Override
    public void productBought() {
        System.out.println("Display: Product bought!");
    }

    @Override
    public void cancelled() {
        System.out.println("Display: Cancelled!");
    }

    @Override
    public void invalidSlotChosen() {
        System.out.println("Display: Choose valid number!");
    }
}
