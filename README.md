# Agreement Overview API
The assignment is to build a REST API with a GET endpoint to provide
  - Aggregated product details of an user (/agreement-overview/{user})

There are some input API serving via mocks **To build and run mock:** use `mvn compile exec:java`.
Mocks service are available with **http://localhost:8080**:
  - Agreements of a customer (/agreements/{user})
  - Account details for a specific IBAN (/accounts/{accountId})
  - Debit card details (/debit-card/{cardId})
  - There are some errors, please check the JsonStub file

# Requirements
## Functional
  - Don't return blocked cards
  - Handle error case gracefully
  - Your endpoint should be fast

## Non-functional but very important
  - Please write clean and readable code
  - We suggest using Java 8 or higher, Spring-Boot & Maven ( or Gradle )
  - Make your code production ready
  - Please instruct us how to run your application

