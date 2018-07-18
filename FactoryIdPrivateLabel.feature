Feature: Tests private label and factory id combinations

Scenario: When private label is yes, and no factory id is sent an error message should present in response
When private label yes, and no factory id is sent while creating stage item
Then valid error message should be sent for factory id