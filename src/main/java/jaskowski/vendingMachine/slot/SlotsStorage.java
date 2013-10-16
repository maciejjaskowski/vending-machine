package jaskowski.vendingMachine.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SlotsStorage {

    private Map<SlotId, Slot> map = new HashMap<SlotId, Slot>();

    public Slot find(SlotId slotId) throws SlotDoesNotExistException {
        if (!map.containsKey(slotId)) {
            throw new SlotDoesNotExistException(slotId);
        }
        return map.get(slotId);
    }

    public void put(SlotId slotId, Slot slot) {
        map.put(slotId, slot);
    }

    public void accept(SlotVisitor slotVisitor) {
        ArrayList<SlotId> slotIds = new ArrayList<SlotId>(map.keySet());

        for (SlotId slotId : slotIds) {
            Slot slot = map.get(slotId);

            slotId.accept(slotVisitor);
            slot.accept(slotVisitor);
        }
    }
}
