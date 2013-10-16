package jaskowski.vendingMachine;

import jaskowski.vendingMachine.coinsDispenser.CoinsDispenser;
import jaskowski.vendingMachine.coinsStorage.ChangeCannotBeReturnedException;
import jaskowski.vendingMachine.coinsStorage.CoinsStorage;
import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Money;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GivenCoinsRepository {
    private CoinsStorage coinsStorage;
    private Money money;
    private CoinsDispenser coinsDispenser = mock(CoinsDispenser.class);

    public GivenCoinsRepository(CoinsStorage coinsStorage) {
        this.coinsStorage = coinsStorage;
    }

    public GivenCoinsRepository contains(Coin... coins) {
        coinsStorage.addAll(new Coins(coins));
        return this;
    }

    public GivenCoinsRepository thenFor(Money money) {
        this.money = money;
        return this;
    }

    public void expectReleasing(Coin ...coins) throws Exception {
        coinsStorage.releaseChange(money, coinsDispenser);
        verify(coinsDispenser).release(new Coins(coins));
    }

    public void expectException(ChangeCannotBeReturnedException expectedException) {
        boolean exceptionCaught = false;
        try {
            coinsStorage.releaseChange(money, coinsDispenser);
        } catch (ChangeCannotBeReturnedException e) {
            assertThat(e).isEqualTo(expectedException);
            exceptionCaught = true;
        }
        assertThat(exceptionCaught).isTrue();

    }
}
