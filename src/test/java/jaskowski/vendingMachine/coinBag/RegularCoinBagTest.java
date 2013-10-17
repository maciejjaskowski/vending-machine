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
    private Transaction transaction;
    @Mock
    private CoinsDispenser coinsDispenser;

    @Test
    public void shouldReleaseCoinsAsSoonAsEnoughMoneyIsPut() throws Exception {
        //given
        CoinBag coinBag = new RegularCoinBag(display, transaction(isEnoughMoney(5), transaction));

        //when
        coinBag.putCoin(coin5());

        //then
        verify(transaction).commit(new Money(5));
        verify(display).remainsToPay(new Price(0));
    }

    @Test
    public void shouldReleaseCoinsNotSoonerThenWhenEnoughMoneyIsPut() throws Exception {
        //given
        CoinBag coinBag = new RegularCoinBag(display, transaction(isEnoughMoney(10), transaction));

        //when
        coinBag.putCoin(coin5());
        coinBag.putCoin(coin5());

        //then
        InOrder inOrder = inOrder(transaction, display);
        inOrder.verify(display).remainsToPay(new Price(10));
        inOrder.verify(display).remainsToPay(new Price(5));
        inOrder.verify(display).remainsToPay(new Price(0));
        inOrder.verify(transaction, times(1)).commit(new Money(10));
    }

    @Test
    public void shouldReleaseMoney() throws Exception {
        //given
        CoinBag coinBag = new RegularCoinBag(display, transaction(isEnoughMoney(7), transaction));


        //when
        coinBag.putCoin(coin5());
        coinBag.releaseCoins(coinsDispenser);

        coinBag.putCoin(coin2());
        coinBag.putCoin(coin5());

        //then


        InOrder inOrder = inOrder(coinsDispenser, transaction, display);
        inOrder.verify(display).remainsToPay(new Price(7));
        inOrder.verify(display).remainsToPay(new Price(2));
        inOrder.verify(coinsDispenser).release(new Coins(coin5()));

        inOrder.verify(display).remainsToPay(new Price(5));
        inOrder.verify(display).remainsToPay(new Price(0));
        inOrder.verify(transaction).commit(new Money(7));

    }

    private PendingTransaction transaction(final ExpectedMoney enoughMoney, final Transaction transaction) {
        return new PendingTransaction(enoughMoney, transaction);
    }

    private ExpectedMoney isEnoughMoney(final int value) {
        ExpectedMoney enoughMoney = new AbstractExpectedMoney() {
            @Override
            public Money lacks(Money sum) {
                return new Money(value).minus(sum);
            }
        };

        return enoughMoney;
    }
}
