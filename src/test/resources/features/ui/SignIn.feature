Feature: US_2 - User Sign-In Functionality

  Background:
    Given the user is on the landing page
    When the user clicks on the sign-in button on the landing page
    Then the user should be navigated to the Login page

  @smoke @ui
  Scenario: Successful login with valid credentials
    Given the user enters the valid credentials.
    When the user clicks the login button
    Then the user should be navigated to the Home page
    And the user is able to see the reservation section

  @regression @ui
  Scenario Outline: Unsuccessful login attempts with invalid credentials
    When the user attempts to login with invalid credentials:
      | email   | password   |
      | <email> | <password> |
    And the user clicks the login button
    Then the user should see the error message "<message>" for the "<field>" field

    Examples:
    | field     | email           | password        | message                                                         |
    | email     | @empty          | Secure@123      | Email address is required. Please enter your email to continue. |
    | password  | gafg@gmail.com  | @empty          | Password is required. Please enter your password to continue.   |
    | auth      | gafg@gmail.com  | uitestcaseidu   | Incorrect email or password. Try again or create an account     |
