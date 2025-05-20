@api
Feature: US_3 - Automatic Role Assignment on Registration and Login

  This feature ensures that users receive the correct role
  in the login response based on their account.

  @smoke @positive
  Scenario Outline: Verify role in login response
    Given the user logs in with the following credentials:
      | email           | password         |
      | <email>         | <password>       |
    When the login request is sent to sign-in endpoint
    Then the response status code should be 200
    And the response should match the "signInResponseSchema" schema
    And the response should contain the "<role>" role

    Examples:
      | role       | email                       | password       |
      | admin      | tony_stark@gmail.com        | Doom@1234      |
      | customer   | kristin_watson@epam.com     | Pleasework@123 |
      | waiter     | money@example.com           | Secure@123!    |


