package jaskowski.vendingMachine.slot;

import jaskowski.vendingMachine.money.Price;
import org.junit.Test;
import org.mockito.InOrder;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

public class SlotsStorageTest {

    private SlotsStorage slotsStorage = new SlotsStorage();


    @Test
    public void shouldCreateSlot() throws Exception {
        //given
        Slot aSlot = build(aSlot());
        SlotId id = anId();

        //when
        slotsStorage.put(id, aSlot);

        //then
        assertThat(slotsStorage.find(id)).isEqualTo(aSlot);
    }

    @Test
    public void shouldOverrideSlot() throws Exception {
        //given
        SlotId id = anId();
        givenSomeSlotUnder(id);
        Slot aSlot = build(aSlot());

        //when
        slotsStorage.put(id, aSlot);

        //then
        assertThat(slotsStorage.find(id)).isEqualTo(aSlot);
    }

    @Test(expected = SlotDoesNotExistException.class)
    public void shouldThrowExceptionIfSlotDoesNotExist() throws Exception {
        //given
        SlotId id = anId();

        //when
        slotsStorage.find(id);
    }

    @Test
    public void shouldVisitEmptySlot() {
        //given
        SlotVisitor slotVisitor = mock(SlotVisitor.class);
        Slot slot = build(aSlot().withPrice(1));
        SlotId id = new SlotId("8");
        givenSlot(id, slot);

        //when
        slotsStorage.accept(slotVisitor);

        //then
        InOrder inOrder = inOrder(slotVisitor);
        inOrder.verify(slotVisitor).visitSlotId("8");
        inOrder.verify(slotVisitor).visitSlotPrice(new Price(1));
        inOrder.verify(slotVisitor).visitEmptySlot();
    }

    @Test
    public void shouldVisitNotEmptySlot() {
        //given
        SlotVisitor slotVisitor = mock(SlotVisitor.class);
        Slot slot = build(aSlot().withPrice(15).withProducts(new Product("Coca cola")));
        SlotId id = new SlotId("8");
        givenSlot(id, slot);

        //when
        slotsStorage.accept(slotVisitor);

        //then
        InOrder inOrder = inOrder(slotVisitor);
        inOrder.verify(slotVisitor).visitSlotId("8");
        inOrder.verify(slotVisitor).visitSlotPrice(new Price(15));
        inOrder.verify(slotVisitor).visitProduct("Coca cola");
    }

    private void givenSlot(SlotId slotId, Slot slot) {
        slotsStorage.put(slotId, slot);
    }

    private static Slot build(SlotBuilder slotBuilder) {
        return slotBuilder.build();
    }

    private void givenSomeSlotUnder(SlotId id) {
        slotsStorage.put(id, build(aSlot()));
    }

    private int uniqueId = 1;
    private SlotId anId() {
        return new SlotId("" + uniqueId++);
    }

    private SlotBuilder aSlot() {
        return new SlotBuilder().withPrice(15);
    }
}
