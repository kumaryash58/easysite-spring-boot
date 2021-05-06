package com.easybuild.site.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easybuild.site.entity.User;
@Repository
public interface UserDAO extends JpaRepository<User, Long>{
	Optional<User> findById(Long id);
	Optional<User> findByFirstName(String firstName);
	Optional<User> findByEmail(String email);
}
