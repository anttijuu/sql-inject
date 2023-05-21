# SQL injection

When learning database programming, What **you must not** do and what you **must** do instead (Java/JDBC).

![Bobby tables](xkcd.png)

See also [bobby-tables.com](https://bobby-tables.com).

This project has two database class implementations:

1. `BadDatabase` which does not do things as they should be done, and thus the app is vulnerable to SQL Injection attacks.
2. `GoodDatabase` which uses prepared statements to do database operations, and thus does not expose the app to SQL Injection attacks.

You may choose which of the implementations the app uses in the class `SQLInjectionApp` method `run()`. By default, it uses the bad one.

Additionally, `GoodDatabase` does encrypt the user passwords using salt and hash, contrary to `BadDatabase` saving the clear text passwords into the database.

It should be clear that you should learn to do these things correctly *from the very beginning* when learning database programming:

1. *Always* use prepared statements when doing database operations that use parameters.
2. *Always* save passwords using salting and hashing.

## Building

The project is using Maven so you can open it with any IDE that supports Maven and Java (Visual Studio Code, Eclipse) or build it from command line after installing a JDK (18+) and Maven.

Build from command line:

```console
mvn package
```
Run from command line:

```console
 java -jar target/sql-injection-1.0-SNAPSHOT-jar-with-dependencies.jar
 ```

 ## How to cause the problem?

1. Make sure the app uses the  `BadDatabase` implementation.
2. Launch the app
3. In the textboxes, the following data items are shown:
  - name
  - email
  - password
4. Input data for a new user, but in the email input box, enter after some text (e.g. asdf) and then:

> asdf');delete from user;--

5. Press the New button.

This SQL injection attack empties all data from the database.

## Who did this

* Antti Juustila
* Lecturer
* INTERACT Research Unit
* University of Oulu, Finland