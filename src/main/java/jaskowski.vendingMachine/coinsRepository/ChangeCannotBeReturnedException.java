package jaskowski.vendingMachine.coinsRepository;

import jaskowski.vendingMachine.money.Money;

public class ChangeCannotBeReturnedException extends Exception {

    public ChangeCannotBeReturnedException() {
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().equals(getClass());
    }
}
