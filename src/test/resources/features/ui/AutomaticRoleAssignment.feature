Feature: US_3 - Automatic Role Assignment After Login

  As a system
  I want to assign roles (Customer, Waiter, Admin) to users upon login
  So that each user accesses the appropriate functionalities

  Background:
    Given the user is on the landing page
    When the user clicks on the sign-in button on the landing page


  @smoke @ui
  Scenario: Assign correct role after successful login
    And the user should be navigated to the Login page
    When the user attempts to login with given credentials:
      | email                   | password        |
      | kristin_watson@epam.com | Pleasework@123  |
    And the user clicks the login button
    Then the user should have the role "kristin watson (customer)"

  @smoke @ui
  Scenario: Assign waiter role after successful login
    And the waiter should be navigated to the Login page
    When the waiter attempts to login with given credentials:
      | email                   | password      |
      | money@example.com       | Secure@123!   |
    And the waiter clicks the login button
    Then the waiter should have the role "Money Man (waiter)"

  @regression @ui
  Scenario: Default role is Customer when registering a new user
    And the customer is navigated to the sign-up page
    When the customer provides registration details:
      | firstName | lastName | email     | password    | confirmPassword |
      | Johnson   | Doe      | @random   | Secure@123  | Secure@123      |
    And the customer clicks the "Create an Account" button
    Then the account should be successfully created
    When the user logs in with the registered credentials
    Then the user should see their role as "customer"







