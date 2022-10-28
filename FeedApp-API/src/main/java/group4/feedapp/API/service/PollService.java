package group4.feedapp.API.service;

import java.util.Date;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group4.feedapp.API.dao.FAUserDAO;
import group4.feedapp.API.dao.IoTDeviceDAO;
import group4.feedapp.API.dao.IoTVotesDAO;
import group4.feedapp.API.dao.PollDAO;
import group4.feedapp.API.dao.VoteDAO;
import group4.feedapp.API.messaging.RabbitMQSender;
import group4.feedapp.API.messaging.Topics;
import group4.feedapp.API.model.FAUser;
import group4.feedapp.API.model.IoTDevice;
import group4.feedapp.API.model.IoTVotes;
import group4.feedapp.API.model.Poll;
import group4.feedapp.API.model.Vote;


@Service
public class PollService {
	private final PollDAO pollDAO;
	private final FAUserDAO userDAO;
	private final IoTDeviceDAO deviceDAO;
	private final VoteDAO voteDAO;
	private final IoTVotesDAO IoTVotesDAO;
	
	@Autowired 
	private RabbitMQSender messageSender;
	
	@Autowired
	public PollService(PollDAO pollDAO, FAUserDAO userDAO, IoTDeviceDAO deviceDAO, VoteDAO voteDAO, IoTVotesDAO IoTVotesDAO) {
		this.pollDAO = pollDAO;
		this.userDAO = userDAO;
		this.deviceDAO = deviceDAO;
		this.voteDAO = voteDAO;
		this.IoTVotesDAO = IoTVotesDAO;
	}

	public Poll addPoll(String question, int noCount, int yesCount, Date startTime, Date endTime,
			boolean isPublic, int status, String accessCode, FAUser creator) {
		Poll poll = new Poll(question, noCount, yesCount, startTime, endTime, isPublic, status, accessCode, creator);
		FAUser owner = userDAO.readUser(creator.getId());
		if(owner != null) {
			// Updating the creator's list of polls
			owner.getCreatedPolls().add(poll);
			userDAO.updateUser(owner.getId(), owner);
			poll = pollDAO.createPoll(poll);
			
			if (poll != null) {
				// TODO Messaging Event --> Created poll
				messageSender.publishPollEvent(Topics.POLL_OPENED, poll);
			}
			
			return poll;
		}
		
		return null;
			
	}
	
	public Collection<Poll> getAllPolls(){
		return pollDAO.readPolls();
	}
	
	public Poll getPoll(Long id){
		return pollDAO.readPoll(id);
	}
	
	public Poll getPollByCode(String code) {
		return pollDAO.readPollByCode(code);
	}
	
	public Poll deletePoll(Long id) {
		Poll poll = pollDAO.readPoll(id);
		if (poll == null) {
			return null;
		}
		// Resetting the linked polls
		for(IoTDevice device : poll.getLinkedDevices()) {
			device.setLinkedPoll(null);
			deviceDAO.updateIoTDevice(device.getId(), device);
		}
		for(Vote v : poll.getUserVotes()) {
			voteDAO.deleteVote(v.getId());
		}
		for(IoTVotes v : poll.getIotVotes()) {
			IoTVotesDAO.deleteIoTVotes(v.getId());
		}
		return pollDAO.deletePoll(id);
	}
	
	public Poll updatePoll(Long id, Poll updatedPoll) {
		// TODO check if owner changed ?
		Poll poll = pollDAO.readPoll(id);
		if (poll == null) {
			return null;
		}
		
		// TODO check if the poll is being closed: Message Event --> Poll closed 
		if (poll.getStatus() != 2 && updatedPoll.getStatus() == 2) {
			System.out.println("Poll closed: " + poll.toString());
		}
		return pollDAO.updatePoll(id, updatedPoll);
	}
	
	public Vote getUserVote(Long pollId, Long userId) {
		Poll poll = getPoll(pollId);
		FAUser user = userDAO.readUser(userId);
		return voteDAO.findUserVote(poll, user);
	}
	
	public Vote voteOnPoll(Long pollId, Long userId, Vote vote) {
		
		// User is only able to vote on the poll if:
		// - vote, poll and voter != null
		// - The poll is open (status == 1)
		// - The user hasn't already voted on the poll
		// - TODO Check the time limit?
		Poll poll = getPoll(pollId);
		FAUser voter = userDAO.readUser(userId);
		if(vote!= null && poll!= null && poll.getStatus() == 1 && voter != null && voteDAO.findUserVote(poll, voter) == null) {
			poll.getUserVotes().add(vote);
			if(vote.getAnswer()) {
				poll.setYesCount(poll.getYesCount() + 1);
			}else {
				poll.setNoCount(poll.getNoCount() + 1);
			}
			voter.getVotes().add(vote);
			vote.setVotePoll(poll);
			vote.setVoter(voter);
			voteDAO.createVote(vote);
			userDAO.updateUser(voter.getId(), voter);
			pollDAO.updatePoll(poll.getId(), poll);
			return vote;
		}else {
			System.out.println("Vote error");
		}
		return null;
	}
	
	public Vote voteOnPoll(Long pollId, Vote vote) {
		// A public vote is possible if:
		// - vote and poll != null
		// - The poll is open (status == 1)
		// - The poll is public
		// - TODO Check the time limit?
		Poll poll = getPoll(pollId);
		if(vote!= null && poll!= null && poll.getStatus() == 1 && poll.isPublic()) {
			if(vote.getAnswer()) {
				poll.setYesCount(poll.getYesCount() + 1);
			}else {
				poll.setNoCount(poll.getNoCount() + 1);
			}
			pollDAO.updatePoll(poll.getId(), poll);
			return vote;
		}else {
			System.out.println("Vote error");
		}
		return null;
	}
	
	public IoTVotes voteOnPoll(Long pollId, Long deviceId, IoTVotes votes) {
		Poll poll = getPoll(pollId);
		IoTDevice device = deviceDAO.readIoTDevice(deviceId);
		if(poll != null && poll.getStatus() == 1 && device != null) {
			poll.getIotVotes().add(votes);
			poll.setNoCount(poll.getNoCount() + votes.getNoCount());
			poll.setYesCount(poll.getYesCount() + votes.getYesCount());
			votes.setIotVotePoll(poll);
			votes.setDevice(device);
			IoTVotesDAO.createIoTVotes(votes);
			pollDAO.updatePoll(poll.getId(), poll);
			return votes;
		}
		return null;
	}
	
	public Collection<Poll> getUserPolls(Long userId) {
		FAUser user = userDAO.readUser(userId);
		return pollDAO.getUserPolls(user);
	}

}
