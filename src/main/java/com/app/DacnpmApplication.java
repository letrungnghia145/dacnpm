package com.app;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.model.post.Post;
import com.app.model.post.Post_;
import com.app.repository.post.PostRepository;
import com.app.repository.tag.TagRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class DacnpmApplication implements CommandLineRunner {

	@Autowired
	private EntityManager em;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private PostRepository repository;
	@Autowired
	private ObjectMapper objectMapper;

	public static void main(String[] args) {
		SpringApplication.run(DacnpmApplication.class, args);
	}

//	public void configureObjectMapper(ObjectMapper objectMapper) {
//		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//		objectMapper.setSerializationInclusion(Include.NON_NULL);
//
//	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
//		configureObjectMapper(objectMapper);
//		repository.updateCurrentPostIndex(new Tag(1L));
//
//		Page<Post> findAll = repository.findAll(Specification.where(null),
//				PageRequest.of(0, 3).withSort(Sort.by("id").descending()));
//		findAll.forEach(post -> {
//			System.out.println(post.getId());
//		});
//		Specification<Post> specification = Specification.<Post>where((root, cq, cb) -> {
//			return cb.equal(root.get(Post_.ID), 20L);
//		}).or((root, cq, cb) -> {
//			return cb.equal(root.get(Post_.ID), 10L);
//		});
//		List<Post> findAll = repository.findAll(Specification.<Post>where(null).and(specification));
//		System.out.println(findAll);
		
	}
}
