Doodle-like REST API backed by Cassandra
===========

This project aims at supporting a lecture from [University of Fribourg](http://www.unifr.ch) about [Cassandra](http://cassandra.apache.org).

The goal of this exercise is to put hands-on and try Cassandra, [rest-assured](https://code.google.com/p/rest-assured/) for testing the REST API and a few other libraries in order to quickly build a REST API.
The interface [PollService](src/main/java/ch/noisette/doodle/service/PollService.java) define the functions to implement in Cassandra.

A [simple integration test](src/test/java/ch/noisette/doodle/test/RESTBasicTest.java) is creating a Poll, adding a few Subscribers and assert that everything is returned as expected.
And [in-memory implementation](src/main/java/ch/noisette/doodle/service/impl/InMemoryPollService.java) is proposed in order to have the integration test passing. An [empty class](src/main/java/ch/noisette/doodle/service/impl/PollService.java) should be implemented.

# Advised libraries

To have a chance to complete the exercise within a decent time, it is advised to rely on [Spring Boot](http://projects.spring.io/spring-boot/) for building fast and easy rest services and [Achilles](https://github.com/doanduyhai/Achilles) for dealing with Cassandra.

# REST API

## List the available [Polls](src/main/java/ch/noisette/doodle/entity/Poll.java)

```
GET /rest/polls
[  
   {  
      "id":"734969d1-99bb-4fba-b7fb-c68b9df48c04",
      "label":"Afterwork",
      "choices":[  
         "Monday",
         "Tuesday",
         "Friday"
      ],
      "email":"email@address.com",
      "maxChoices":1,
      "subscribers":null
   }
]
```

## Create a new [Poll](src/main/java/ch/noisette/doodle/entity/Poll.java)

```
POST /rest/polls
  {
    "label": "Afterwork",
    "choices": [ "Monday", "Tuesday", "Friday" ],
    "email": "benoit@noisette.ch"
  }
```

* Returns `pollId` in `Location` header

## Add a new [Subscriber](src/main/java/ch/noisette/doodle/entity/Subscriber.java)

```
PUT /rest/poll/<pollId>
  {
    "id": "f2fabfd7-1240-43c0-be5c-23274b087ac8",
    "label": "benoit",
    "choices": [ "Monday", "Friday" ]
  }
```

* Returns `subscriberId` in the `Location` header

## Retrieve some [Poll](src/main/java/ch/noisette/doodle/entity/Poll.java)

```
GET /rest/poll/<pollId>
```

* Returns a [Poll](src/main/java/ch/noisette/doodle/entity/Poll.java) 

```
  {
    "label": "Afterwork",
    "choices": [ "Monday", "Tuesday", "Friday" ],
    "email": "benoit@noisette.ch",
    "subscribers": [ { "name": "Benoit", "choices": [ "Monday", "Friday" ] }, ... ]
  }
```

# Advised Cassandra data model

Cassandra data model is rather flexible, and even simple modelization like this one have several options. 
The option presented here is the most convenient to use with [CQL 3](https://cassandra.apache.org/doc/cql3/CQL.html)

## Creating Keyspace

```
    CREATE KEYSPACE doodle WITH replication = {
        'class':'SimpleStrategy',
        'replication_factor':3
    };
```
 
## Creating Table

```
	CREATE TABLE doodle.poll(
		id uuid,
		subscribers list<text>,
		maxchoices int,
		label text,
		choices list<text>,
		email text,
		PRIMARY KEY(id))
	WITH comment = 'Create table for entity "ch.noisette.doodle.entity.Poll"'
```

## Insert a new Poll

```
	INSERT INTO doodle.poll(id, label, choices, email) VALUES ();
```

## Insert a new Subscriber

```
	UPDATE doodle.poll SET subscribers + '{}';
```

## Retrieve the Poll

```
	SELECT * FROM doodle.poll WHERE id = '';
```

# Clone it, Build it and Run it

## Clone it

```
git clone https://github.com/killerwhile/doodle-rest2.git
```

**Forking it is also encouraged.**

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

The test uses an in-memory db by default, the goal is to replace that with a Cassandra backed datastore.

## Modify it

The class [PollServiceImpl](src/main/java/ch/noisette/doodle/service/impl/PollServiceImpl.java) can be modified to implement the Cassandra part.

**Note: The project requires Java 7 at least.**

This class and all the project is provided for convenience, if you're familiar with other framework, feel free to write your own. The scenario described in [RESTBasicTest](src/test/java/ch/noisette/doodle/rest/RESTBasicTest.java) is the one asserting of the correctness of the implementation.

