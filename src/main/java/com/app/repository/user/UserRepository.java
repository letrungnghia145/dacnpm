package com.app.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.app.model.user.User;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	public Optional<User> findByEmail(String email);

	@EntityGraph(attributePaths = { "sharedPosts" })
	public Optional<User> findUserWithSharedPostsById(Long id);

}
