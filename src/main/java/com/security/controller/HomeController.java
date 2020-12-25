package com.security.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.security.entity.User;
import com.security.service.EmailService;
import com.security.service.UserService;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class HomeController {   //saját login oldal projecthez
	
	//logolás naplózás hasonló mint a println, infók átadása, lehet debag indíttatás, lehet rendszerezni mit hova küldjünk, minden vagy csak fontos adatokat debag módban futást application.proban  van
	  private final Logger log = LoggerFactory.getLogger(this.getClass());
	    
	    private UserService userService;
	    
	    private EmailService emailService;

		@Autowired
		public void setEmailService(EmailService emailService) {
			this.emailService = emailService;
		}
		
		@Autowired
		//Qualifired("osztályneve") - ha több osztály implementálná ugxsnszt sz intrrface-t
		public void setUserService(UserService userService) {
			this.userService = userService;
		}
		
		@RequestMapping("/")
		public String home(){
			return "index";
		}
		
		@RequestMapping("/bloggers")
		public String bloggers(){
			return "bloggers";
		}
		
		@RequestMapping("/stories")
		public String stories(){
			return "stories";
		}
		
		@RequestMapping("/registration")
		public String registration(Model model){
			model.addAttribute("user", new User());
			return "registration";
		}
		
//		@RequestMapping(value = "/reg", method = RequestMethod.POST)
		@PostMapping("/reg")
	    public String reg(@ModelAttribute User user) {
			log.info("Uj user!");
//			emailService.sendMessage(user.getEmail());
			log.debug(user.getFullName());
			log.debug(user.getEmail());
			log.debug(user.getPassword());
			userService.registerUser(user);
	        return "auth/login";
	    }
		
		 /*@RequestMapping(value = "/activation/{code}", method = RequestMethod.GET)//aktiváló kódot, url-ben emailben küdjüka linket a szerverünk url-je/code, ez get lekérdezés a büngésző url-jéből szedjüki ki azt
		    public String activation(@PathVariable("code") String code, HttpServletResponse response) {
			String result = userService.userActivation(code);
			return "auth/login";//?activationsuccess
		 }*/
		
		@RequestMapping(value="/activation/{code}", method = RequestMethod.GET)
		public String activation(@PathVariable("code") String code, HttpServletResponse response) {
			String result =userService.userActivation(code);			
			return"auth/login?activationsuccess";
		}
}
