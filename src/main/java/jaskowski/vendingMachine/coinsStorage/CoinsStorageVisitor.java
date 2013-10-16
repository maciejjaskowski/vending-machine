package jaskowski.vendingMachine.coinsStorage;

public interface CoinsStorageVisitor {
    void visitCoin(String s);
}
