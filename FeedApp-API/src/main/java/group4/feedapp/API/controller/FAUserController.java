package group4.feedapp.API.controller;

import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import group4.feedapp.API.model.FAUser;
import group4.feedapp.API.model.Poll;
import group4.feedapp.API.service.FAUserService;
import group4.feedapp.API.service.PollService;

@CrossOrigin(origins = "http://127.0.0.1:5173")
@RestController
public class FAUserController {
	private final FAUserService userService;
	private final PollService pollService;

	public FAUserController(FAUserService userService, PollService pollService) {
		this.userService = userService;
		this.pollService = pollService;
	}
	
	@GetMapping("/users")
    public ResponseEntity<Collection<FAUser>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<FAUser> getUser(@PathVariable Long id) {

        FAUser user = userService.getUser(id);

        if (user == null) {
            System.out.println(String.format("User with the id  \"%s\" not found!", id));
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @GetMapping("/users/email/{email}")
    public ResponseEntity<FAUser> getUser(@PathVariable String email) {

        FAUser user = userService.getUserByEmail(email);

        if (user == null) {
            System.out.println(String.format("User with the e-mail  \"%s\" not found!", email));
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @GetMapping("/users/{id}/polls")
    public ResponseEntity<Collection<Poll>> getUserPolls(@PathVariable Long id) {
    	if(userService.getUser(id) == null) {
    		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    	}
    	return new ResponseEntity<>(pollService.getUserPolls(id), HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<FAUser> updateUser(@RequestBody FAUser updatedUser, @PathVariable Long id) {

        FAUser user = userService.updateUser(id, updatedUser);

        if (user == null) {
            System.out.println(String.format("User with the id  \"%s\" not found!", id));
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<FAUser> addUser(@RequestBody FAUser newUser) {
        FAUser user = userService.addUser(newUser.getEmail(),newUser.getPassword(),newUser.getName(), newUser.isAdmin());
        if(user == null) {
        	return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<FAUser> deleteUser(@PathVariable Long id) {

        FAUser user = userService.deleteUser(id);

        if (user == null) {
            System.out.println(String.format("User with the id  \"%s\" not found!", id));
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }	
    
    @PostMapping("/users/{id}/polls")
    public ResponseEntity<Poll> addPoll(@RequestBody Poll newPoll, @PathVariable Long id) {
    	FAUser creator = userService.getUser(id);
    	
    	 if (creator == null) {
             System.out.println(String.format("User with the id  \"%s\" not found!", id));
             return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
         }
    	
    	 Poll poll = pollService.addPoll(newPoll.getQuestion(), newPoll.getNoCount(), newPoll.getYesCount(), 
        		newPoll.getStartTime(), newPoll.getEndTime(),newPoll.isPublic(), newPoll.getStatus(), 
        		newPoll.getAccessCode(), creator);
    	 
    	 if (poll == null) {
             System.out.println("Invalid input for poll");
             return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
         }
    	 
    	 return new ResponseEntity<>(poll, HttpStatus.OK);
        
    }
	
}
