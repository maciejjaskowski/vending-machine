package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.coinsDispenser.CoinsDispenser;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Money;
import jaskowski.vendingMachine.money.Price;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;

import static jaskowski.vendingMachine.money.Coin.coin2;
import static jaskowski.vendingMachine.money.Coin.coin5;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class RegularCoinBagTest {

    @Test
    public void shouldReleaseCoinsAsSoonAsEnoughMoneyIsPut() {
        //given
        EnoughMoneyInserted enoughMoneyInserted = mock(EnoughMoneyInserted.class);
        CoinBag coinBag = new RegularCoinBag(isEnoughMoney(true), enoughMoneyInserted);

        //when
        coinBag.putCoin(coin5());

        //then
        verify(enoughMoneyInserted).fire(new Money(5));
    }

    @Test
    public void shouldReleaseCoinsNotSoonerThenWhenEnoughMoneyIsPut() {
        //given
        EnoughMoneyInserted enoughMoneyInserted = mock(EnoughMoneyInserted.class);
        CoinBag coinBag = new RegularCoinBag(isEnoughMoney(false, true), enoughMoneyInserted);

        //when
        coinBag.putCoin(coin5());
        coinBag.putCoin(coin5());

        //then
        verify(enoughMoneyInserted, times(1)).fire(new Money(10));
    }

    @Test
    public void shouldReleaseMoney() {
        //given
        EnoughMoneyInserted enoughMoneyInserted = mock(EnoughMoneyInserted.class);
        CoinBag coinBag = new RegularCoinBag(isEnoughMoney(false, false, true), enoughMoneyInserted);
        CoinsDispenser coinsDispenser = mock(CoinsDispenser.class);

        //when
        coinBag.putCoin(coin5());
        coinBag.releaseCoins(coinsDispenser);
        coinBag.putCoin(coin5());
        coinBag.putCoin(coin2());

        //then
        verify(coinsDispenser).release(new Coins(coin5()));
        verify(enoughMoneyInserted, times(1)).fire(new Money(7));

    }

    @Test
    public void shouldSomeMoneyRemainToBePaid() {
        //given
        EnoughMoneyInserted enoughMoneyInserted = mock(EnoughMoneyInserted.class);
        IsEnoughMoney enoughMoney = isEnoughMoney(false, false, true);
        CoinBag coinBag = new RegularCoinBag(enoughMoney, enoughMoneyInserted);
        when(enoughMoney.lacks(new Money(0))).thenReturn(new Money(5));

        //when
        Price price = coinBag.remainsToPay();

        //then
        assertThat(price).isEqualTo(new Price(5))      ;
    }


    private IsEnoughMoney isEnoughMoney(final boolean ...values) {
        IsEnoughMoney isEnoughMoney = mock(IsEnoughMoney.class);
        OngoingStubbing<Boolean> stubbing = when(isEnoughMoney.enough(any(Money.class)));
        for (boolean value : values) {
            stubbing = stubbing.thenReturn(value);
        }

        return isEnoughMoney;
    }
}
