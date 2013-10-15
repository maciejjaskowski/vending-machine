Feature: Cancel button pushed


  Scenario: Cancel button pushed after inserting some coins
  	Given product with price 10 was chosen
    And coin 5 is put
    When cancel button is pushed    
    Then machine returns coin 5
    And machine does not release the product
    And machine displays message "Operation canceled!"

  Scenario: Cancel button pushed after choosing product but before inserting any coin
    Given product with price 10 was chosen
    When cancel button is pushed
    Then machine does not release the product
    And machine displays message "Operation canceled!"


  Scenario: Cancel button pushed before choosing product
    When cancel button is pushed
    Then machine does not release the product
    And machine displays message "Choose product!"

 
