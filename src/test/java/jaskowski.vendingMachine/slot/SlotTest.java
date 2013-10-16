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
        slot.releaseProduct(productDispenser);

        //then
        verify(productDispenser).release(product);
    }

    @Test
    public void shouldRemainSomethingToPay() {
        //given
        Product product = aProduct();
        Slot slot = build(aSlot().withPrice(15));

        //when
        Money money = slot.howMuchMoreShouldBePaidThen(new Money(10));

        //then
        assertThat(money).isEqualTo(new Money(5));
    }

    @Test
    public void shouldNothingRemainToPay() {
        //given
        Product product = aProduct();
        Slot slot = build(aSlot().withPrice(15));

        //when
        Money money = slot.howMuchMoreShouldBePaidThen(new Money(15));

        //then
        assertThat(money).isEqualTo(new Money(0));
    }

    @Test
    public void shouldNothingRemainToPayIfMoreMoneyPut() {
        //given
        Slot slot = build(aSlot().withPrice(15));

        //when
        Money money = slot.howMuchMoreShouldBePaidThen(new Money(29));

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
        Slot slot = build(aSlot().withProducts());

        //then
        assertThat(slot.productAvailable()).isFalse();
    }

    @Test
    public void shouldReturnOverPaidZeroIfNotOverPaid() {
        //given
        Slot slot = build(aSlot().withPrice(10));

        //then
        assertThat(slot.overPaid(new Money(10))).isEqualTo(new Money(0));
    }

    @Test
    public void shouldReturnOverPaidZeroIfUnderPaid() {
        //given
        Slot slot = build(aSlot().withPrice(15));

        //then
        assertThat(slot.overPaid(new Money(10))).isEqualTo(new Money(0));
    }

    @Test
    public void shouldReturnPositiveOverPaidIfOverPaid() {
        //given
        Slot slot = build(aSlot().withPrice(5));

        //then
        assertThat(slot.overPaid(new Money(10))).isEqualTo(new Money(5));
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
