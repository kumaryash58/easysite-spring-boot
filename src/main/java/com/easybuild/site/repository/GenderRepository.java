package com.easybuild.site.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easybuild.site.bean.Gender;


@Repository
public interface GenderRepository extends JpaRepository<Gender, String>{
	Optional<Gender> findByGenderName(String name);
}