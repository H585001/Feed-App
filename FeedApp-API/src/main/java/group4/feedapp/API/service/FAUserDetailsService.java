package group4.feedapp.API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import group4.feedapp.API.dao.FAUserDAO;
import group4.feedapp.API.model.FAUser;

@Service
public class FAUserDetailsService implements UserDetailsService {

	
	private final FAUserDAO userDao;
	
	public FAUserDetailsService(FAUserDAO userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) {
		FAUser user = userDao.readUserByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException(email);
		}
		return new FAUserPrincipal(user);
	}
	
}
