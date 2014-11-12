package ch.noisette.doodle.service;

import ch.noisette.doodle.entity.Poll;
import ch.noisette.doodle.entity.Subscriber;

import java.util.List;

public interface PollService {

	public Poll getPollById(String pollId);

	public List<Poll> getAllPolls();

	public Poll createPoll(Poll poll);

	public Poll addSubscriber(String pollId, Subscriber subscriber);

	public void deletePoll(String pollId);
	
}
