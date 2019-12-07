@allTestScenario
Feature: All steps flow
  Scenario: All steps scenario

  Given I verify page product sticker
  Then I verify product details
    Then I verify product details elements
    When I add product to cart
    Given I create account
    When I sign out
    When I sign in
    When I sign out