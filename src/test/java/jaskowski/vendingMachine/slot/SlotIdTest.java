package jaskowski.vendingMachine.slot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SlotIdTest {

    @Mock
    private SlotVisitor slotVisitor;

    @Test
    public void shouldVisitSlotId() {
        //given
        SlotId slot = new SlotId("123");

        //when
        slot.accept(slotVisitor);

        //then
        verify(slotVisitor).visitSlotId("123");
    }


}
