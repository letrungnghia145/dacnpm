package com.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.model.Category;
import com.app.model.tag.Tag;
import com.app.service.category.CategoryService;

@SpringBootApplication
public class DacnpmApplication implements CommandLineRunner {
	@Autowired
	private CategoryService categoryService;

	public static void main(String[] args) {
		SpringApplication.run(DacnpmApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Tag> tags = new ArrayList<>();
		Tag e = new Tag();
		e.setName("Việc làm");
		Tag e1 = new Tag();
		e1.setName("Sự kiện công nghệ");
		tags.add(e);
		tags.add(e1);
		Category category = new Category(null, tags);
		category.setName("Thông tin - Sự kiện công nghệ");
		
		categoryService.createObject(category);
	}
}
