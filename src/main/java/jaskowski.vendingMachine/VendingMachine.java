package jaskowski.vendingMachine;

import jaskowski.vendingMachine.coinBag.*;
import jaskowski.vendingMachine.coinsDispenser.CoinsDispenser;
import jaskowski.vendingMachine.coinsStorage.ChangeCannotBeReturnedException;
import jaskowski.vendingMachine.coinsStorage.CoinsStorage;
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
        display.remainsToPay(coinBag.remainsToPay());

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
        coinBag = new RegularCoinBag(new AbstractIsEnoughMoney() {
            @Override
            public Money lacks(Money money) {
                return chosenSlot.remainsToPay(money);
            }
        }, new EnoughMoneyInserted() {
            @Override
            public void fire(Money moneyInserted) {
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
                } catch (ChangeCannotBeReturnedException ignore) {
                    coinBag.releaseCoins(coinsDispenser);
                    display.cantChange();
                }
                VendingMachine.this.coinBag = createImmediatelyReleasingCoinBag();
            }
        });
        display.remainsToPay(coinBag.remainsToPay());
    }

    public VendingMachine cancel() {
        coinBag.releaseCoins(coinsDispenser);
        display.cancelled();
        display.chooseProduct();
        return this;
    }

    private ImmediatelyReleasingCoinBag createImmediatelyReleasingCoinBag() {
        return new ImmediatelyReleasingCoinBag(new EnoughMoneyInserted() {
            @Override
            public void fire(Money moneyInserted) {
                coinBag.releaseCoins(coinsDispenser);
                display.chooseProduct();
            }
        });
    }
}
