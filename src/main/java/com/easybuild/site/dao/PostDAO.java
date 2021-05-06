package com.easybuild.site.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easybuild.site.entity.Post;
import com.easybuild.site.entity.User;
@Repository
public interface PostDAO extends JpaRepository<Post, String>{
	Post findByUser(User user);
	List<Post> findPostByUser(User user);
	Optional<Post> findById(Long id);
	
}
