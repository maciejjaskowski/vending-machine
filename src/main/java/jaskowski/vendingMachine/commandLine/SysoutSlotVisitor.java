package jaskowski.vendingMachine.commandLine;

import jaskowski.vendingMachine.money.Price;
import jaskowski.vendingMachine.slot.SlotVisitor;

import java.io.PrintStream;

class SysoutSlotVisitor implements SlotVisitor {

    private PrintStream printStream = System.out;

    SysoutSlotVisitor(PrintStream printStream) {
        this.printStream = printStream;
    }

    @Override
    public void visitSlotId(String id) {
        printStream.print(id + ": ");
    }

    @Override
    public void visitSlotPrice(Price price) {
        printStream.print(price + ": ");
    }

    @Override
    public void visitEmptySlot() {
        printStream.println("[Empty slot]");
    }

    @Override
    public void visitProduct(String name) {
        printStream.println(name);
    }
}
