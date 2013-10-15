package jaskowski.vendingMachine;

import jaskowski.vendingMachine.coinBag.*;
import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Money;
import jaskowski.vendingMachine.slot.*;

public class VendingMachine {
    private Display display;
    private final ProductDispenser productDispenser;
    private final CoinsDispenser coinsDispenser;
    private final SlotsRepository slotsRepository;
    private CoinBag coinBag;

    public VendingMachine(Display display, ProductDispenser productDispenser, CoinsDispenser coinsDispenser, SlotsRepository slotsRepository) {
        this.display = display;
        this.productDispenser = productDispenser;
        this.coinsDispenser = coinsDispenser;
        this.slotsRepository = slotsRepository;
        this.coinBag = createAlwaysReleasingCoinBag();
    }

    public VendingMachine putCoin(Coin coin) {
        coinBag.putCoin(coin);
        display.remainsToPay(coinBag.remainsToPay());

        return this;
    }

    public VendingMachine chooseSlot(SlotId slotId) {
        try {
            onSlotFound(slotsRepository.find(slotId));
        } catch (SlotDoesNotExistException ignore) {
            //but onSlotFound is not invoked!
            display.invalidSlotChosen();
        } finally {
            return this;
        }
    }

    private void onSlotFound(final Slot slot) {
        if (!slot.productAvailable()) {
            display.productNotAvailable();
            return;
        }
        coinBag = new CoinBag(new IsEnoughMoney() {
            @Override
            public boolean enough(Money money) {
                return slot.remainsToPay(money).equals(new Money(0));
            }

            @Override
            public Money lacks(Money money) {
                return slot.remainsToPay(money);
            }
        }, new EnoughMoneyInserted() {
            @Override
            public void fire(Coins coins) {
                slot.release(productDispenser);
                display.productBought();
                VendingMachine.this.coinBag = createAlwaysReleasingCoinBag();
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

    private AlwaysReleasingCoinBag createAlwaysReleasingCoinBag() {
        return new AlwaysReleasingCoinBag(new EnoughMoneyInserted() {
            @Override
            public void fire(Coins coins) {
                VendingMachine.this.coinsDispenser.release(coins);
                display.chooseProduct();
            }
        });
    }
}
