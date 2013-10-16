package jaskowski.vendingMachine;

import jaskowski.vendingMachine.coinBag.CoinBag;
import jaskowski.vendingMachine.coinsDispenser.CoinsDispenser;
import jaskowski.vendingMachine.coinsRepository.CoinsRepository;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.slot.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static jaskowski.vendingMachine.Given.given;
import static jaskowski.vendingMachine.money.Coin.coin5;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class VendingMachineTest {

    @Mock
    private ProductDispenser productDispenser;
    @Mock
    private SlotsRepository slotsRepository;
    @Mock
    private CoinBag coinBag;
    @Mock
    private CoinsDispenser coinsDispenser;
    @Mock
    private Display display;
    @Mock
    private CoinsRepository coinsRepository;

    private VendingMachine vendingMachine;

    @Before
    public void setUp() {
        vendingMachine = new VendingMachine(display, productDispenser, coinsDispenser, coinsRepository, slotsRepository);
    }

    @Test
    public void shouldPutCoinsIntoCoinBag() {
        //given

        //when
        vendingMachine.putCoin(coin5());

        //then
        coinBag.putCoin(coin5());
    }

    @Test
    public void shouldReleaseProductIfEnoughMoneyInserted() throws Exception {
        //given
        SlotId id = anId();
        Product product = aProduct();
        Slot slot = build(aSlot().withPrice(5).withProducts(product));
        given(slotsRepository).contains(id, slot);
        givenChosenSlot(id);

        //when
        vendingMachine.putCoin(coin5());

        //then
        verify(productDispenser).release(product);
    }

    @Test
    public void shouldReleaseCoinsIfCancelButtonPressed() throws Exception {
        //given
        SlotId id = anId();
        Product product = aProduct();
        Slot slot = build(aSlot().withPrice(10).withProducts(product));
        given(slotsRepository).contains(id, slot);
        givenChosenSlot(id);

        //when
        vendingMachine.putCoin(coin5());
        vendingMachine.cancel();

        //then
        verify(coinsDispenser).release(new Coins(coin5()));
        verify(productDispenser, never()).release(product);
    }

    @Test
    public void shouldReleaseCoinsImmediatelyIfNoProductChosen() throws Exception {
        //given

        //when
        vendingMachine.putCoin(coin5());

        //then
        verify(coinsDispenser).release(new Coins(coin5()));
        verify(productDispenser, never()).release(any(Product.class));
    }

    @Test
    public void shouldReleaseCoinsImmediatelyAfterProductBough() throws Exception {
        //given
        productWasJustBough();

        //when
        vendingMachine.putCoin(coin5());

        //then
        verify(coinsDispenser).release(new Coins(coin5()));
        verify(productDispenser, never()).release(any(Product.class));
    }

    private void productWasJustBough() throws Exception {
        SlotId id = anId();
        Product product = aProduct();
        Slot slot = build(aSlot().withPrice(5).withProducts(product));
        given(slotsRepository).contains(id, slot);
        givenChosenSlot(id);

        vendingMachine.putCoin(coin5());
        Mockito.reset(productDispenser, coinsDispenser);
    }


    private void givenChosenSlot(SlotId id) {
        vendingMachine.chooseSlot(id);
    }

    private static Slot build(SlotBuilder slotBuilder) {
        return slotBuilder.build();
    }

    private SlotBuilder aSlot() {
        return new SlotBuilder();
    }

    private SlotId anId() {
        return new SlotId("5");
    }


    private Product aProduct() {
        return new Product("Coca Cola");
    }
}
