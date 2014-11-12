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

    @Test
    public void createPollTest() throws IOException
    {

//        given().contentType("text/plainjson; charset=UTF-8")
//          .get("/").then()
//          .statusCode(HttpStatus.OK.value())
//          .body("ok", equalTo("ok"));

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

        System.out.println(pollId);

        Subscriber s = new Subscriber();
        s.setLabel("User1");
        List<String> selectedChoices = new ArrayList<String>(poll.getChoices().size());

        for (String choice: poll.getChoices()) {
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

        System.out.println(subscriberId);
    }

}
