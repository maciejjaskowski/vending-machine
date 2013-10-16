package jaskowski.vendingMachine.commandLine;

import jaskowski.vendingMachine.Display;
import jaskowski.vendingMachine.SysoutDisplay;
import jaskowski.vendingMachine.VendingMachine;
import jaskowski.vendingMachine.coinsDispenser.CoinsDispenser;
import jaskowski.vendingMachine.coinsStorage.CoinsStorage;
import jaskowski.vendingMachine.greedyCoinsChanger.GreedyCoinsChangerFactory;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Price;
import jaskowski.vendingMachine.slot.*;

import java.util.Scanner;

import static jaskowski.vendingMachine.money.Coin.coin2;
import static jaskowski.vendingMachine.money.Coin.coin5;
import static java.util.Arrays.asList;

public class CommandLine {

    public static void main(String[] args) {
        final Display display = new SysoutDisplay();
        final ProductDispenser productDispenser = new SysOutProductDispenser();
        final CoinsDispenser coinsDispenser = new SysOutCoinsDispenser();
        final SlotsStorage slotsStorage = new SlotsStorage();
        final CoinsStorage coinsStorage = new CoinsStorage(new GreedyCoinsChangerFactory());
        final VendingMachine vendingMachine = new VendingMachine(display, productDispenser, coinsDispenser, coinsStorage, slotsStorage);
        final CommandLinkFactory commandLinkFactory = new CommandLinkFactory(vendingMachine);
        final SafeCommandChain interpreter = new SafeCommandChain(new CommandChainWithHelp(asList(
                commandLinkFactory.listProducts(new SysoutSlotVisitor(System.out)),
                commandLinkFactory.inspectCoinsStorage(),
                commandLinkFactory.insertCoin(1),
                commandLinkFactory.insertCoin(2),
                commandLinkFactory.insertCoin(5),
                commandLinkFactory.chooseProduct()
                ), System.out));


        coinsStorage.addAll(new Coins(coin5(), coin5(), coin2(), coin2(), coin2()));

        slotsStorage.put(new SlotId("1"), new Slot(new Price(15), new Product("Coca cola"), new Product("Fanta")));
        slotsStorage.put(new SlotId("2"), new Slot(new Price(5), new Product("Mars bar"), new Product("Mars bar"), new Product("Mars bar")));
        slotsStorage.put(new SlotId("3"), new Slot(new Price(5)));


        System.out.println("Type 'help' to see the list of available commands");

        while(true) {
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                interpreter.interpret(scanner.nextLine());
            }
        }

    }

    private static class SysOutProductDispenser implements ProductDispenser {
        @Override
        public void release(Product product) {
            System.out.println("Product released: " + product);
        }
    }

    private static class SysOutCoinsDispenser implements CoinsDispenser {
        @Override
        public void release(Coins coins) {
            System.out.println("Coins released: " + coins);
        }
    }
}
