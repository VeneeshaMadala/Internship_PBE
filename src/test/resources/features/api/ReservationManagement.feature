@api
Feature: US_6 - Table Reservation Management by Customer

  This feature describes the scenarios involving table reservations made by customers.
  It covers positive and negative user journeys, ensuring functionality such as reservation
  creation, cancellation, error handling for invalid scenarios, and prevention of duplicate
  bookings are thoroughly tested.

  Background:
    Given a registered customer logs in with valid credentials
    And the customer has a valid access token

  @smoke @positive
  Scenario: Customer successfully reserves a table
    When the customer makes a reservation with:
      | locationId  | tableNumber | date        | guestsNumber  | timeFrom  | timeTo |
      | loc-051     | 1           | 2025-04-31  | 4             | 14:00     | 15:00  |
    And the user sends a reservation request with the payload and a valid token.
    Then the response status code should be 200
    And the response should match the "reservationSchema" schema
    And the reservation date, number of guests, and time slot in the response should match the request
    And the reservation status should be "Confirmed"
    And the user cancels the reservation

  @regression @negative
  Scenario Outline: Book table with various valid and invalid data combinations
    When the customer makes a reservation with:
      | locationId    | tableNumber | date       | guestsNumber | timeFrom | timeTo |
      | <locationId>  | <tableNumber> | <date> | <guestsNumber> | <timeFrom> | <timeTo> |
    And the user sends a reservation request with the payload and a valid token.
    Then the response status code should be <statusCode>
    And the response should match the "errorSchema" schema
    And the error message should be true
    And the error message should be "<message>"

    Examples:
      | locationId | tableNumber | date       | guestsNumber | timeFrom | timeTo | statusCode     | message                                                   |
      | loc-100    | 1           | 2025-05-01 | 4            | 14:00    | 15:30  | 404            | Table not found for the given location and table number.  |
      | loc-051    | 100         | 2025-05-01 | 4            | 14:00    | 15:30  | 404            | Table not found for the given location and table number.  |
      | loc-051    | 1           | 2022-01-01 | 4            | 14:00    | 15:30  | 400            | Date cannot be in the past                                |
      | loc-051    | 1           | 2025-05-01 | 100          | 14:00    | 15:00  | 400            | Number of guests cannot exceed 20                         |
      | loc-051    | 1           | 2025-05-01 | 4            | 14:00    | 12:00  | 400            | End time must be after start time                         |

  @smoke @negative
  Scenario: Customer attempts to reserve a table without an access token
    When the customer makes a reservation with:
      | locationId  | tableNumber | date        | guestsNumber  | timeFrom  | timeTo |
      | loc-051     | 1           | 2025-04-31  | 4             | 14:00     | 15:00  |
    And the customer attempts to access the reservation form without access token
    Then the error message should be "Unauthorized"

  @smoke @positive
  Scenario: Successfully get reservations list
    When the customer makes a reservation with:
      | locationId  | tableNumber | date        | guestsNumber  | timeFrom  | timeTo |
      | loc-051     | 1           | 2025-05-10   | 4            | 14:00     | 15:00  |
    And the user sends a reservation request with the payload and a valid token.
    When user send a GET request to reservations endpoint with token
    Then the response status code should be 200
    And the response should match the "reservationsListSchema" schema
    And the reservation we created should be present in the list
    And the user cancels the reservation

  @smoke @positive
  Scenario: Prevent overbooking for the same table and time
    When the customer makes a reservation with:
      | locationId  | tableNumber | date        | guestsNumber  | timeFrom  | timeTo |
      | loc-051     | 1           | 2025-04-31  | 4             | 14:00     | 15:00  |
    And the user sends a reservation request with the payload and a valid token.
    And the customer attempts to reserve the same table at the same time again
    Then the response status code should be 409
    And the response should match the "errorSchema" schema
    And the error message should be true
    And the error message should be "The requested time slot is not available for the selected table."
    And the user cancels the reservation

  @smoke @positive
  Scenario: Cancel a reservation successfully
    When the customer makes a reservation with:
      | locationId  | tableNumber | date        | guestsNumber  | timeFrom  | timeTo |
      | loc-051     | 1           | 2025-05-15  | 4             | 14:00     | 15:00  |
    And the user sends a reservation request with the payload and a valid token.
    When the user cancels the reservation
    Then the response status code should be 200
    And the error message should be "Reservation cancelled successfully and slots freed up."

