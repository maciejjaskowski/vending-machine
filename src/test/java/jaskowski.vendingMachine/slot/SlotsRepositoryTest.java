package jaskowski.vendingMachine.slot;

import jaskowski.vendingMachine.money.Price;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SlotsRepositoryTest {

    private SlotsRepository slotsRepository = new SlotsRepository();


    @Test
    public void shouldCreateSlot() throws Exception {
        //given
        Slot aSlot = aSlot();
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
        Slot aSlot = aSlot();

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

    private void givenSomeSlotUnder(SlotId id) {
        slotsRepository.put(id, aSlot());
    }

    private int uniqueId = 1;
    private SlotId anId() {
        return new SlotId("" + uniqueId++);
    }

    private Slot aSlot() {
        return new Slot(new Price(15));
    }
}
