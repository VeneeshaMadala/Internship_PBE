@api
Feature: US_5 - View Available Tables for Reservation

  This feature verifies the functionality of the "View Available Tables" API,
  which allows users to check table availability at different restaurant locations
  based on specific input criteria such as location ID, date, time, and number of
  guests. The tests cover positive cases for valid inputs and negative cases for
  invalid query parameters, ensuring the API handles both success and error scenarios
  gracefully.

  @smoke @positive
  Scenario: Verify the API returns a list of locations successfully
    When I send a GET request to get list of locations
    Then the response status code should be 200
    And the response should match the "locationsSchema" schema
    And the response should contain a list of locations with id and address fields


  @smoke @positive
  Scenario Outline: Verify that the API returns the list of available tables for valid input
    When I send a GET request to the booking tables endpoint with the following query parameters:
      | locationId   | date       | time   | guests   |
      | <locationId> | <date>     | <time> | <guests> |

    Then the response status code should be 200
    And the response should match the "viewAvailableTablesSchema" schema
    And the response should be having the locationId as "<locationId>"

    Examples:
      | locationId | date       | time  | guests |
      | loc-048    | 2025-04-30 | 12:00 | 4      |
      | loc-049    | 2025-05-05 | 14:00 | 3      |
      | loc-050    | 2025-05-06 | 16:00 | 2      |

  @regression @negative
  Scenario Outline: Verify filtering by location and invalid locationId returns error message
    When I send a GET request to the booking tables endpoint with the following query parameters:
      | locationId   | date       | time   | guests   |
      | <locationId> | <date>     | <time> | <guests> |
    Then the response status code should be 404
    And the response should match the "errorSchema" schema
    And the error message should be true
    And the error message should be "<message>"

    Examples:
      | locationId | date       | time  | guests | message                               |
      | dgdsdsd    | 2025-04-30 | 12:00 | 4      | Location not found with id: dgdsdsd   |
      | 09         | 2025-05-05 | 14:00 | 5      | Location not found with id: 09        |
      | null       | 2025-05-10 | 16:00 | 8      | Location not found with id: null      |


  @regression @negative
  Scenario Outline: Verify filtering by location and invalid date returns error message
    When I send a GET request to the booking tables endpoint with the following query parameters:
      | locationId   | date       | time   | guests   |
      | <locationId> | <date>     | <time> | <guests> |
    Then the response status code should be 400
    And the response should match the "errorSchema" schema
    And the error message should be true
    And the error message should be "<message>"

    Examples:
      | locationId | date       | time  | guests | message                                             |
      | loc-048    | 20250430   | 12:00 | 4      | Invalid date format. Please use YYYY-MM-DD format   |
      | loc-049    | safsdgfsfs | 14:00 | 5      | Invalid date format. Please use YYYY-MM-DD format   |
      | loc-050    | @empty     | 16:00 | 8      | Date cannot be empty                                |

  @regression @negative
  Scenario Outline: Verify filtering by invalid time returns error message
    When I send a GET request to the booking tables endpoint with the following query parameters:
      | locationId   | date       | time   | guests   |
      | <locationId> | <date>     | <time> | <guests> |
    Then the response status code should be 400
    And the response should match the "errorSchema" schema
    And the error message should be true
    And the error message should be "<message>"

    Examples:
      | locationId | date       | time    | guests | message                                                  |
      | loc-048    | 2025-04-30 | 1200    | 4      | Invalid time format. Please use HH:MM format (24-hour)   |
      | loc-049    | 2025-05-05 | vgsdd   | 5      | Invalid time format. Please use HH:MM format (24-hour)   |
      | loc-050    | 2025-05-10 | @empty  | 8      | Time cannot be empty                                     |


  @regression @negative
  Scenario Outline: Verify filtering by invalid guest count returns error message
    When I send a GET request to the booking tables endpoint with the following query parameters:
      | locationId   | date       | time   | guests   |
      | <locationId> | <date>     | <time> | <guests> |
    Then the response status code should be 400
    And the response should match the "errorSchema" schema
    And the error message should be true
    And the error message should be "<message>"

    Examples:
      | locationId | date       | time    | guests | message                                  |
      | loc-048    | 2025-04-30 | 12:00   | @empty | Number of guests cannot be empty         |
      | loc-049    | 2025-05-05 | 14:00   | sdsf   | Number of guests must be a valid number  |
      | loc-050    | 2025-05-10 | 16:00   | -1     | Number of guests must be positive        |

