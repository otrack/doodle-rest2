package ch.noisette.doodle.service.impl;

import ch.noisette.doodle.entity.Poll;
import ch.noisette.doodle.entity.Subscriber;
import ch.noisette.doodle.service.PollService;
import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import info.archinnov.achilles.internal.utils.UUIDGen;
import info.archinnov.achilles.json.DefaultJacksonMapper;
import info.archinnov.achilles.persistence.PersistenceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by bperroud on 11-Nov-14.
 */
@Service
@EnableAutoConfiguration
public class PollServiceImpl implements PollService {

    @Autowired
    private PersistenceManager persistenceManager;

    boolean avoidReadBeforeWrite = false;

    @Override
    public Poll getPollById(String pollId) {

        Poll poll = persistenceManager.find(Poll.class, UUID.fromString(pollId));

        return poll;
    }

    @Override
    public List<Poll> getAllPolls() {
        return null;
    }

    @Override
    public Poll createPoll(Poll poll) {

        poll.setId(UUID.randomUUID());
        persistenceManager.insert(poll);

        return poll;
    }

    @Override
    public Subscriber addSubscriber(String pollId, Subscriber subscriber) {

        UUID pollUUID = UUID.fromString(pollId);

        // Generate a time UUID to sort correctly the users by their subscription time
        subscriber.setId(UUIDGen.getTimeUUID());

        if (avoidReadBeforeWrite) {
            return addSubscriberWithoutReadBeforeWrite(pollUUID, subscriber);
        } else {
            return addSubscriberWithReadBeforeWrite(pollUUID, subscriber);
        }
    }

    protected Subscriber addSubscriberWithReadBeforeWrite(UUID pollId, Subscriber subscriber) {
        /*
         In this version, we use plain Achilles API. In details, we
         lazily load the Poll via a proxy
         Poll.getSubscribers() call will load the entire object in memory,
         but poll.getSubscribers().add(subscriber) will only generate an update to cassandra,
         not a complete replace of the collection.
         So Achilles is doing a fairly good job at applying the delta, the only draw back
         is that there is a read-before-write.

         https://github.com/doanduyhai/Achilles/issues/85 is discussing an more optimal way to avoid
         the unwanted read-before-write feature.
         */
        Poll pollProxy = persistenceManager.getProxy(Poll.class, pollId);

        // The proxy is loaded (read-before-write)
        pollProxy.getSubscribers().add(subscriber);

        // Only the delta (i.e. the new Subscriber) is inserted into Cassandra
        persistenceManager.update(pollProxy);

        return subscriber;
    }

    protected  Subscriber addSubscriberWithoutReadBeforeWrite(UUID pollId, Subscriber subscriber) {

        /*
        This version does not do a read-before-write.
        The drawback is that the table name and the subscribers field
        is not checked at compile time., i.e. if you rename the table Poll,
        you need to change here.
        Another gotcha is that te subscriber object is manually serialized
        to String as Achilles would do otherwise.
        For the rest the QueryBuilder API from Datastax driver is used.
         */
        try {
            String subscriberStr = DefaultJacksonMapper.DEFAULT.get().writeValueAsString(subscriber);
            RegularStatement update = QueryBuilder.update("poll").with(QueryBuilder.append("subscribers", subscriberStr))
                    .where(QueryBuilder.eq("id", pollId));
            persistenceManager.getNativeSession().execute(update);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Serialization failed", e);
        }

        return subscriber;
    }

    @Override
    public void deletePoll(String pollId) {
        persistenceManager.deleteById(Poll.class, pollId);
    }
}
