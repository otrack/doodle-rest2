package ch.noisette.doodle.service.impl;

import ch.noisette.doodle.entity.Poll;
import ch.noisette.doodle.entity.Subscriber;
import ch.noisette.doodle.service.PollService;
import info.archinnov.achilles.persistence.PersistenceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by bperroud on 11-Nov-14.
 */
//@Service
//@EnableAutoConfiguration
public class PollServiceImpl implements PollService {

    @Autowired
    private PersistenceManager persistenceManager;

    @Override
    public Poll getPollById(String pollId) {
        // FIXME
        return null;
    }

    @Override
    public List<Poll> getAllPolls() {
        // FIXME
        return null;
    }

    @Override
    public Poll createPoll(Poll poll) {
        // FIXME
        return null;
    }

    @Override
    public Subscriber addSubscriber(String pollId, Subscriber subscriber) {
        // FIXME
        return null;
    }

    @Override
    public void deletePoll(String pollId) {
        // FIXME
    }
}
