package jaskowski.vendingMachine;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jaskowski.vendingMachine.coinBag.CoinsDispenser;
import jaskowski.vendingMachine.money.Coin;
import jaskowski.vendingMachine.money.Coins;
import jaskowski.vendingMachine.money.Price;
import jaskowski.vendingMachine.slot.*;

import static org.mockito.Mockito.*;


public class Stepdefs {

    private final ProductDispenser productDispenser = mock(ProductDispenser.class);
    private final CoinsDispenser coinsDispenser = mock(CoinsDispenser.class);
    private final Product product = new Product("Coca cola");
    private final Display display = mock(Display.class);
    private final SlotsRepository slotsRepository = new SlotsRepository();
    private final VendingMachine vendingMachine = new VendingMachine(display, productDispenser, coinsDispenser, slotsRepository);

    @Given("^product with price (\\d+) was chosen$")
    public void product_with_price_was_chosen(int price) throws Throwable {
        final SlotId id = new SlotId("1");

        slotsRepository.put(id, new Slot(new Price(price), product));

        vendingMachine.chooseSlot(id);
    }

    @When("^coin (\\d+) is put$")
    public void coin5_is_put(int value) throws Throwable {
        vendingMachine.putCoin(coin(value));
    }

    @Then("^machine releases the chosen product$")
    public void machine_releases_the_chosen_product() throws Throwable {
        verify(productDispenser).release(product);
    }

    @When("^cancel button is pushed$")
    public void cancel_button_is_pushed() throws Throwable {
        vendingMachine.cancel();
    }

    @Then("^machine returns coin (\\d+)$")
    public void machine_returns_coin(int value) throws Throwable {
        verify(coinsDispenser).release(new Coins(coin(value)));
    }

    private Coin coin(int value) {
        switch (value) {
            case 2:
                return Coin.coin2();
            case 5:
                return Coin.coin5();
        }
        throw new RuntimeException("unknown coin: " + value);
    }

    @Then("^machine does not release the product$")
    public void machine_does_not_release_the_product() throws Throwable {
        verify(productDispenser, never()).release(any(Product.class));
    }

    @Given("^machine contains coin (\\d+), coin (\\d+)$")
    public void machine_contains_coin_coin(int arg1, int arg2) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Then("^machine returns coin (\\d+), coin (\\d+)$")
    public void machine_returns_coin_coin(int arg1, int arg2) throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Given("^machine contains no coins$")
    public void machine_contains_no_coins() throws Throwable {
        // Express the Regexp above with the code you wish you had
        throw new PendingException();
    }

    @Given("^no product chosen$")
    public void no_product_chosen() throws Throwable {
        //empty body
    }

    @When("^empty slot chosen$")
    public void empty_slot_chosen() throws Throwable {
        final SlotId id = new SlotId("1");

        slotsRepository.put(id, new Slot(new Price(15)));

        vendingMachine.chooseSlot(id);
    }

    @When("^invalid slot chosen$")
    public void invalid_slot_chosen() throws Throwable {
        final SlotId id = new SlotId("1");

        vendingMachine.chooseSlot(id);
    }

    @Then("^machine displays message \"Product not available\"$")
    public void machine_displays_message_product_not_available() throws Throwable {
        verify(display).productNotAvailable();
    }

    @Then("^machine displays message \"(\\d+)\"$")
    public void machine_displays_message_price(int value) throws Throwable {
        verify(display).remainsToPay(new Price(value));
    }

    @Then("^machine displays message \"Choose product!\"$")
    public void machine_displays_message_choose_product() throws Throwable {
        verify(display).chooseProduct();
    }

    @Then("^machine displays message \"Sorry, can't release change :-\\(\"$")
    public void machine_displays_message_cant_change() throws Throwable {
        verify(display).cantChange();
    }

    @Then("^machine displays message \"Product bought!\"$")
    public void machine_displays_message_product_bought() throws Throwable {
        verify(display).productBought();
    }

    @Then("^machine displays message \"Operation canceled!\"$")
    public void machine_displays_message_operation_canceled() throws Throwable {
        verify(display).cancelled();
    }

    @Then("^machine displays message \"Invalid slot chosen\"$")
    public void machine_displays_message_invalid_slot_chosen() throws Throwable {
        verify(display).invalidSlotChosen();
    }


}
