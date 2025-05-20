@api
Feature: US_4 - View Restaurant Location Details

  This feature verifies that users can retrieve restaurant location info,
  including general details, speciality dishes, feedbacks, and popular dishes.
  It also validates filtering, sorting, and pagination for feedback data.

  @smoke @positive
  Scenario: Verify that general information about the locations is accessible
    When I send a GET request to locations
    Then the response status code should be 200
    And the response should match the "locationDetailsSchema" schema

  @smoke @positive
  Scenario Outline: Verify fetching of speciality dishes for a valid location
    When I send a GET request to fetch the speciality dishes for a location using its "<ID>".
    Then the response status code should be 200
    And the response should match the "locationSpecialityDishesSchema" schema

    Examples:
      | ID      |
      | loc-048 |
      | loc-049 |
      | loc-050 |

  @regression @negative
  Scenario Outline: Verify fetching of specialty dishes for an invalid location
    When I send a GET request to fetch the speciality dishes for a location using its "<ID>".
    Then the response status code should be 404
    And the response should match the "specialityErrorSchema" schema
    And the error message should be "<message>"

    Examples:
      | ID     | message               |
      | 081    | Location not found    |
      | asd    | Location not found    |
      | lo-091 | Location not found    |

  @smoke @positive
  Scenario Outline: Verify fetching of feedback for a restaurant location
    When I send a GET request to get the feedback for a location using it "<ID>"
    Then the response status code should be 200
    And the response should match the "feedbackListSchema" schema
    And the response should have the "<ID>" in body
    Examples:
      | ID      |
      | loc-048 |
      | loc-049 |
      | loc-050 |

  @smoke @positive
  Scenario Outline: Verify filtering of feedback data by type cuisine experience
    When I send a GET request to get the feedback for a location using it "<ID>" and by type as "cuisine_experience"
    Then the response status code should be 200
    And the response should match the "feedbackListSchema" schema
    And the response should only include feedbacks matching the type "CUISINE_EXPERIENCE"

    Examples:
      | ID      |
      | loc-048 |
      | loc-049 |
      | loc-050 |


  @smoke @positive
  Scenario Outline: Verify filtering of feedback data by type service quality
    When I send a GET request to get the feedback for a location using it "<ID>" and by type as "service_quality"
    Then the response status code should be 200
    And the response should match the "feedbackListSchema" schema
    And the response should only include feedbacks matching the type "SERVICE_QUALITY"

    Examples:
      | ID      |
      | loc-048 |
      | loc-049 |
      | loc-050 |

  @smoke @positive
  Scenario Outline: Verify feedbacks can be filtered with pagination and sorted by different fields
    When I send a GET request to get the feedback for a location "<ID>" with page "<page>", size "<size>" and sorted by "<sortField>" in "<sortOrder>" order
    Then the response status code should be 200
    And the response should match the "feedbackListSchema" schema
    And the response should return feedbacks sorted by "<sortField>" in "<sortOrder>" order

    Examples:
      | ID       | page | size | sortField | sortOrder |
      | loc-048  | 0    | 1    | date      | desc      |
      | loc-048  | 0    | 2    | date      | asc       |
      | loc-048  | 0    | 1    | rating    | asc       |
      | loc-048  | 0    | 2    | rating    | desc      |


  @smoke @positive
  Scenario: Verify the retrieval of the popular dishes using the /dishes/popular API
    When I send a GET request to popular dishes
    Then the response status code should be 200
    And the response should match the "popularDishes" schema
    And the response list have all the popular dishes