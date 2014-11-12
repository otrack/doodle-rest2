package ch.noisette.doodle.service.impl;

import ch.noisette.doodle.domain.Poll;
import ch.noisette.doodle.domain.Subscriber;
import ch.noisette.doodle.service.PollService;
import info.archinnov.achilles.internal.utils.UUIDGen;
import info.archinnov.achilles.persistence.PersistenceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by bperroud on 11-Nov-14.
 */
@Service
public class PollServiceImpl implements PollService {

    @Autowired
    private PersistenceManager persistenceManager;

    @Override
    public Poll getPollById(String pollId) {

        UUID uuid = UUID.fromString(pollId);

        return null;
    }

    @Override
    public List<Poll> getAllPolls() {
        return null;
    }

    @Override
    public Poll createPoll(Poll poll) {
        return null;
    }

    @Override
    public Poll addSubscriber(String pollId, Subscriber subscriber) {
        return null;
    }

    @Override
    public void deletePoll(String pollId) {

    }
}
