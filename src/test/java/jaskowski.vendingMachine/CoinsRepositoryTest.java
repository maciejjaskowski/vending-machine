package jaskowski.vendingMachine;

import jaskowski.vendingMachine.coinsDispenser.CoinsDispenser;
import jaskowski.vendingMachine.coinsRepository.ChangeCannotBeReturnedException;
import jaskowski.vendingMachine.coinsRepository.CoinsRepository;
import jaskowski.vendingMachine.dummyCoinsChanger.DummyCoinsChangerFactory;
import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static jaskowski.vendingMachine.money.Coin.coin1;
import static jaskowski.vendingMachine.money.Coin.coin2;
import static jaskowski.vendingMachine.money.Coin.coin5;
import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CoinsRepositoryTest {

    @Mock
    private CoinsDispenser coinsDispenser;
    private CoinsRepository coinsRepository = new CoinsRepository(new DummyCoinsChangerFactory());

    @Test
    public void shouldAddAllCoins() {
        //given

        //when
        coinsRepository.addAll(coins(coin2(), coin5()));

        //then
        assertThat(coinsRepository.hasTheSameCoins(asList(coin2(), coin5()))).isTrue();
    }

    @Test
    public void shouldReleaseChangeInSimpleCase() throws Exception {
        given(coinsRepository)
                .contains(coin1())
                .thenFor(new Money(1))
                .expectReleasing(coin1());
    }

    @Test
    public void shouldReleaseChangeIfOneTypeOfCoinsAvailableOnly() throws Exception {
        given(coinsRepository)
                .contains(coin1(), coin1(), coin1())
                .thenFor(new Money(2))
                .expectReleasing(coin1(), coin1());
    }

    @Test
    public void shouldReleaseChangeIfManyTypesOfCoinsAvailable() throws Exception {
        given(coinsRepository)
                .contains(coin1(), coin2(), coin2(), coin5(), coin5())
                .thenFor(new Money(9))
                .expectReleasing(coin5(), coin2(), coin2());
    }

    @Test
    public void shouldReleaseChangeIfNothingExpected() throws Exception {
        given(coinsRepository)
                .contains()
                .thenFor(new Money(0))
                .expectReleasing();
    }

    @Test
    public void shouldReleaseChangeIfManyTypesOfCoinsAvailable2() throws Exception {
        given(coinsRepository)
                .contains(coin1(), coin2(), coin2(), coin2(), coin5(), coin5())
                .thenFor(new Money(6))
                .expectReleasing(coin5(), coin1());
    }

    @Test
    public void shouldThrowExceptionIfChangeCantBeReturned() {
        given(coinsRepository)
                .contains(coin1(), coin2())
                .thenFor(new Money(6))
                .expectException(new ChangeCannotBeReturnedException());
    }



    private GivenCoinsRepository given(CoinsRepository coinsRepository) {
        return new GivenCoinsRepository(coinsRepository);
    }

    private Coins coins(Coin ...coins) {
        return new Coins(coins);
    }
}
