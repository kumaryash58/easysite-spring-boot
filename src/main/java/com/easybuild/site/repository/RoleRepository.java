package com.easybuild.site.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easybuild.site.bean.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
	Optional<Role> findByRoleName(String name);
}
