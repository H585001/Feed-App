package group4.feedapp.API.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group4.feedapp.API.model.Poll;
import group4.feedapp.API.model.Vote;
import group4.feedapp.API.service.FAUserService;
import group4.feedapp.API.service.PollService;

@RestController
public class PollController {
	private final PollService pollService;
	private final FAUserService userService;

	public PollController(PollService pollService, FAUserService userService) {
		this.pollService = pollService;
		this.userService = userService;
	}
	
	@GetMapping("/polls")
    public ResponseEntity<Collection<Poll>> getPolls() {
		Collection<Poll> polls = pollService.getAllPolls();
        return new ResponseEntity<>(polls, HttpStatus.OK);
    }

    @GetMapping("/polls/access/{code}")
    public ResponseEntity<Poll> getPoll(@PathVariable String code) {

        Poll poll = pollService.getPollByCode(code);

        if (poll == null) {
            System.out.println(String.format("Poll with the access code  \"%s\" not found!", code));
            return new ResponseEntity<>(poll, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(poll, HttpStatus.OK);
    }
    
    @GetMapping("/polls/{id}")
    public ResponseEntity<Poll> getPoll(@PathVariable Long id) {

        Poll poll = pollService.getPoll(id);

        if (poll == null) {
            System.out.println(String.format("Poll with the id  \"%s\" not found!", id));
            return new ResponseEntity<>(poll, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(poll, HttpStatus.OK);
    }

    @PutMapping("/polls/{id}")
    public ResponseEntity<Poll> updatePoll(@RequestBody Poll updatedPoll, @PathVariable Long id) {

        Poll poll = pollService.updatePoll(id, updatedPoll);

        if (poll == null) {
            System.out.println(String.format("Poll with the id  \"%s\" not found!", id));
            return new ResponseEntity<>(poll, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(poll, HttpStatus.OK);
    }
    
    @PostMapping("/polls/{pollId}/{userId}")
    public ResponseEntity<Poll> voteOnPoll(@RequestBody Vote vote, @PathVariable Long pollId, @PathVariable Long userId) {

        Vote userVote = pollService.voteOnPoll(pollId, userId, vote);

        if (userVote != null) {
          return new ResponseEntity<>(pollService.getPoll(pollId), HttpStatus.OK);
        }
        
        return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
    }
    
    @PostMapping("/polls/{id}")
    public ResponseEntity<Poll> voteOnPoll(@RequestBody Vote vote, @PathVariable Long id) {

        Vote userVote = pollService.voteOnPoll(id, vote);

        if (userVote != null) {
        	return new ResponseEntity<>(pollService.getPoll(id), HttpStatus.OK);
        }
        
        return new ResponseEntity<>(null, HttpStatus.METHOD_NOT_ALLOWED);
    }
    
    @GetMapping("/polls/{pollId}/{userId}")
    public ResponseEntity<Vote> getUserVoteOnPoll(@PathVariable Long pollId, @PathVariable Long userId) {

        Vote vote = pollService.getUserVote(pollId, userId);

        if (vote != null) {
        	return new ResponseEntity<>(vote, HttpStatus.OK);
        }
        
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/polls/{id}")
    public ResponseEntity<Poll> deletePoll(@PathVariable Long id) {

        Poll poll = pollService.deletePoll(id);

        if (poll == null) {
            System.out.println(String.format("Poll with the id  \"%s\" not found!", id));
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(poll, HttpStatus.OK);
    }	
	
}
