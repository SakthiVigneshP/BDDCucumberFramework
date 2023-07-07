@login
Feature: Login the application
  I want to login to the application

  @loginApp
  Scenario Outline: Login the application
    Given launch the application
    When application launched
    Then login with "<username>" and "<password>"
    Then check login success or not

    Examples: 
      |username|password| 
      |standard_user|secret_sauce|


  @loginApp2
  Scenario Outline: Login the application2
    Given launch the application
    When application launched
    Then login with "<username>" and "<password>"
    Then check login success or not

    Examples:
      |username|password|
      |locked_out_user|secret_sauce|