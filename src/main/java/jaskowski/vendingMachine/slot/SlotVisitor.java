package jaskowski.vendingMachine.slot;

import jaskowski.vendingMachine.money.Price;

public interface SlotVisitor {

    void visitSlotId(String id);

    void visitSlotPrice(Price price);

    void visitEmptySlot();

    void visitProduct(String name);
}
