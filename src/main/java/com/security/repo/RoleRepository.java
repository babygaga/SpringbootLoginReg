package com.security.repo;

import org.springframework.data.repository.CrudRepository;
import com.security.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	 Role findByRole(String role);

}
