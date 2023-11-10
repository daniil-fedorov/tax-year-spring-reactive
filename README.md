# tax-year-reactive-spring

Reactive Spring application with 2 endpoints to retrieve information from embedded MongoDB server based on video course
from O'Reilly

Usage:

1. There are 2 options to keep the application:
   either use RouterConfig + TaxYearHandler OR TaxYearController + TaxYearControllerExceptionHandler

2. Each one of options should do the same, however main differences are in the exception handling:

* Because TaxYearHandler is using ServerResponse as the return value for the methods, it doesn't need any additional
  exception handler class;
* On the other hand, TaxYearController requires additional ExceptionHandler class, which is introduced here, and helps
  to maintain all exceptions in the same place;

Possible play-around-s is to use ResponseStatusException instead of creating a handler, however it limits usage of
logger and other features, as exception handling is being covered by Spring 'under-the-hood'.
