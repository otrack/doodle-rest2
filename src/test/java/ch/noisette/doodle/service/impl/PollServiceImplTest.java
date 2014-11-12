package ch.noisette.doodle.service.impl;

import ch.noisette.doodle.domain.Poll;
import ch.noisette.doodle.domain.Subscriber;
import info.archinnov.achilles.internal.utils.UUIDGen;
import info.archinnov.achilles.junit.AchillesResource;
import info.archinnov.achilles.junit.AchillesResourceBuilder;
import info.archinnov.achilles.persistence.PersistenceManager;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;

import java.util.*;

public class PollServiceImplTest extends TestCase {

    @Rule
    public AchillesResource resource = AchillesResourceBuilder.withEntityPackages
            ("ch.noisette.doodle.domain").withKeyspaceName("doodle").truncateBeforeTest().build();

    private PersistenceManager persistenceManager = resource.getPersistenceManager();

    private static Random r = new Random();

    private static UUID pollId = UUIDGen.getTimeUUID();

    @Before
    public void setup() {

        // Create a default poll
        Poll poll = new Poll();
        poll.setId(pollId);
        poll.setLabel("Test Poll 123");
        poll.setEmail("email@test.com");
        List<String> choices = new ArrayList<String>(4);
        for (int i = 0; i < 4; i++) {
            choices.add("choice" + i);
        }
        poll.setChoices(choices);
        persistenceManager.insert(poll);
    }

    public void testGetPollById() throws Exception {

        Poll p = persistenceManager.find(Poll.class, pollId);
        Assert.assertNotNull(p);

    }

    public void testGetAllPolls() throws Exception {

//        Poll p = persistenceManager.find(Poll.class, pollId);
//        Assert.assertNotNull(p);

    }

    public void testCreatePoll() throws Exception {

        Poll poll = new Poll();
        poll.setId(pollId);
        poll.setLabel("Test Poll 123");
        poll.setEmail("email@test.com");
        List<String> choices = new ArrayList<String>(4);
        for (int i = 0; i < 4; i++) {
            choices.add("choice" + i);
        }
        poll.setChoices(choices);
        persistenceManager.insert(poll);

    }

    public void testAddSubscriber() throws Exception {


        Poll p = persistenceManager.find(Poll.class, pollId);
        Assert.assertNotNull(p);

        List<Subscriber> subscribers = new ArrayList<Subscriber>(10);
        for (int i = 0; i < 10; i++) {

            Subscriber s = new Subscriber();
            Subscriber.SubscriberKey key = new Subscriber.SubscriberKey();
            key.setPollId(pollId);
            key.setSubscriberId(UUID.randomUUID());
            s.setId(key);
            s.setLabel("S" + i);
            List<String> selectedChoices = new ArrayList<String>(p.getChoices().size());
            for (String choice: p.getChoices()) {
                if (r.nextBoolean()) {
                    selectedChoices.add(choice);
                }
            }
            s.setChoices(selectedChoices);
            persistenceManager.insert(s);
        }


        Iterator<Subscriber> it = persistenceManager.sliceQuery(Subscriber.class)
                .forIteration().withPartitionComponents(pollId)
                .noLimit().iterator();
        int count = 0;
        while (it.hasNext()) {
            count++;
            it.next();
        }
        Assert.assertEquals(10, count);
    }

    public void testDeletePoll() throws Exception {

        Poll p = new Poll();
        p.setId(pollId);
        persistenceManager.delete(p);

    }
}