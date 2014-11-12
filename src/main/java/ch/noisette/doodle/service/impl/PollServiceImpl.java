package ch.noisette.doodle.service.impl;

import ch.noisette.doodle.entity.Poll;
import ch.noisette.doodle.entity.Subscriber;
import ch.noisette.doodle.service.PollService;
import info.archinnov.achilles.internal.utils.UUIDGen;
import info.archinnov.achilles.persistence.PersistenceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

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

    @Override
    public Poll getPollById(String pollId) {

        UUID uuid = UUID.fromString(pollId);
        Poll poll;

        poll = persistenceManager.find(Poll.class, uuid);
        //poll = persistenceManager.removeProxy(poll);
        return poll;
    }

    @Override
    public List<Poll> getAllPolls() {
        return null;
    }

    @Override
    public Poll createPoll(Poll poll) {

        poll.setId(UUIDGen.getTimeUUID());
        persistenceManager.insert(poll);

        return poll;
    }

    @Override
    public Subscriber addSubscriber(String pollId, Subscriber subscriber) {

        Subscriber.SubscriberKey key = new Subscriber.SubscriberKey();
        key.setSubscriberId(UUID.fromString(pollId));
        key.setSubscriberId(UUIDGen.getTimeUUID());
        subscriber.setId(key);

        persistenceManager.insert(subscriber);

        return subscriber;
    }

    @Override
    public void deletePoll(String pollId) {

        UUID uuid = UUID.fromString(pollId);
        Poll poll = new Poll();
        poll.setId(uuid);

        persistenceManager.delete(poll);
    }
}
