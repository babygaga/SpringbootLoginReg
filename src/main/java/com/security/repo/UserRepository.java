package com.security.repo;

import org.springframework.data.repository.CrudRepository;
import com.security.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {//innen kiveszia felhn és jelszót és hasonlítsa össze az ab-al
User findByEmail(String email);  //felhasználó keresése email alapján, objektumot ad vissz a user infokkal

User findByActivation(String code);
}
