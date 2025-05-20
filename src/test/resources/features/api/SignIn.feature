@api
Feature: US_2 - User Login

  This feature verifies the login functionality using valid, invalid,
  and missing credentials. It ensures appropriate responses,
  schema validation, and proper error handling.

  @smoke @positive
  Scenario Outline: Valid user login
    Given the user logs in with the following credentials:
      | email   | password   |
      | <email> | <password> |
    When the login request is sent to sign-in endpoint
    Then the response status code should be 200
    And the response should match the "signInResponseSchema" schema
    And the response should contain the "<username>" username

    Examples:
      | username       | email                       | password       |
      | tony stark     | tony_stark@gmail.com        | Doom@1234      |
      | kristin watson | kristin_watson@epam.com     | Pleasework@123 |
      | Money Man      | money@example.com           | Secure@123!    |


  @regression @negative
  Scenario Outline: Invalid login credentials
    Given the user logs in with the following credentials:
      | email   | password   |
      | <email> | <password> |
    When the login request is sent to sign-in endpoint
    Then the response status code should be 401
    And the response should match the "errorSchema" schema
    And the error message should be true
    And the error message should be "<message>"

#    This user is waiter
    Examples:
      | email                       | password       | message                    |
      | money@example.com           | Wr0ngPass456@  | Invalid email or password  |


  @regression @negative
  Scenario Outline: Missing login credentials
    Given the user logs in with the following credentials:
      | email   | password   |
      | <email> | <password> |
    When the login request is sent to sign-in endpoint
    Then the response status code should be 400
    And the response should match the "errorSchema" schema
    And the error message should be true
    And the error message should be "<message>"

    Examples:
      | email                | password        | message                   |
      |                      | SecurePass@123! | Invalid input: [email]    |
      | john.doe@example.com |                 | Invalid input: [password] |
      | money@example.com    | @empty          | Invalid input: [password] |