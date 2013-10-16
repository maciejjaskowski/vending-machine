package jaskowski.vendingMachine.commandLine;

import jaskowski.vendingMachine.VendingMachine;
import jaskowski.vendingMachine.coinsStorage.CoinsStorageVisitor;
import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Price;
import jaskowski.vendingMachine.slot.SlotId;
import jaskowski.vendingMachine.slot.SlotVisitor;

import java.io.PrintStream;
import java.util.regex.Pattern;

public class CommandLinkFactory {

    private VendingMachine vendingMachine;

    public CommandLinkFactory(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public CommandLink insertCoin(int value) {

        return new CommandLink(Pattern.compile("insert coin " + value), command(coin(value)));

    }

    private Command command(final Coin coin) {

        return new Command<Void>() {

            @Override
            public void command(Void v) {
                vendingMachine.putCoin(coin);
            }

        };
    }

    private Coin coin(int value) {
        switch (value) {
            case 1:
                return Coin.coin1();
            case 2:
                return Coin.coin2();
            case 5:
                return Coin.coin5();
            default:
                throw new RuntimeException();
        }
    }

    public CommandLink chooseProduct() {
        return new CommandLink(Pattern.compile("choose product (\\d+)"), new Command<String>() {

            @Override
            public void command(String id) {
                vendingMachine.chooseSlot(new SlotId(id));
            }
        });
    }

    public CommandLink listProducts(final SlotVisitor slotVisitor) {
        return new CommandLink(Pattern.compile("list products"), new Command<Void>() {
            @Override
            public void command(Void ignore) {
                vendingMachine.accept(slotVisitor);
            }
        });
    }

    public CommandLink inspectCoinsStorage() {
        return new CommandLink(Pattern.compile("inspect coinsStorage"), new Command<Void>() {

            @Override
            public void command(Void param) {
                vendingMachine.accept(new CoinsStorageVisitor() {
                    @Override
                    public void visitCoin(String coins) {
                        System.out.println(coins);
                    }
                });
            }
        });
    }

}
