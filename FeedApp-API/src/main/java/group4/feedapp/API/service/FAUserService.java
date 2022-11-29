package group4.feedapp.API.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import group4.feedapp.API.dao.FAUserDAO;
import group4.feedapp.API.model.FAUser;

@Service
public class FAUserService {
	private final FAUserDAO userDAO;
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public FAUserService(FAUserDAO userDAO, PasswordEncoder passwordEncoder) {
		this.userDAO = userDAO;
		this.passwordEncoder = passwordEncoder;
	}

	public FAUser addUser(String email, String password, String name, boolean isAdmin) {
		return userDAO.createUser(email, passwordEncoder.encode(password), name, isAdmin);
	}
	
	public Collection<FAUser> getAllUsers(){
		return userDAO.readUsers();
	}
	
	public FAUser getUser(Long id){
		return userDAO.readUser(id);
	}
	
	public FAUser getUserByName(String name){
		return userDAO.readUserByName(name);
	}
	
	public FAUser getUserByEmail(String email){
		return userDAO.readUserByEmail(email);
	}
	
	public FAUser deleteUser(Long id) {
		return userDAO.deleteUser(id);
	}
	
	public FAUser updateUser(Long id, FAUser newUser) {
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		return userDAO.updateUser(id, newUser);
	}
}
