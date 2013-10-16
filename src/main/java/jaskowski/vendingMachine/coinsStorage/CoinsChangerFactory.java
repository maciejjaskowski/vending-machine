package jaskowski.vendingMachine.coinsStorage;

import jaskowski.vendingMachine.money.Coins;

/**
 * Created with IntelliJ IDEA.
 * User: pragmatists
 * Date: 10/16/13
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CoinsChangerFactory {
    CoinsChanger create(Coins coins);
}
