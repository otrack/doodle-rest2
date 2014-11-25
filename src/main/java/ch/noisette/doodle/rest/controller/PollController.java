package ch.noisette.doodle.rest.controller;

import ch.noisette.doodle.entity.Poll;
import ch.noisette.doodle.entity.Subscriber;
import ch.noisette.doodle.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by bperroud on 11-Nov-14.
 */

@RestController
@EnableAutoConfiguration
public class PollController {

    @Autowired
    private PollService pollService;

    @RequestMapping(value = "/", produces = "application/json")
    @ResponseBody
    ResponseEntity<String> home() {
        return new ResponseEntity<String>("Hello world", HttpStatus.MULTI_STATUS);
    }

    @RequestMapping(value = "/rest/poll/{pollId}", method = RequestMethod.GET)
    @ResponseBody
    public Poll getPoll(@PathVariable("pollId") String pollId) {
        Poll poll = null;

        poll = pollService.getPollById(pollId);

        return poll;
    }

    /**
     * Gets all polls.
     *
     * @return the polls
     */
    @RequestMapping(value = "/rest/polls/", method = RequestMethod.GET)
    public List<Poll> getPolls() {
        List<Poll> polls;

        polls = pollService.getAllPolls();

        return polls;
    }

    /**
     * Creates a new poll.
     *
     * @param poll
     *            the poll
     * @return the model and view
     */
    @RequestMapping(value = { "/rest/poll" }, method = { RequestMethod.POST })
    public String createPoll(@RequestBody Poll poll, WebRequest request,
                                   HttpServletResponse response) {

        Poll createdPoll;

        createdPoll = pollService.createPoll(poll);

		/* set HTTP response code */
        response.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
        response.setHeader("Location", request.getContextPath() + "/rest/poll/" + createdPoll.getId());

        /**
         * Return the view
         */
        return "ok";
    }

    /**
     * Updates poll with given poll id.
     *
     * @param subscriber
     *            the subscriber
     * @return the model and view
     */
    @RequestMapping(value = { "/rest/poll/{pollId}" }, method = { RequestMethod.PUT })
    public String addSubscriber(@RequestBody Subscriber subscriber, @PathVariable("pollId") String pollId,
                                WebRequest request, HttpServletResponse response) {

        Subscriber createdSubscriber;

        createdSubscriber = pollService.addSubscriber(pollId, subscriber);

        response.setStatus(HttpStatus.CREATED.value());

        response.setHeader("Location", request.getContextPath() + "/rest/polls/" + pollId + "/" + createdSubscriber.getId());

        return "ok";
    }

    /**
     * Deletes the poll with the given poll id.
     *
     * @param pollId
     *            the poll id
     * @return the model and view
     */
    @RequestMapping(value = "/rest/poll/{pollId}", method = RequestMethod.DELETE)
    public String removePoll(@PathVariable("pollId") String pollId,
                                   HttpServletResponse response) {

        pollService.deletePoll(pollId);

        response.setStatus(HttpStatus.OK.value());
        return "ok";
    }

}
