package jaskowski.vendingMachine.slot;

import java.util.HashMap;
import java.util.Map;

public class SlotsRepository {

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
}
