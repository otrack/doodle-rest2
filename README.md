Doodle-like REST API backed by Cassandra
===========

This project aims at supporting a lecture from [University of Fribourg](http://www.unifr.ch) about [Cassandra](http://cassandra.apache.org).

The goal of this exercise is to put hands-on and try Cassandra, [rest-assured](https://code.google.com/p/rest-assured/) for testing the REST API and a few other libraries in order to quickly build a REST API.
The interface [PollService](src/main/java/ch/noisette/doodle/service/PollService.java) define the functions to implement in Cassandra.

A [simple integration test](src/test/java/ch/noisette/doodle/test/RESTBasicTest.java) is creating a Poll, adding a few Subscribers and assert that everything is returned as expected.
And [in-memory implementation](src/main/java/ch/noisette/doodle/service/impl/InMemoryPollService.java) is proposed in order to have the integration test passing. An [empty class](src/main/java/ch/noisette/doodle/service/impl/PollService.java) should be implemented.

# REST API

## Create a new [Poll](src/main/java/ch/noisette/doodle/entity/Poll.java)

```
POST /rest/polls
  {
    "label": "Afterwork",
    "choices": [ "Monday", "Tuesday", "Friday" ],
    "email": "benoit@noisette.ch"
  }
```

* Returns <pollId> in `Location` header

## Add a new [Subscriber](src/main/java/ch/noisette/doodle/entity/Subscriber.java)

```
PUT /rest/polls/<pollId>
  {
    "name": "Benoit", 
    "choices": [ "Monday", "Friday" ]
  }
```

* Returns <subscriberId> in the `Location` header

## Retrieve the Poll

```
GET /rest/polls/<pollId>
```

* Returns
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
