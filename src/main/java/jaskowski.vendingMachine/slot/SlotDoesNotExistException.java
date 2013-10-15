package jaskowski.vendingMachine.slot;


public class SlotDoesNotExistException extends Exception {
    public SlotDoesNotExistException(SlotId slotId) {
        super("Slot with id " + slotId + " does not exist!");
    }
}
