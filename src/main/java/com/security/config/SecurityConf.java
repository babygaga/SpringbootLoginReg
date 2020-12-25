package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled =true) ///melyik függvényt milyen szintű felhasználó érhesse el
@Configuration
public class SecurityConf extends WebSecurityConfigurerAdapter{

	//csak tesztelésnél, pld távoli szerver nem lehet vagy nem akarunk hozzáférni, különben a belépésiadatok az ab-ban vannk
	//korlátlan felhasználó létrehozáas az appproperties nélkül
	
	@Bean
	public UserDetailsService userDetailsService() {
	    return super.userDetailsService();
	}
	
	@Autowired
	private UserDetailsService userService;
	
	@Autowired
	public void configreAuth(AuthenticationManagerBuilder auth) throws Exception{  //login az auth mappában van lásd webconfig
		//ab miatt nem kell
		/*auth
		.inMemoryAuthentication()
		.withUser("sfjuser")
		.password("{noop}pass")
		.roles("USER")
		.and()
		.withUser("sfjadmin")
		.password("{noop}pass")
		.roles("ADMIN");*/
		auth.userDetailsService(userService);	
	}
	
	//szerver viselkedésébe szól bele, ahttp securitit adja át nekünk, pld az mp3 file a szerveren ne legyen elérhető
	//fontos előszőr mindent kizárunk, utánna másik szabályal ezt és ezt engedem meg, fordítva biztonsági kockázat lesz
	@Override
	protected void configure(HttpSecurity http)  throws Exception {
		//saját bejelentkező oldal
		http
		.authorizeRequests()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.antMatchers("/registration").permitAll()//bejelentkezés nélkü eléri mindenki
		.antMatchers("/reg").permitAll()
		.anyRequest().authenticated()  //bármilyen lekérdezéshez authentikálni kell magát a usernek
		.and()
		.formLogin()
		.loginPage("/login")  //hova irányítson minket a form login login.html oldal
		.permitAll()
		.and()
		.logout()
		.logoutSuccessUrl("/login?logout")//kijelentkezéskor a login oldalra irányít, a getbe átadja alogout paramétert (?)
		.permitAll();
		
		/*httpSec
		.authorizeRequests()  //tesztelés van itt fordított sorrend, szabályokat hozok létre
		.antMatchers(HttpMethod.GET,"/")//lekrdezés a gyökér csatornára
		.permitAll()//minden ilyen req-t engedélyez
		.antMatchers("/delete").hasRole("ADMIN") //törlésnél madmin legyen
		.antMatchers("/admin/**").hasRole("ADMIN") //** bármi mp3 pdf doc
		.and()
		.formLogin()
		.permitAll(); //legyen mindenki számara  bejelentkező ablak, /-nél nem kell belépni ittsem
		*/
	}
	
	
}
