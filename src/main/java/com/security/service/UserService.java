package com.security.service;

import com.security.entity.User;

//interfacel megoldható ha a felh adatokat többféle filba mentse ki
public interface UserService { // service rétegnek el kell érnie egy usert email alapján

	public String registerUser(User user);

	public User findByEmail(String email);
	
	public String userActivation(String code);
}
