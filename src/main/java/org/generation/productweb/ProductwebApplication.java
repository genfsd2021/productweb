package org.generation.productweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class ProductwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductwebApplication.class, args);
	}

}

@RestController
class HelloController {

	@GetMapping("/")
	String hello()
	{
		return "Hello World";
	}

}

