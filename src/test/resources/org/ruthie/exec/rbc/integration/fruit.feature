Feature: Integration test for fruit type management

  Background:
    Given the fruit type "banana" is valid
    And the fruit type "orange" is valid
    And the fruit type "apple" is valid
    And the fruit type "lemon" is valid
    And the fruit type "melon" is not valid
    And the fruit type "kiwi" is not valid

  Scenario: Test update a fruit type works
    When I update the unit price of "apple" to 20.5 pounds each
    Then I verify the unit price of "apple" is 20.5 pounds each
    
  Scenario: Test update a non existent fruit type fails
    When I update the unit price of "melon" to 2 pounds each
    Then resource not found exception is thrown