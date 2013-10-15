Feature: Not enough money put


  Scenario: Not enough money put
  	Given product with price 10 was chosen
  	And machine displays message "10"
    When coin 5 is put
    Then machine does not release the product
    And machine displays message "5"
