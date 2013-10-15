Feature: Enough money put


  Scenario: An exact amount of money is put
  	Given product with price 10 was chosen
    When coin 5 is put
    And  coin 5 is put
    Then machine releases the chosen product
    And machine displays message "Product bought!"

  Scenario: Too much money put and change can be returned
    Given machine contains coin 1, coin 2
    And product with price 7 was chosen
    When coin 5 is put
    And coin 5 is put
    Then machine releases the chosen product
    And machine returns coin 1, coin 2
    And machine displays message "Product bought!"

  Scenario: Too much money put and change can't be returned
    Given machine contains no coins
    And product with price 7 was chosen
    When coin 5 is put
    And coin 5 is put
    Then machine does not release the product
    And machine returns coin 5, coin 5
    And machine displays message "Sorry, can't release change :-("
