Feature: No product chosen


  Scenario: Coin inserted
  	Given no product chosen
    When coin 5 is put
    Then machine returns coin 5
    And machine does not release the product
    And machine displays message "Choose product!"