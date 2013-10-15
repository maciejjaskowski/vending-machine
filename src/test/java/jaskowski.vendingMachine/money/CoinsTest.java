package jaskowski.vendingMachine.money;

import org.junit.Test;

import static jaskowski.vendingMachine.money.Coin.coin2;
import static jaskowski.vendingMachine.money.Coin.coin5;
import static org.fest.assertions.Assertions.assertThat;

public class CoinsTest {


    @Test
    public void shouldAddCoin() {
        //given
        Coins coins = new Coins();

        //when
        coins.add(coin5());

        //then
        assertThat(coins).containsOnly(coin5());
    }

    @Test
    public void shouldAddTwoSameCoins() {
        //given
        Coins coins = new Coins();

        //when
        coins.add(coin5());
        coins.add(coin5());

        //then
        assertThat(coins).containsOnly(coin5(), coin5());
    }

    @Test
    public void shouldAddTwoDifferentCoins() {
        //given
        Coins coins = new Coins();

        //when
        coins.add(coin5());
        coins.add(coin2());

        //then
        assertThat(coins).containsOnly(coin5(), coin2());
    }

    @Test
    public void shouldSumOfZeroCoinsBeZero() {
        //given
        Coins coins = new Coins();

        //then
        assertThat(coins.sum()).isEqualTo(new Money(0));
    }

    @Test
    public void shouldSumOfOneCoinBeEqualMoneyOfThatCoin() {
        //given
        Coins coins = new Coins();

        //when
        coins.add(Coin.coin2());

        //then
        assertThat(coins.sum()).isEqualTo(new Money(2));
    }

    @Test
    public void shouldSumCoins() {
        //given
        Coins coins = new Coins();

        //when
        coins.add(coin5());
        coins.add(coin2());

        //then
        assertThat(coins.sum()).isEqualTo(new Money(7));
    }


}
