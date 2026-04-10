# Shopping Cart feature — tests adding and removing products from the cart.
#
# Notice how scenarios build on top of each other:
#   - First scenario:  adds ONE product
#   - Second scenario: adds a product, then REMOVES it
#   - Third scenario:  adds MULTIPLE products

Feature: Shopping Cart Management

  # Verifies the cart badge updates when a product is added
  Scenario: Add a product to the cart
    Given the user is logged in as "standard_user"
    When the user adds "Sauce Labs Backpack" to the cart
    Then the cart badge should display "1"

  # Full round-trip: add → verify → remove → verify empty
  Scenario: Remove a product from the cart
    Given the user has "Sauce Labs Backpack" in the cart
    When the user removes "Sauce Labs Backpack" from the cart
    Then the cart should be empty

  # Verifies the cart correctly counts multiple products
  Scenario: Add multiple products to the cart
    Given the user is logged in as "standard_user"
    When the user adds "Sauce Labs Backpack" to the cart
    And the user adds "Sauce Labs Bike Light" to the cart
    Then the cart badge should display "2"
