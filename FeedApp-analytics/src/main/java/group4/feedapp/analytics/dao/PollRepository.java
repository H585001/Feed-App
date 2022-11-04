package group4.feedapp.analytics.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import group4.feedapp.analytics.model.Poll;

public interface PollRepository extends MongoRepository<Poll, String> {
	
}
