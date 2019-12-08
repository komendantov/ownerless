@openMainPage to verifyCreateAccountSuccessMessage
Feature: Generated scenario
Scenario: openMainPage to verifyCreateAccountSuccessMessage
Given I create my first testGiven I open create account page
|country|accountType|
|default|default|
Given I fill account fields
|country|accountType|
|default|default|
Given I verify create account success message
|country|accountType|
|default|default|

