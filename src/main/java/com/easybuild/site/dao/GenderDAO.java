package com.easybuild.site.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easybuild.site.entity.Gender;
@Repository
public interface GenderDAO extends JpaRepository<Gender, String>{
	Optional<Gender> findByGenderName(String name);
}