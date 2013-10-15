package jaskowski.vendingMachine.slot;

import jaskowski.vendingMachine.money.Money;
import org.fest.assertions.Assertions;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SlotTest {

    @Test
    public void shouldReturnTrueIfExactlyEnoughMoney() {
        //given
        Slot slot = build(aSlot().withPrice(15));

        //then
        assertThat(slot.hasPriceLowerOrEqualThen(new Money(15))).isTrue();
    }

    @Test
    public void shouldReturnTrueIfEnoughMoney() {
        //given
        Slot slot = build(aSlot().withPrice(14));

        //then
        assertThat(slot.hasPriceLowerOrEqualThen(new Money(15))).isTrue();
    }

    @Test
    public void shouldReturnFalseIfNotEnoughMoney() {
        //given
        Slot slot = build(aSlot().withPrice(16));

        //then
        assertThat(slot.hasPriceLowerOrEqualThen(new Money(15))).isFalse();
    }

    @Test
    public void shouldReleaseProduct() {
        //given
        Product product = aProduct();
        Slot slot = build(aSlot().withPrice(15).withProducts(product));
        ProductDispenser productDispenser = mock(ProductDispenser.class);

        //when
        slot.release(productDispenser);

        //then
        verify(productDispenser).release(product);
    }

    @Test
    public void shouldRemainSomethingToPay() {
        //given
        Product product = aProduct();
        Slot slot = build(aSlot().withPrice(15));

        //when
        Money money = slot.remainsToPay(new Money(10));

        //then
        assertThat(money).isEqualTo(new Money(5));
    }

    @Test
    public void shouldNothingRemainToPay() {
        //given
        Product product = aProduct();
        Slot slot = build(aSlot().withPrice(15));

        //when
        Money money = slot.remainsToPay(new Money(15));

        //then
        assertThat(money).isEqualTo(new Money(0));
    }

    @Test
    public void shouldNothingRemainToPayIfMoreMoneyPut() {
        //given
        Slot slot = build(aSlot().withPrice(15));

        //when
        Money money = slot.remainsToPay(new Money(29));

        //then
        Assertions.assertThat(money).isEqualTo(new Money(0));
    }

    @Test
    public void shouldProductBeAvailable() {
        //given
        Product product = aProduct();
        Slot slot = build(aSlot().withProducts(product));

        //then
        assertThat(slot.productAvailable()).isTrue();
    }

    @Test
    public void shouldProductBeNotAvailable() {
        //given
        Product product = aProduct();
        Slot slot = build(aSlot());

        //then
        assertThat(slot.productAvailable()).isFalse();
    }

    private Slot build(SlotBuilder slotBuilder) {
        return slotBuilder.build();
    }

    private SlotBuilder aSlot() {
        return new SlotBuilder();
    }

    private Product aProduct() {
        return new Product("Coca cola");
    }
}
