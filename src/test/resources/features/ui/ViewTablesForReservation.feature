@ui
Feature: US_5 View Available Tables for Reservation

  Background:
    Given the user is on the landing page
    And the user signs up and logs in with email "venmad@gmail.com" and password "Venmad123@"
    Then the user is redirected back to the landing page
    When the user navigates to the Table Search page

  @regression @positive @ui
  Scenario Outline: Successfully view available tables with valid inputs
    Given User is on the Table Search page
    When User selects location "<locationName>"
    And User sets guest count to <guestCount>
    And User selects timeslot "<date>" "<time>"
    Then User clicks on Find Table button
    And User should see a list of available tables

    Examples:
      | locationName                | guestCount | date       | time  |
      | 50 Sattva Avenue, Madhapur  | 2          | 05-05-2025 | 18:00 |
#      | 51 Rustaveli Avenue, Patna  | 4          | 2025-05-05 | 19:30 |
#      | 49 Rustaveli Avenue, Kharar | 10         | 2025-05-05 | 21:00 |

  @regression @negative @ui
  Scenario: Try viewing tables without selecting location
    Given User is on the Table Search page
    When User sets guest count to 2
    And User selects timeslot "05-05-2025" "18:00"
    Then User clicks on Find Table button
    And User should see an error message "Please select a location to proceed"

  @regression @negative @ui
  Scenario: Try viewing tables without filling timeslot fields
    Given User is on the Table Search page
    When User selects location "49 Rustaveli Avenue, Kharar"
    And User sets guest count to 10
    Then User clicks on Find Table button
    And User should see an error message "Please provide a valid timeslot"