Feature: View Restaurant Details

  As a customer or visitor
  User want to explore restaurant location details, ratings, and reviews
  So that user can make an informed decision about visiting or reserving a table

  Background:
    Given User is on the landing page

  @regression @positive @ui
  Scenario Outline: Restaurant location has required information
    When User scroll to the Locations section
    Then the location card for "<Address>" should show capacity and occupancy

    Examples:
      | Address                     |
      | 50 Sattva Avenue, Madhapur  |
      | 51 Rustaveli Avenue, Patna  |
      | 49 Rustaveli Avenue, Kharar |

  @smoke @positive @ui
  Scenario Outline: Clicking on a location opens its detailed page
    When User clicks on the following locations
      | locationName   |
      | <locationName> |
    Then User should be redirected to the Location Overview page for "<locationID>"
    And User should see restaurant details including:
      | Rating      |
      | Photo       |
      | Description |
    Examples:
      | locationName                | locationID |
      | 50 Sattva Avenue, Madhapur  | loc-050    |
      | 51 Rustaveli Avenue, Patna  | loc-051    |
      | 49 Rustaveli Avenue, Kharar | loc-049    |


  @smoke @positive @ui
  Scenario Outline: Home page shows key navigation elements
    And User clicks on the following locations
      | locationName   |
      | <locationName> |
    When User is on the restaurant page of "<locationName>"
    Then User should see the following menu items:
      | Logo         |
      | Main Page    |
      | Book a Table |
      | Sign In      |

    Examples:
      | locationName                |
      | 50 Sattva Avenue, Madhapur  |
      | 51 Rustaveli Avenue, Patna  |
      | 49 Rustaveli Avenue, Kharar |


  @regression @positive @ui
  Scenario Outline: All specialty dishes display price and weight
    When User clicks on the following locations
      | locationName   |
      | <locationName> |
    Then Each specialty dish card should have name, price, and weight
    Examples:
      | locationName                |
      | 50 Sattva Avenue, Madhapur  |
      | 49 Rustaveli Avenue, Kharar |
      | 48 Rustaveli Avenue         |

  @regression @positive @ui
  Scenario Outline: Customer can filter and view reviews by experience type
    When User clicks on the following locations
      | locationName   |
      | <locationName> |
    Then Reviews should be visible on the following experience tabs:
      | service        |
      | cuisine        |

    Examples:
      | locationName                |
      | 49 Rustaveli Avenue, Kharar |
      | 48 Rustaveli Avenue         |

  @regression @positive @ui
  Scenario Outline: Customer can sort reviews by rating and date
    When User clicks on the following locations
      | locationName               |
      | 50 Sattva Avenue, Madhapur |
    And User sort reviews using "<sortOption>"
    Then reviews should appear sorted by "<sortOption>"

    Examples:
      | sortOption        |
      | Top rated first   |
      | Low rated first   |
      | Oldest first      |
      | Newest first      |

  @regression @negative @ui
  Scenario: No specialty dishes message when none are configured
    When User clicks on the following locations
      | locationName               |
      | 51 Rustaveli Avenue, Patna |
    Then User should see a message "No speciality dishes found."


