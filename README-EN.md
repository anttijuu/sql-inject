# SQL injection

When learning database programming, there are things that **you must not** do. 

One of those is to avoid [SQL injection](https://en.wikipedia.org/wiki/SQL_injection). That is the main point of this simple example repository, implemented in Java/JDBC, using SQLite as the database. Another thing demonstrated here is how to save passwords salted and encrypted.

![Bobby tables](xkcd.png)

*Link:* [xkcd](https://xkcd.com/327/). See also [bobby-tables.com](https://bobby-tables.com).

This project has two database class implementations:

1. `BadDatabase` which does not do things as they should be done, and thus the app is vulnerable to SQL Injection attacks.
2. `GoodDatabase` which uses prepared statements to do database operations, and thus does not expose the app to SQL Injection attacks.

You may choose which of the implementations the app uses in the class `SQLInjectionApp` method `run()`. By default, it uses the bad one.

Additionally, `GoodDatabase` does encrypt the user passwords using salt and hash, contrary to `BadDatabase` saving the clear text passwords into the database.

It should be clear that you should learn to do these things correctly *from the very beginning* when learning database programming:

1. *Always* use prepared statements when doing database operations that use parameters.
2. *Always* save passwords using salting and hashing.

## Dependencies

For building and running, this app needs:

* SQLite; install it if your system does not already have it.
* `org.xerial.sqlite-jdbc` JDBC driver for SQLite, already specified in the `pom.xml`.
* Apache `commons-codec`, which is also specified in the `pom.xml`.
* Java Developer Kit (JDK) version 18 or newer.
* Maven.


## Building

The project is using Maven so you can open it with any IDE that supports Maven and Java (Visual Studio Code, Eclipse, IntelliJ IDEA) or build it from command line after installing a JDK (18+) and Maven.

Build from command line:

```console
mvn package
```
Run from command line:

```console
 java -jar target/sql-injection-1.0-SNAPSHOT-jar-with-dependencies.jar
 ```

 ## How to cause the SQL injection problem?

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