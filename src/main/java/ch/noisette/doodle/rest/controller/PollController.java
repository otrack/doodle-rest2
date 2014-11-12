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
        return new ResponseEntity<String>("Hello world", HttpStatus.OK);
    }

    @RequestMapping(value = "/rest/poll/{pollId}", method = RequestMethod.GET)
    @ResponseBody
    public Poll getPoll(@PathVariable("pollId") String pollId) {
        Poll poll = null;

		/* validate poll Id parameter */
        if (isEmpty(pollId) || pollId.length() < 5) {
            String message = "Error invoking getPoll - Invalid poll Id parameter";
            return null; //createErrorResponse(sMessage);
        }

        try {
            poll = pollService.getPollById(pollId);
        } catch (Exception e) {
            String message = "Error invoking getPoll. [%1$s]";
            return null; //createErrorResponse(String.format(sMessage, e.toString()));
        }

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

        try {
            polls = pollService.getAllPolls();
        } catch (Exception e) {
            String message = "Error getting all polls. [%1$s]";
            return null; //createErrorResponse(String.format(sMessage, e.toString()));
        }

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

        try {
            createdPoll = pollService.createPoll(poll);
        } catch (Exception e) {
            String message = "Error creating new poll. [%1$s]";
            return null; //createErrorResponse(String.format(sMessage, e.toString()));
        }

		/* set HTTP response code */
        response.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
        response.setHeader("Location", request.getContextPath() + "/rest/poll/" + poll.getId());

        /**
         * Return the view
         */
        return "ok";
    }

    /**
     * Updates poll with given poll id.
     *
     * @param poll
     *            the poll
     * @return the model and view
     */
    @RequestMapping(value = { "/rest/poll/{pollId}" }, method = { RequestMethod.PUT })
    public String addSubscriber(@RequestBody Subscriber subscriber, @PathVariable("pollId") String pollId,
                                WebRequest request, HttpServletResponse response) {

        //logger_c.debug("Add Subscriber: " + subscriber.toString());

		/* validate poll Id parameter */
        if (isEmpty(pollId) || pollId.length() < 5) {
            String message = "Error updating poll - Invalid poll Id parameter";
            return null; //createErrorResponse(sMessage);
        }

        Subscriber createdSubscriber;

        try {
            createdSubscriber = pollService.addSubscriber(pollId, subscriber);
        } catch (Exception e) {
            String message = "Error updating poll. [%1$s]";
            return null; //createErrorResponse(String.format(sMessage, e.toString()));
        }

        response.setStatus(HttpStatus.CREATED.value());

        response.setHeader("Location", request.getContextPath() + "/rest/polls/" + createdSubscriber.getId().getPollId() + "/" + createdSubscriber.getId().getSubscriberId());

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

		/* validate poll Id parameter */
        if (isEmpty(pollId) || pollId.length() < 5) {
            String message = "Error deleting poll - Invalid poll Id parameter";
            return null; //createErrorResponse(sMessage);
        }

        try {
            pollService.deletePoll(pollId);
        } catch (Exception e) {
            String message = "Error invoking getPolls. [%1$s]";
            return null; //createErrorResponse(String.format(sMessage, e.toString()));
        }

        response.setStatus(HttpStatus.OK.value());
        return "ok";
    }

    public static boolean isEmpty(String s_p) {
        return (null == s_p) || s_p.trim().length() == 0;
    }

}
