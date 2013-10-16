package jaskowski.vendingMachine.commandLine;

import jaskowski.vendingMachine.money.Price;
import jaskowski.vendingMachine.slot.SlotVisitor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SysoutSlotVisitorTest {

    @Mock
    private PrintStream printStream;

    private SysoutSlotVisitor sysoutSlotVisitor;

    @Before
    public void  setUp() {
        sysoutSlotVisitor = new SysoutSlotVisitor(printStream);
    }

    @Test
    public void shouldVisitProduct() {
        //when
        sysoutSlotVisitor.visitProduct("Coca cola");

        //then
        verify(printStream).println("Coca cola");

    }

    @Test
    public void shouldVisitSlotId() {
        //when
        sysoutSlotVisitor.visitSlotId("123");

        //then
        verify(printStream).print("123: ");
    }

    @Test
    public void shouldVisitPrice() {
        //when
        sysoutSlotVisitor.visitSlotPrice(new Price(12));

        //then
        verify(printStream).print(new Price(12) + ": ");
    }

    @Test
    public void shouldVisitEmptySlot() {
        //when
        sysoutSlotVisitor.visitEmptySlot();

        //then
        verify(printStream).println("[Empty slot]");
    }


}
