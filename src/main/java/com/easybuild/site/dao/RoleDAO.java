package com.easybuild.site.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easybuild.site.entity.Role;
@Repository
public interface RoleDAO extends JpaRepository<Role, String>{
	Optional<Role> findByRoleName(String name);
}
