Feature: Example file to ensure it setup correctly

  Scenario Outline:
    Given I have numbers <x> and <y>
    When I <"action"> them
    Then I expect the result to be <z>

    Examples:
      | x | y | "action" | z  |
      | 1 | 2 | add      | 3  |
      | 1 | 9 | add      | 10 |
      | 1 | 2 | minus    | -1 |
      | 2 | 1 | minus    | 1  |