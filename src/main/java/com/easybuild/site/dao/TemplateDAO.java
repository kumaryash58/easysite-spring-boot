package com.easybuild.site.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easybuild.site.entity.Templates;
@Repository
public interface TemplateDAO extends JpaRepository<Templates, String>{
	Optional<Templates> findById(Long id);

}
