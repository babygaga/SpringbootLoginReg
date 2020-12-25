package com.security.service;

import java.util.Random;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.security.entity.Role;
import com.security.entity.User;
import com.security.repo.RoleRepository;
import com.security.repo.UserRepository;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private UserRepository userRepository;

	private RoleRepository roleRepository;

	private final String USER_ROLE = "USER";

	@Autowired
	public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = findByEmail(username);// sajátab-ból a username
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserDetailsImp(user);// az az implementáció tudja mit kezdje a userrel
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public String registerUser(User userToRegister) {
		User userCheck = userRepository.findByEmail(userToRegister.getEmail());// kiveszem az emailcímet, mwg akarom állapítani ezzel az emillel van-e már regisztrált tag

		if (userCheck != null)
			return "alreadyExists!";

		Role userRole = roleRepository.findByRole(USER_ROLE);
		if (userRole != null) {
			userToRegister.getRoles().add(userRole);
		} else {
			userToRegister.addRoles(USER_ROLE);
		}

		userToRegister.setEnabled(false);
		userToRegister.setActivation(generateKey());
		userRepository.save(userToRegister);
		return "Ok";

	}

	private String generateKey() { // 16 karakteres véletlen kód gen
		String key = "";
		Random random = new Random();
		char[] word = new char[16];
		for (int j = 0; j < word.length; j++) {
			word[j] = (char) ('a' + random.nextInt(26));
		}
		String toReturn = new String(word);
		log.debug("Random kód " + toReturn);
		return new String(word);
	}

	@Override
	public String userActivation(String code) {
		User user = userRepository.findByActivation(code);
		if(user == null)
			return "noResult";
		
		user.setEnabled(true);
		user.setActivation("");
		userRepository.save(user);
		return "ok";
	}



}
