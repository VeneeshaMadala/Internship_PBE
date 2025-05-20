Feature: US_1 - User Sign-Up Functionality

  As a new customer
  The customer should be able to register successfully
  and see proper validation messages for invalid inputs

  Background:
    Given the customer is on the "Create an Account" page

  @smoke @ui
  Scenario: Successful registration with valid data
    When the customer provides registration details:
      | firstName | lastName | email     | password    | confirmPassword |
      | Johnson   | Doe      | @random   | Secure@123  | Secure@123      |
    And the customer clicks the "Create an Account" button
    Then the account should be successfully created


  @regression @ui
  Scenario Outline: Registration with missing required fields
    When the customer leaves the "<field>" field empty
    And the customer clicks the "Create an Account" button
    Then the customer should see a validation message for the "<field>" field
    Examples:
      | field            |
      | First Name       |
      | Last Name        |
      | Email            |

  @regression @ui
  Scenario Outline: Registration with invalid email format
    When the customer enters email "<email>"
    And the customer fills out the rest of the form with valid values
    And the customer clicks the "Create an Account" button
    Then an error message "<errorMessage>" should be displayed for the email field
    Examples:
      | email              | errorMessage                                                                |
      | johnsonexample.com | Invalid email address. Please use domain.com, gmail.com, or epam.com        |
      | @gmail.com         | Invalid email address. Please use domain.com, gmail.com, or epam.com        |

  @regression @ui
  Scenario Outline: Registration with weak password
    When the customer enters password "<password>"
    And the customer clicks the "Create an Account" button
    Then the customer should see password validation as "<message>"

    Examples:
      | password      | message |
      | abc           | ● Weak  |
      | password123   | ● Weak  |
      | 12345678      | ● Weak  |

  @regression @ui
  Scenario Outline: Registration with mismatched confirm password
    When the customer enters password "<password>"
    And the customer enters a different confirm password "<confirmPassword>"
    And the customer clicks the "Create an Account" button
    Then an error message "<message>" should be displayed for the confirm password field
    Examples:
      | password       | confirmPassword | message                                      |
      | Y2kjqKHX!abcd  | Xx123456!       | ● Confirm password must match new password   |
      | StrongPass123! | Different123!   | ● Confirm password must match new password   |
