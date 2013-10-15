package jaskowski.vendingMachine;

import jaskowski.vendingMachine.slot.Slot;
import jaskowski.vendingMachine.slot.SlotDoesNotExistException;
import jaskowski.vendingMachine.slot.SlotId;
import jaskowski.vendingMachine.slot.SlotsRepository;
import org.mockito.Mockito;

public class GivenSlotsRepository {
    private SlotsRepository slotsRepository;

    public GivenSlotsRepository(SlotsRepository slotsRepository) {
        this.slotsRepository = slotsRepository;
    }

    public void contains(SlotId id, Slot slot) throws SlotDoesNotExistException {
        Mockito.when(slotsRepository.find(id)).thenReturn(slot);
    }

    public void doesNotContain(SlotId id) throws SlotDoesNotExistException {
        Mockito.when(slotsRepository.find(id)).thenThrow(new SlotDoesNotExistException(id));
    }
}
