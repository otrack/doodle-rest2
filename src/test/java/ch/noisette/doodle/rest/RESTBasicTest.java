package ch.noisette.doodle.rest;

import ch.noisette.doodle.DoodleApp;
import ch.noisette.doodle.entity.Poll;
import ch.noisette.doodle.entity.Subscriber;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RESTBasicTest
{

    ConfigurableApplicationContext context;

    private static Random r = new Random();

    @Before
    public void setup() {
        context = SpringApplication.run(DoodleApp.class, new String[0]);
    }

    @After
    public void tearDown() throws Exception {
        context.close();
    }


    /**
     * Launch the application, create a poll, subscribe between 5 and 15 people,
     * retrieve the poll and and assert on the output.
     *
     */
    @Test
    public void createPollTest() throws IOException
    {

        Poll poll = new Poll();
        poll.setEmail("email@address.com");
        
        poll.setLabel("Afterwork");
        poll.setMaxChoices(1);
        
        @SuppressWarnings("serial")
        List<String> choices = new ArrayList<String>() {{ add("Monday"); add("Tuesday"); add("Friday"); }};
        poll.setChoices(choices);

        String pollLocation = given().contentType("application/json; charset=UTF-8")
            .body(poll)
            .post("/rest/poll").then()
            .statusCode(HttpStatus.CREATED.value())
            .header("Location", notNullValue()).extract().header("Location");

        String pollId = pollLocation.substring(pollLocation.lastIndexOf('/') + 1);

        Assert.assertNotNull(pollId);

        int numberOfSubscribers = r.nextInt(10) + 5;
        List<String> subscriberIds = new ArrayList<String>(numberOfSubscribers);

        for (int i = 1; i <= numberOfSubscribers; i++) {
            Subscriber s = new Subscriber();
            s.setLabel("User1");
            List<String> selectedChoices = new ArrayList<String>(poll.getChoices().size());

            for (String choice : poll.getChoices()) {
                if (r.nextBoolean()) {
                    selectedChoices.add(choice);
                }
            }
            s.setChoices(selectedChoices);

            String subscriberLocation = given().contentType("application/json; charset=UTF-8")
                    .body(s)
                    .pathParam("pollId", pollId)
                    .when()
                    .put("/rest/poll/{pollId}")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .header("Location", notNullValue()).extract().header("Location");

            String subscriberId = subscriberLocation.substring(subscriberLocation.lastIndexOf('/') + 1);

            Assert.assertNotNull(subscriberId);
            subscriberIds.add(subscriberId);
        }

        Assert.assertEquals(numberOfSubscribers, subscriberIds.size());

        Poll retrievedPoll = given().contentType("application/json; charset=UTF-8")
                .when()
                .get("/rest/poll/{pollId}", pollId).as(Poll.class);

        Assert.assertNotNull(retrievedPoll);
        Assert.assertEquals(pollId, retrievedPoll.getId().toString());
        Assert.assertEquals(numberOfSubscribers, retrievedPoll.getSubscribers().size());

    }

}
