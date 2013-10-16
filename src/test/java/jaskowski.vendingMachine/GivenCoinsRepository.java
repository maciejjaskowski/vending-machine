package jaskowski.vendingMachine;

import jaskowski.vendingMachine.coinsDispenser.CoinsDispenser;
import jaskowski.vendingMachine.coinsRepository.ChangeCannotBeReturnedException;
import jaskowski.vendingMachine.coinsRepository.CoinsRepository;
import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Money;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GivenCoinsRepository {
    private CoinsRepository coinsRepository;
    private Money money;
    private CoinsDispenser coinsDispenser = mock(CoinsDispenser.class);

    public GivenCoinsRepository(CoinsRepository coinsRepository) {
        this.coinsRepository = coinsRepository;
    }

    public GivenCoinsRepository contains(Coin... coins) {
        coinsRepository.addAll(new Coins(coins));
        return this;
    }

    public GivenCoinsRepository thenFor(Money money) {
        this.money = money;
        return this;
    }

    public void expectReleasing(Coin ...coins) throws Exception {
        coinsRepository.releaseChange(money, coinsDispenser);
        verify(coinsDispenser).release(new Coins(coins));
    }

    public void expectException(ChangeCannotBeReturnedException expectedException) {
        boolean exceptionCaught = false;
        try {
            coinsRepository.releaseChange(money, coinsDispenser);
        } catch (ChangeCannotBeReturnedException e) {
            assertThat(e).isEqualTo(expectedException);
            exceptionCaught = true;
        }
        assertThat(exceptionCaught).isTrue();

    }
}
