package jaskowski.vendingMachine;

import jaskowski.vendingMachine.slot.Slot;
import jaskowski.vendingMachine.slot.SlotDoesNotExistException;
import jaskowski.vendingMachine.slot.SlotId;
import jaskowski.vendingMachine.slot.SlotsStorage;
import org.mockito.Mockito;

public class GivenSlotsRepository {
    private SlotsStorage slotsStorage;

    public GivenSlotsRepository(SlotsStorage slotsStorage) {
        this.slotsStorage = slotsStorage;
    }

    public void contains(SlotId id, Slot slot) throws SlotDoesNotExistException {
        Mockito.when(slotsStorage.find(id)).thenReturn(slot);
    }

    public void doesNotContain(SlotId id) throws SlotDoesNotExistException {
        Mockito.when(slotsStorage.find(id)).thenThrow(new SlotDoesNotExistException(id));
    }
}
