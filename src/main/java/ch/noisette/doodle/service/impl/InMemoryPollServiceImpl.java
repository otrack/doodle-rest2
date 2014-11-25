package ch.noisette.doodle.service.impl;

import ch.noisette.doodle.entity.Poll;
import ch.noisette.doodle.entity.Subscriber;
import ch.noisette.doodle.service.PollService;
import info.archinnov.achilles.internal.utils.UUIDGen;
import info.archinnov.achilles.persistence.PersistenceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bperroud on 11-Nov-14.
 */
//@Service
//@EnableAutoConfiguration
public class InMemoryPollServiceImpl implements PollService {

    private final Map<String, Poll> polls = new ConcurrentHashMap<String, Poll>();

    @Override
    public Poll getPollById(String pollId) {

        Poll poll = polls.get(pollId);

        return poll;
    }

    @Override
    public List<Poll> getAllPolls() {

        List<Poll> polls = new ArrayList<Poll>(this.polls.size());

        for (Poll poll: this.polls.values()) {
            polls.add(poll);
        }
        return polls;
    }

    @Override
    public Poll createPoll(Poll poll) {

        poll.setId(UUIDGen.getTimeUUID());

        polls.put(poll.getId().toString(), poll);

        if (poll.getSubscribers() == null) {
            poll.setSubscribers(new ArrayList<Subscriber>());
        }

        return poll;
    }

    @Override
    public Subscriber addSubscriber(String pollId, Subscriber subscriber) {

        Poll poll = polls.get(pollId);

        if (poll == null) {
            throw new IllegalStateException("Poll " + pollId + " not found");
        }

        subscriber.setId(UUID.fromString(pollId));

        poll.getSubscribers().add(subscriber);

        return subscriber;
    }

    @Override
    public void deletePoll(String pollId) {
        polls.remove(pollId);
    }
}
