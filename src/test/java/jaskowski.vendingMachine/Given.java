package jaskowski.vendingMachine;

import jaskowski.vendingMachine.slot.SlotsRepository;

public class Given {
    public static GivenSlotsRepository given(SlotsRepository slotsRepository) {
        return new GivenSlotsRepository(slotsRepository);
    }
}
