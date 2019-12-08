@openMainPage to verifyCartIsEmpty
Feature: Generated scenario
Scenario: openMainPage to verifyCartIsEmpty
Given I create my first testGiven I open cart page by checkout
|country|accountType|
|default|default|
Given I remove all from cart
|country|accountType|
|default|default|
Given I verify cart is empty
|country|accountType|
|default|default|

