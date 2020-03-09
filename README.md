# Agreement Overview API
The assignment is to build a REST API with a GET endpoint to provide
  - Aggregated product details of an user (/agreement-overview/{user})

There are some input API serving via mocks **To build and run mock:** use `mvn compile exec:java`
  - Agreements of a customer (/agreements/{user})
  - Account details for a specific IBAN (/accounts/{accountId})
  - Debit card details (/debit-card/{cardId})
  - Credit card details (/credit-card/{cardId})
  - There are some errors, please check the JsonStub file

# Requirements
## Functional
  - Don't return inactive products
  - Handle error case gracefully

## Non-functional but very important
  - Please write clean and readable code
  - We suggest using Java 8 or higher, Spring-Boot & Maven ( or Gradle )
  - Your code should have tests!
  - Think about performance optimization
  - Please instruct us how to run your application
  - Would be very helpful if we get a swagger file :)

## Extra for the daring
  - (Optional) Expose the API over HTTPS
  - (Optional) Dockerize your application

