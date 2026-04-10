# This is a GHERKIN feature file — it describes test scenarios in plain English.
#
# WHAT IS BDD (Behavior-Driven Development)?
#   BDD means writing tests in a language that everyone can read —
#   developers, testers, and even non-technical stakeholders.
#
# GHERKIN KEYWORDS:
#   Feature  → a group of related test scenarios (like a test suite)
#   Scenario → one specific test case
#   Given    → set up preconditions (what state the system starts in)
#   When     → perform an action (what the user does)
#   Then     → verify the result (what should happen)
#   And      → continues the previous Given/When/Then

Feature: User Login on SauceDemo

  # HAPPY PATH — tests that the correct credentials work
  Scenario: Successful login with valid credentials
    Given the user is on the SauceDemo login page
    When the user enters username "standard_user" and password "secret_sauce"
    And the user clicks the login button
    Then the user should be redirected to the products page

  # NEGATIVE TEST — wrong username and password should show an error
  Scenario: Login fails with invalid credentials
    Given the user is on the SauceDemo login page
    When the user enters username "wrong_user" and password "wrong_pass"
    And the user clicks the login button
    Then an error message "Username and password do not match" should be displayed

  # EDGE CASE — submitting the form without entering anything
  Scenario: Login fails with empty credentials
    Given the user is on the SauceDemo login page
    When the user clicks the login button without entering credentials
    Then an error message "Username is required" should be displayed

  # SPECIAL USER — SauceDemo has a "locked_out_user" that always fails
  Scenario: Locked out user cannot login
    Given the user is on the SauceDemo login page
    When the user enters username "locked_out_user" and password "secret_sauce"
    And the user clicks the login button
    Then an error message "Sorry, this user has been locked out" should be displayed
