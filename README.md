Doodle-like REST API backed by Cassandra, used for Fribourg's University Advanced Databases System Course
----

# Homework

The goal of the exercice is to build a REST API that will be able to create a poll, subscribe to the poll and retrieve the subsciptions.

In REST notation:

## Create a new poll

    POST /rest/poll
    { "label": "Afterwork", "choices": [ "Monday", "Tuesday", "Friday" ], "email": "benoit@noisette.ch" }

Returns ``pollId`` (in Location header)

## Subscribe to the poll

	PUT /rest/poll/<pollId>
	{ "name": "Benoit", "choices": [ "Monday", "Friday" ] }

Returns the updated poll, JSON encoded (see below)

	GET /rest/poll/<pollId>

Returns the poll, JSON encoded

	{ "label": "Afterwork", "choices": [ "Monday", "Tuesday", "Friday" ], "email": "benoit@noisette.ch", "subscribers": [ { "name": "Benoit", "choices": [ "Monday", "Friday" ] }, ... ] }

# Clone it, Build it and Run it

The goal of the project is to be done within a 2 hours lab exercice. It relies then on [Spring Boot](http://projects.spring.io/spring-boot/) 
for building fast and easy rest services, and [Achilles](https://github.com/doanduyhai/Achilles) for dealing with Cassandra.

## Clone it

```
git clone https://github.com/killerwhile/doodle-rest2.git
```

**Cloning it is also encouraged.**

## Build it

```
mvn clean install
```

To generate Eclipse project, simply run

```
mvn eclipse:eclipse 
```

Or import it directly in your favorite IDE as a Maven project.

## Run it

There's a complete unit test that could be run as is: [RESTBasicTest](src/test/java/ch/noisette/doodle/rest/RESTBasicTest.java)

## Modify it

This will build the project, and run a test. The test uses an in-memory db, the goal is to replace that with a Cassandra backed datastore.

**Note: The project requires Java 7 at least.**


