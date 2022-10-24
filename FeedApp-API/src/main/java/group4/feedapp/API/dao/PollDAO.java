package group4.feedapp.API.dao;

import java.util.Date;
import java.util.Collection;

import group4.feedapp.API.model.FAUser;
import group4.feedapp.API.model.Poll;

public interface PollDAO {
	Poll createPoll(String question, int noCount, int yesCount, Date startTime, Date endTime,
			boolean isPublic, int status, String accessCode, FAUser creator);
	Poll createPoll(Poll newPoll);
	Poll readPoll(Long id);
	Collection<Poll> readPolls();
	Poll updatePoll(Long id, Poll updatedPoll);
	Poll deletePoll(Long id);
	Collection<Poll> getUserPolls(FAUser user);
	Poll readPollByCode(String code);
}
