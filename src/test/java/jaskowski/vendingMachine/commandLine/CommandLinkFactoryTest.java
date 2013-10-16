package jaskowski.vendingMachine.commandLine;

import jaskowski.vendingMachine.VendingMachine;
import jaskowski.vendingMachine.coinsStorage.CoinsStorageVisitor;
import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Price;
import jaskowski.vendingMachine.slot.SlotId;
import jaskowski.vendingMachine.slot.SlotVisitor;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommandLinkFactoryTest {

    @Mock
    private VendingMachine vendingMachine;

    private CommandLinkFactory factory;

    @Before
    public void setUp() {
        factory = new CommandLinkFactory(vendingMachine);
    }

    @Test
    public void shouldRespondToChooseProduct() {
        //given
        CommandLink commandLink = factory.chooseProduct();

        assertThatResponds(commandLink, "choose product 1");
        assertThatResponds(commandLink, "choose product 12");
        assertThatResponds(commandLink, "choose product 22");
        verify(vendingMachine).chooseSlot(new SlotId("1"));
        verify(vendingMachine).chooseSlot(new SlotId("12"));
        verify(vendingMachine).chooseSlot(new SlotId("22"));
    }

    @Test
    public void shouldRespondToListProduct() {
        //given
        SlotVisitor slotVisitor = mock(SlotVisitor.class);
        CommandLink commandLink = factory.listProducts(slotVisitor);

        assertThatResponds(commandLink, "list products");
        verify(vendingMachine).accept(slotVisitor);
    }

    @Test
    public void shouldRespondToInspectCoinsStorage() {
        //given
        CommandLink commandLink = factory.inspectCoinsStorage();

        assertThatResponds(commandLink, "inspect coinsStorage");
        verify(vendingMachine).accept(any(CoinsStorageVisitor.class));
    }

    @Test
    public void shouldRespondToInsertCoin1() {
        //given
        CommandLink commandLink = factory.insertCoin(1);

        assertThatResponds(commandLink, "insert coin 1");
        verify(vendingMachine).putCoin(Coin.coin1());
    }

    @Test
    public void shouldRespondToInsertCoin2() {
        //given
        CommandLink commandLink = factory.insertCoin(2);


        assertThatResponds(commandLink, "insert coin 2");
        verify(vendingMachine).putCoin(Coin.coin2());
    }

    @Test
    public void shouldRespondToInsertCoin5() {
        //given
        CommandLink commandLink = factory.insertCoin(5);

        assertThatResponds(commandLink, "insert coin 5");
        verify(vendingMachine).putCoin(Coin.coin5());
    }

    private void assertThatResponds(CommandLink commandLink, String text) {
        assertThat(commandLink.tryInvoke(text)).isTrue();
    }
}

