package jaskowski.vendingMachine;

import jaskowski.vendingMachine.slot.SlotsStorage;

public class Given {
    public static GivenSlotsRepository given(SlotsStorage slotsStorage) {
        return new GivenSlotsRepository(slotsStorage);
    }

}
