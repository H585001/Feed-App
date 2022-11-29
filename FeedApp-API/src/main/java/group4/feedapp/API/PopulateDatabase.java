package group4.feedapp.API;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import group4.feedapp.API.dao.FAUserDAO;
import group4.feedapp.API.dao.FAUserDAOImplementation;
import group4.feedapp.API.model.FAUser;
import group4.feedapp.API.model.IoTDevice;
import group4.feedapp.API.model.Poll;

public class PopulateDatabase {
	private static final String PERSISTENCE_UNIT_NAME = "feedapp-derby";
	private static EntityManagerFactory factory;

	public static void main(String[] args) {        
        populateDatabase();
        
        FAUserDAO userDAO = new FAUserDAOImplementation();
        
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
        c.add(Calendar.DATE, 100);
        Date end = c.getTime();
        
        PasswordEncoder encoder = new BCryptPasswordEncoder(11);;
        
		FAUser user = new FAUser("simon@gmail.com", encoder.encode("testing"), "Simon", true);
		Poll poll1 = new Poll("Are you sick and tired?", 0, 0, null, null, true, 1, "ABCDEF", user);
		Poll poll2 = new Poll("Do you like America?", 0, 0, start, end, false, 1, "AMERICA", user);
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
