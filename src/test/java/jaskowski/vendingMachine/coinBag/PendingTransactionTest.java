package jaskowski.vendingMachine.coinBag;

import jaskowski.vendingMachine.money.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PendingTransactionTest {

    @Mock
    private Transaction transaction;

    @Test
    public void shouldNotCommitTransactionIfNotEnoughMoney() throws Exception {
        //given
        PendingTransaction pendingTransaction = new PendingTransaction(expectedMoney(new Money(5)), transaction);

        //when
        pendingTransaction.tryCommit(new Money(1));

        //then
        verify(transaction, never()).commit(any(Money.class));
    }

    @Test
    public void shouldCommitTransactionIfEnoughMoney() throws Exception {
        //given
        Money expectedMoney = new Money(5);
        PendingTransaction pendingTransaction = new PendingTransaction(expectedMoney(expectedMoney), transaction);

        //when
        pendingTransaction.tryCommit(expectedMoney);

        //then
        verify(transaction).commit(expectedMoney);
    }

    @Test
    public void shouldInvokeRollbackIfCommitFails() throws Exception {
        //given
        Money expectedMoney = new Money(5);
        PendingTransaction pendingTransaction = new PendingTransaction(expectedMoney(expectedMoney), transaction);
        givenTransactionFails();

        //when
        pendingTransaction.tryCommit(expectedMoney);

        //then
        verify(transaction).rollback();
    }

    private void givenTransactionFails() throws Exception {
        doThrow(new TransactionException()).when(transaction).commit(any(Money.class));
    }

    private AbstractExpectedMoney expectedMoney(final Money expectedMoney) {
        return new AbstractExpectedMoney() {
            @Override
            public Money lacks(Money sum) {
                return expectedMoney.minus(sum);
            }
        };
    }
}
