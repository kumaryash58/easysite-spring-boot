package com.easybuild.site.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easybuild.site.bean.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, String>{
	//Optional<Post> findByName(String name);
}
