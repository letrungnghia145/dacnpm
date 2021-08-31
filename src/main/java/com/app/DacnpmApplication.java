package com.app;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.repository.tag.TagRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class DacnpmApplication implements CommandLineRunner {

	@Autowired
	private EntityManager em;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private TagRepository repository;
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
	}
}
