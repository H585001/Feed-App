package group4.feedapp.API.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import group4.feedapp.API.model.FAUser;
import group4.feedapp.API.model.Poll;
import group4.feedapp.API.model.Vote;

@Repository
public class VoteDAOImplementation implements VoteDAO {
	private EntityManagerFactory emf;
	private static final String PERSISTENCE_UNIT_NAME = "feedapp-derby";
	
	public VoteDAOImplementation() {
		emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}

	@Override
	public Vote createVote(Vote vote) {
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        boolean success = true;

		try {
			tx.begin();
			em.persist(vote);
			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			success = false;
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}	
		
		if(!success){
			return null;
		};
		return vote;
	}

	@Override
	public Vote createVote(FAUser voter, Poll votePoll, boolean answer) {
		EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        boolean success = true;
        Vote vote = new Vote(voter, votePoll, answer);

		try {
			tx.begin();
			em.persist(vote);
			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			success = false;
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}	
		
		if(!success){
			return null;
		};
		return vote;
	}

	@Override
	public Vote readVote(Long id) {
		EntityManager em = emf.createEntityManager();        
        Vote vote = null;

		try {
			vote = em.find(Vote.class, id);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			em.close();
		}		
		return vote;
	}

	@Override
	public Vote findUserVote(Poll poll, FAUser user) {
		EntityManager em = emf.createEntityManager();        
        Vote vote = null;
		try {
			TypedQuery<Vote> q = em.createQuery("SELECT v FROM Vote v WHERE v.voter = :user AND v.votePoll = :poll", Vote.class);
			q.setParameter("user", user);
			q.setParameter("poll", poll);
			vote = q.getSingleResult();
		} catch (NoResultException e) {
			
		} finally {
			em.close();
		}		
		return vote;
	}

	@Override
	public Collection<Vote> readVotes() {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<Vote> query = em.createQuery("SELECT v FROM Vote v", Vote.class);
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public Vote updateVote(Long id, Vote updatedVote) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
        Vote vote = null;
        boolean success = true;
        
		try {
			tx.begin();
			vote = em.find(Vote.class, id);
			if(vote != null && updatedVote != null) {
				vote.setAnswer(updatedVote.getAnswer());
				vote.setVotePoll(updatedVote.getVotePoll());
				vote.setVoter(updatedVote.getVoter());
			}
			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			success = false;
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}		
		
		if(!success){
			return null;
		};
		return vote;
	}

	@Override
	public Vote deleteVote(Long id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
        Vote vote = null;
        boolean success = true;
        
		try {
			tx.begin();
			vote = em.find(Vote.class, id);
			if(vote != null) {
				em.remove(vote);
			}
			tx.commit();
		} catch (Throwable e) {
			e.printStackTrace();
			success = false;
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			em.close();
		}	
		
		if(!success){
			return null;
		};
		return vote;
	}

}
