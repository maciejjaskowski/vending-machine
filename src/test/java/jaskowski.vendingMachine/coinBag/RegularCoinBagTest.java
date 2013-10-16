package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.Display;
import jaskowski.vendingMachine.coinsDispenser.CoinsDispenser;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Money;
import jaskowski.vendingMachine.money.Price;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static jaskowski.vendingMachine.money.Coin.coin2;
import static jaskowski.vendingMachine.money.Coin.coin5;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegularCoinBagTest {

    @Mock
    private Display display;
    @Mock
    private EnoughMoneyInserted enoughMoneyInserted;
    @Mock
    private CoinsDispenser coinsDispenser;

    @Test
    public void shouldReleaseCoinsAsSoonAsEnoughMoneyIsPut() {
        //given
        CoinBag coinBag = new RegularCoinBag(display, transaction(isEnoughMoney(5), enoughMoneyInserted));

        //when
        coinBag.putCoin(coin5());

        //then
        verify(enoughMoneyInserted).fire(new Money(5));
        verify(display).remainsToPay(new Price(0));
    }

    @Test
    public void shouldReleaseCoinsNotSoonerThenWhenEnoughMoneyIsPut() {
        //given
        CoinBag coinBag = new RegularCoinBag(display, transaction(isEnoughMoney(10), enoughMoneyInserted));

        //when
        coinBag.putCoin(coin5());
        coinBag.putCoin(coin5());

        //then
        InOrder inOrder = inOrder(enoughMoneyInserted, display);
        inOrder.verify(display).remainsToPay(new Price(10));
        inOrder.verify(display).remainsToPay(new Price(5));
        inOrder.verify(display).remainsToPay(new Price(0));
        inOrder.verify(enoughMoneyInserted, times(1)).fire(new Money(10));
    }

    @Test
    public void shouldReleaseMoney() {
        //given
        CoinBag coinBag = new RegularCoinBag(display, transaction(isEnoughMoney(7), enoughMoneyInserted));


        //when
        coinBag.putCoin(coin5());
        coinBag.releaseCoins(coinsDispenser);

        coinBag.putCoin(coin2());
        coinBag.putCoin(coin5());

        //then


        InOrder inOrder = inOrder(coinsDispenser, enoughMoneyInserted, display);
        inOrder.verify(display).remainsToPay(new Price(7));
        inOrder.verify(display).remainsToPay(new Price(2));
        inOrder.verify(coinsDispenser).release(new Coins(coin5()));

        inOrder.verify(display).remainsToPay(new Price(5));
        inOrder.verify(display).remainsToPay(new Price(0));
        inOrder.verify(enoughMoneyInserted).fire(new Money(7));

    }

    private Transaction transaction(final IsEnoughMoney isEnoughMoney, final EnoughMoneyInserted enoughMoneyInserted) {
        return new Transaction(isEnoughMoney, enoughMoneyInserted);
    }

    private IsEnoughMoney isEnoughMoney(final int value) {
        IsEnoughMoney isEnoughMoney = new AbstractIsEnoughMoney() {
            @Override
            public Money lacks(Money sum) {
                return new Money(value).minus(sum);
            }
        };

        return isEnoughMoney;
    }
}
