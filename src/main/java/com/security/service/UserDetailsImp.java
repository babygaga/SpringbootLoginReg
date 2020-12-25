package com.security.service;

import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.security.entity.Role;
import com.security.entity.User;

public class UserDetailsImp implements UserDetails {  /**
	 * 
	 */
	private static final long serialVersionUID = -8279559423102914301L;
//felülírása user adatait hogyan dolgozza fel, én mondom meg, hogyan megy fel egy user

	

	private User user;


	public UserDetailsImp(User user) {
		this.user = user;
	}

	//hogyan vegye ki a jogköröket
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {//A spring sec ide gyűjti a jogköröket
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		Set<Role> roles = user.getRoles();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));//ebbe az osztályba adja át a rolt, mert a rolt nem ismeri
		}
		return authorities;
	}

	@Override
	public String getPassword() {//én user obj-ból veszi ki a jelszót
		return user.getPassword();
	}
	//spring sec 5-ben így kell
	/*@Override
    public String getPassword() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder(); 
         return encoder.encode(this.user.getPassword());
     }*/

	@Override
	public String getUsername() {//az emailcim a username
		//System.out.println("email: ");
		//System.out.println(user.getEmail() );
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {  //lejárt-e az account, most nincs ilyen mező true nem  lejárt
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {//nincs locolva az account,
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {// lejárt e a jelszó,  ha bizonyos időnként emélkeztet változtass jelszót
		return true;
	}

	@Override
	public boolean isEnabled() {//felhasználó engedélyezve van
		return user.getEnabled();
		//return true;
	}
}
