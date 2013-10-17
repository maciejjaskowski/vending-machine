package jaskowski.vendingMachine;

import jaskowski.vendingMachine.coinBag.*;
import jaskowski.vendingMachine.coinsDispenser.CoinsDispenser;
import jaskowski.vendingMachine.coinsStorage.ChangeCannotBeReturnedException;
import jaskowski.vendingMachine.coinsStorage.CoinsStorage;
import jaskowski.vendingMachine.coinsStorage.CoinsStorageVisitor;
import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Money;
import jaskowski.vendingMachine.slot.*;

public class VendingMachine {
    private final Display display;
    private final ProductDispenser productDispenser;
    private final CoinsDispenser coinsDispenser;
    private final CoinsStorage coinsStorage;
    private final SlotsStorage slotsStorage;
    private CoinBag coinBag;

    public VendingMachine(Display display, ProductDispenser productDispenser, CoinsDispenser coinsDispenser, CoinsStorage coinsStorage, SlotsStorage slotsStorage) {
        this.display = display;
        this.productDispenser = productDispenser;
        this.coinsDispenser = coinsDispenser;
        this.coinsStorage = coinsStorage;
        this.slotsStorage = slotsStorage;
        this.coinBag = createImmediatelyReleasingCoinBag();
    }

    public VendingMachine putCoin(Coin coin) {
        coinBag.putCoin(coin);

        return this;
    }

    public VendingMachine chooseSlot(SlotId slotId) {
        try {
            onSlotFound(slotsStorage.find(slotId));
        } catch (SlotDoesNotExistException ignore) {
            //but onSlotFound is not invoked!
            display.invalidSlotChosen();
        } finally {
            return this;
        }
    }


    private void onSlotFound(final Slot chosenSlot) {
        if (!chosenSlot.productAvailable()) {
            display.productNotAvailable();
            return;
        }
        coinBag = new RegularCoinBag(display, new PendingTransaction(
                new AbstractExpectedMoney() {
            @Override
            public Money lacks(Money money) {
                return chosenSlot.howMuchMoreShouldBePaidThen(money);
            }
        }, new Transaction() {
            @Override
            public void commit(Money moneyInserted) throws TransactionException {
                try {
                    Money moneyToRelease = chosenSlot.overPaid(moneyInserted);
                    coinsStorage.releaseChange(moneyToRelease, coinsDispenser);
                    coinBag.releaseCoins(new CoinsDispenser() {
                        @Override
                        public void release(Coins coins) {
                            coinsStorage.addAll(coins);
                        }
                    });
                    chosenSlot.releaseProduct(productDispenser);
                    display.productBought();
                    coinBag = createImmediatelyReleasingCoinBag();
                } catch (ChangeCannotBeReturnedException e) {
                    throw new TransactionException(e);
                }
            }

            @Override
            public void rollback() {
                coinBag.releaseCoins(coinsDispenser);
                display.cantChange();
                coinBag = createImmediatelyReleasingCoinBag();
            }
        }));
    }

    public VendingMachine cancel() {
        coinBag.releaseCoins(coinsDispenser);
        display.cancelled();
        display.chooseProduct();
        return this;
    }

    private ImmediatelyReleasingCoinBag createImmediatelyReleasingCoinBag() {
        return new ImmediatelyReleasingCoinBag(new Transaction() {
            @Override
            public void commit(Money moneyInserted) {
                coinBag.releaseCoins(coinsDispenser);
                display.chooseProduct();
            }

            @Override
            public void rollback() {
                throw new UnsupportedOperationException();
            }
        });
    }

    public void accept(SlotVisitor slotVisitor) {
        slotsStorage.accept(slotVisitor);
    }

    public void accept(CoinsStorageVisitor coinsStorageVisitor) {
        coinsStorage.accept(coinsStorageVisitor);
    }

}
