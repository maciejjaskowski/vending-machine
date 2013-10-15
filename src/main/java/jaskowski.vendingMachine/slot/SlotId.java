package jaskowski.vendingMachine.slot;

public class SlotId {
    private String id;

    public SlotId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SlotId slotId = (SlotId) o;

        if (!id.equals(slotId.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
