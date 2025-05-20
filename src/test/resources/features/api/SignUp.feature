@api
Feature: US_1 - User Sign-Up

  This feature tests the sign-up API with valid and invalid data.
  It covers successful registration, input validations,
  duplicate email handling, and field constraints.

  @smoke @positive
  Scenario Outline: Successful user registration
    Given the user enters the following registration details:
      | firstName   | lastName   | email   | password   |
      | <firstName> | <lastName> | <email> | <password> |
    When the user sends a POST request to sign-up endpoint
    Then the response status code should be 201
    And the response should match the "signUpResponseSchema" schema
    And the response message should be "<message>"

    Examples:
      | firstName   | lastName   | email     | password        | message                      |
      | random      | man        | @random   | SecurePass@123! | User registered successfully |

  @regression @negative
  Scenario Outline: Sign-up with missing required fields
    Given the user enters the following registration details:
      | firstName   | lastName   | email   | password   |
      | <firstName> | <lastName> | <email> | <password> |
    When the user sends a POST request to sign-up endpoint
    Then the response status code should be 400
    And the response should match the "errorSchema" schema
    And the error message should be true
    And the error message should be "<message>"

    Examples:
      | firstName | lastName | email              | password        | message                    |
      |           | Doe      | john@email.com     | SecurePass@123! | Invalid input: [firstName] |
      | John      |          | john@email.com     | SecurePass@123! | Invalid input: [lastName]  |
      | John      | Doe      |                    | SecurePass@123! | Invalid input: [email]     |
      | John      | Doe      | john@email.com     |                 | Invalid input: [password]  |

  @regression @negative
  Scenario Outline: Sign-up with empty fields
    Given the user enters the following registration details:
      | firstName   | lastName   | email   | password   |
      | <firstName> | <lastName> | <email> | <password> |
    When the user sends a POST request to sign-up endpoint
    Then the response status code should be 400
    And the response should match the "errorSchema" schema
    And the error message should be true
    And the error message should be "<message>"

    Examples:
      | firstName | lastName | email          | password        | message                      |
      | @empty    | Doe      | john@email.com | SecurePass@123! | Invalid input: [firstName]   |
      | John      | @empty   | john@email.com | SecurePass@123! | Invalid input: [lastName]    |
      | John      | Doe      | @empty         | SecurePass@123! | Invalid input: [email]       |
      | John      | Doe      | john@email.com | @empty          | Invalid input: [password]    |

  @regression @negative
  Scenario Outline: Sign-up with invalid email format
    Given the user enters the following registration details:
      | firstName   | lastName   | email   | password   |
      | <firstName> | <lastName> | <email> | <password> |
    When the user sends a POST request to sign-up endpoint
    Then the response status code should be 400
    And the response should match the "errorSchema" schema
    And the error message should be true
    And the error message should be "<message>"

    Examples:
      | firstName | lastName | email                | password        | message                |
      | John      | Doe      | invalid-email        | SecurePass@123! | Invalid input: [email] |
      | Jane      | Smith    | email@               | SecurePass@123! | Invalid input: [email] |
      | Alice     | Brown    | @domain.com          | SecurePass@123! | Invalid input: [email] |
      | Bob       | White    | bob@domain@domain.com| SecurePass@123! | Invalid input: [email] |

  @regression @negative
  Scenario Outline: Sign-up with invalid password formats
    Given the user enters the following registration details:
      | firstName   | lastName   | email   | password   |
      | <firstName> | <lastName> | <email> | <password> |
    When the user sends a POST request to sign-up endpoint
    Then the response status code should be 400
    And the response should match the "errorSchema" schema
    And the error message should be "<message>"

    Examples:
      | firstName | lastName | email                 | password        | message                   |
      | John      | Doe      | john@example.com      | securepass@123  | Invalid input: [password] |
      | Jane      | Smith    | jane@example.com      | STR0NGPASS@     | Invalid input: [password] |
      | Bob       | Coach    | bob@example.com       | Password@       | Invalid input: [password] |
      | Alice     | Trainer  | alice@example.com     | Password123     | Invalid input: [password] |
      | Short     | Name     | short@example.com     | Short1@         | Invalid input: [password] |

  @regression @positive
  Scenario: Sign-up with an already registered email
    Given the user enters the following registration details:
      | firstName   | lastName   | email   | password    |
      | random      | man        | @random | Secure@123! |
    When the user sends a POST request to sign-up endpoint
    Then the response status code should be 201
    And the response message should be "User registered successfully"

    When the same user attempts to register again with the same email
    Then the response status code should be 409
    And the response should match the "errorSchema" schema
    And the error message should be true


  @regression @negative
  Scenario Outline: Sign-up with fields exceeding max character limits
    Given the user enters the following registration details:
      | firstName   | lastName   | email   | password   |
      | <firstName> | <lastName> | <email> | <password> |
    When the user sends a POST request to sign-up endpoint
    Then the response status code should be 400
    And the response should match the "errorSchema" schema
    And the error message should be true
    And the error message should be "<message>"

    Examples:
      | firstName                                                   | lastName                                                              | email                      | password        | message                         |
      | JohnWithVeryLongFirstnameThatExceedsFiftyCharacters         | Doe                                                                   | random1@example.com        | SecurePass@123! | Invalid input: [firstName]      |
      | John                                                        | LastnameExceedingFiftyCharacterLimitllllllllllllLong                  | random2@example.com        | SecurePass@123! | Invalid input: [lastName]       |
