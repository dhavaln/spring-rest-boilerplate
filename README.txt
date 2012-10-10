To run the integration tests included in this example with Maven use:

mvn verify

To deploy the web service on a jetty use:

mvn jetty:run

The web service is now available on http://localhost:9090

For test

1) Call http://localhost:9090/spring-rest-example/login with username, password and token (=test) parameter and it will create the security context
