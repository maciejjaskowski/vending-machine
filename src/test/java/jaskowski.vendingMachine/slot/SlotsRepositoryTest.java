package jaskowski.vendingMachine.slot;

import jaskowski.vendingMachine.money.Price;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SlotsRepositoryTest {

    private SlotsRepository slotsRepository = new SlotsRepository();


    @Test
    public void shouldCreateSlot() throws Exception {
        //given
        Slot aSlot = build(aSlot());
        SlotId id = anId();

        //when
        slotsRepository.put(id, aSlot);

        //then
        assertThat(slotsRepository.find(id)).isEqualTo(aSlot);
    }

    @Test
    public void shouldOverrideSlot() throws Exception {
        //given
        SlotId id = anId();
        givenSomeSlotUnder(id);
        Slot aSlot = build(aSlot());

        //when
        slotsRepository.put(id, aSlot);

        //then
        assertThat(slotsRepository.find(id)).isEqualTo(aSlot);
    }

    @Test(expected = SlotDoesNotExistException.class)
    public void shouldThrowExceptionIfSlotDoesNotExist() throws Exception {
        //given
        SlotId id = anId();

        //when
        slotsRepository.find(id);
    }

    private static Slot build(SlotBuilder slotBuilder) {
        return slotBuilder.build();
    }

    private void givenSomeSlotUnder(SlotId id) {
        slotsRepository.put(id, build(aSlot()));
    }

    private int uniqueId = 1;
    private SlotId anId() {
        return new SlotId("" + uniqueId++);
    }

    private SlotBuilder aSlot() {
        return new SlotBuilder().withPrice(15);
    }
}
