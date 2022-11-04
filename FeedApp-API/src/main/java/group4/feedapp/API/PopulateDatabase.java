package group4.feedapp.API;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import group4.feedapp.API.dao.FAUserDAO;
import group4.feedapp.API.dao.FAUserDAOImplementation;
import group4.feedapp.API.model.FAUser;
import group4.feedapp.API.model.IoTDevice;
import group4.feedapp.API.model.IoTVotes;
import group4.feedapp.API.model.Poll;
import group4.feedapp.API.model.Vote;

public class PopulateDatabase {
	private static final String PERSISTENCE_UNIT_NAME = "feedapp-derby";
	private static EntityManagerFactory factory;

	public static void main(String[] args) {        
        populateDatabase();
        
        FAUserDAO userDAO = new FAUserDAOImplementation();
        userDAO.createUser("jens@ymail.com", "hei", "Jens", false);
        
        Collection<FAUser> users = userDAO.readUsers();
        users.forEach(u -> System.out.println(u));
	}

	private static void populateDatabase() {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();
        
        em.getTransaction().begin();
        
        // Init data
        Date start = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(start);
        c.add(Calendar.DATE, 7);
        Date end = c.getTime();
        
		FAUser user = new FAUser("simon@gmail.com", "test", "Simon", true);
		Poll poll1 = new Poll("Are you sick and tired?", 0, 0, start, end, true, 0, "ABCDEF", user);
		Poll poll2 = new Poll("Do you like America?", 0, 0, start, end, false, 0, "AMERICA", user);
		IoTDevice iot1 = new IoTDevice("IoT Device 1", poll1);
		IoTDevice iot2 = new IoTDevice("IoT Device 2", poll1);
		
		// Persisting
        em.persist(user);
        em.persist(poll1);
        em.persist(poll2);
        em.persist(iot1);
        em.persist(iot2);
        em.getTransaction().commit();
        
        em.close();
	}

}
