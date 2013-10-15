Feature: Empty slot chosen

  Scenario: empty slot chosen
  	When empty slot chosen
    Then machine displays message "Product not available"

  Scenario: invalid slot chosen
    When invalid slot chosen
    Then machine displays message "Invalid slot chosen"

  Scenario: empty slot chosen and coin inserted
    Given empty slot chosen
    When coin 5 is put
    Then machine returns coin 5
    And machine displays message "Choose product!"