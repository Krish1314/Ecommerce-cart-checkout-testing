# Checkout Flow feature — tests the end-to-end purchase process.
#
# This is the most critical user journey:
#   Login → Add to cart → Checkout → Enter details → Verify → Confirm order
#
# Includes both happy path and validation testing.

Feature: Checkout Flow

  # HAPPY PATH — complete purchase from start to finish
  Scenario: Successful checkout with valid details
    Given the user has "Sauce Labs Backpack" in the cart
    When the user proceeds to checkout
    And the user enters first name "Krish", last name "Patel", zip "411001"
    And the user clicks Continue and then Finish
    Then the order confirmation message "Thank you for your order!" should be displayed

  # VALIDATION TEST — required field left empty should block checkout
  Scenario: Checkout fails with missing first name
    Given the user has "Sauce Labs Backpack" in the cart
    When the user proceeds to checkout
    And the user leaves first name empty, enters last name "Patel", zip "411001"
    And the user clicks Continue
    Then an error message "First Name is required" should be displayed

  # DATA VERIFICATION — checks that the price on checkout matches the product page
  Scenario: Verify order total on checkout overview
    Given the user has "Sauce Labs Backpack" in the cart
    When the user proceeds to checkout and fills in valid details
    Then the checkout overview should display the correct item total
