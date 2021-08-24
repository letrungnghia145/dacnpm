package com.app.service.user;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.exception.RegistrationException;
import com.app.helper.Mapper;
import com.app.helper.token.TokenUtils;
import com.app.model.post.Post;
import com.app.model.user.Role;
import com.app.model.user.User;
import com.app.model.user.User_;
import com.app.repository.post.PostRepository;
import com.app.repository.user.UserRepository;
import com.app.service.Filter;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private ObjectMapper mapper;

	private UserDetails toUserDetails(User user) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		Role role = user.getRole();
		authorities.add(new SimpleGrantedAuthority(role.name()));
		role.getPermissions().forEach(authority -> {
			authorities.add(new SimpleGrantedAuthority(authority.getPermission()));
		});
		return new UserDetailsImpl(user.getEmail(), user.getPassword(), authorities);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Optional<User> optional = userRepository.findByEmail(username);
			return toUserDetails(optional.orElseThrow());
		} catch (UsernameNotFoundException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}

	@Override
	public User doRegister(String payload, String inputCode) throws Exception {
		Map<String, Object> information = TokenUtils.getInfomationFromToken(payload);
		User user = mapper.convertValue(information.get("user"), User.class);
		String validationCode = (String) information.get("validationCode");
		Long expiredDate = (Long) information.get("expiredDate");
		System.out.println(user.getPassword());
		if (new Date().before(new Date(expiredDate)) && validationCode.equals(inputCode)) {
			user.setRole(Role.ROLE_USER);
			return this.createObject(user);
		} else {
			throw new RegistrationException(!inputCode.equals(validationCode) ? "Invalid code!" : "Expired code!");
		}
//		return this.createObject(user);
	}

	@Override
	public User createObject(User object) {
		return userRepository.save(object);
	}

	@Override
	public List<User> getAllObjects(Map<String, String> filters) {
		Filter<User> filter = new Filter<>(filters);
		return userRepository.findAll(filter.getSpecification());
	}

	@Override
	public User getObjectById(Long id) {
		return userRepository.findById(id).orElseThrow();
	}

	@Override
	public User updateObject(Long id, User object) {
		User oldUser = userRepository.findById(id).orElseThrow();
		return Mapper.convert(oldUser, object);
	}

	@Override
	public void deleteObjectById(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public User getByEmail(String username) {
		return userRepository.findByEmail(username).orElseThrow();
	}

	@Override
	public Post addUserSharedPost(Long id, Post post) {
		User user = userRepository.findUserWithSharedPostsById(id).orElseThrow();
		user.getSharedPosts().add(post);
		post.getSharers().add(user);
		return post;
	}

	@Override
	public List<Post> getUserSharedPosts(Long id, Map<String, String> filters) {
		Filter<Post> filter = new Filter<>(filters);
		Specification<Post> specification = (post, cq, cb) -> {
			Root<User> user = cq.from(User.class);
			Expression<List<Post>> sharedPosts = user.get(User_.SHARED_POSTS);
			return cb.and(cb.equal(user.get(User_.ID), id), cb.isMember(post, sharedPosts));
		};
		return postRepository.findAll(specification.and(filter.getSpecification()));
	}
}
