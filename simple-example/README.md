# Simple Example
This shows an example of a quick and dirty implementation for API testing using Rest-Assured, Hoverfly, and Junit5. The 
mocks would be removed once the service had been deployed into a testable state.

## ExampleApiTest
An example of a standalone test suite that can be created quickly. Each test is self-contained, creating the mocks that 
it needs and the request it is testing. This is suitable for a small API with only a few endpoints or functionality, or 
a temporary test used for exploratory testing or demonstration.

## RefactoredExampleApiTest
This is an example of starting a simple framework.  The environment variables have been moved out and use a utility to 
read a property file.  The individual test mocks have been moved out into the start of a framework and the tests are no 
longer aware of what library the mock uses.  The Rest-Assured request code has been abstracted into services to help with 
the readability of the code.  The JsonPath aspect of Rest-Assured remains as it remains readable when used in the 
assertions but these can also be moved into as shown with the **getFieldForSalesId** verification.