Feature: Calculate total price of content of a fruit basket

  Background: 
    Given I create a new basket
    And the unit price for "banana" is 1 pounds per kg
    And the unit price for "orange" is 2 pounds per kg
    And the unit price for "apple" is 3 pounds each
    And the unit price for "lemon" is 4 pounds each
    And the unit price for "peach" is 5 pounds each

  Scenario: Test empty basket total is 0
    Then I verify the total price of the basket is 0.0 pounds

  Scenario Outline: Test add 1 type of fruits
    When I add <quantity> "<fruit>" of <weight> kg to the basket
    Then I verify the total price of the basket is <total> pounds

    Examples:
      | quantity | fruit  | weight | total |
      | 1        | banana | 0.1    | 0.1   |
      | 2        | orange | 0.2    | 0.4   |
      | 3        | apple  | 0.3    | 9     |
      | 4        | lemon  | 0.4    | 16    |
      | 5        | peach  | 0.5    | 25    |
  
  # apple: 3 * 1, banana: 1 * 2, orange: 2 * 5.5, total: 16
  Scenario: Test add 3 type of fruits
    When I add 1 "apple" of 0.1 kg to the basket
    And I add 5 "banana" of 2 kg to the basket
    And I add 30 "orange" of 5.5 kg to the basket
    Then I verify the total price of the basket is 16 pounds
  
  # apple: 5 * 1, peach: 5 * 20, lemon: 1.5 * 1.5, total: 107.25
  Scenario: Test total calculation after unit price of fruit is updated
    When I add 1 "apple" of 0.1 kg to the basket
    And I add 5 "peach" of 2 kg to the basket
    And I add 10 "lemon" of 1.5 kg to the basket
    And I add 15 "peach" of 2 kg to the basket
    And I update the unit price of "apple" to 5 pounds each
    And I update the unit price of "lemon" to 1.5 pounds per kg
    Then I verify the total price of the basket is 107.25 pounds