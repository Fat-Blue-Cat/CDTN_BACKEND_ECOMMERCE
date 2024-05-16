package org.example.ecommerceweb;

import org.example.ecommerceweb.config.VnPayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import static org.example.ecommerceweb.util.Hash.getIpAddress;

@SpringBootApplication
@ConfigurationPropertiesScan
public class EcommerceWebApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceWebApplication.class, args);
	}

	@Autowired
	private VnPayConfig vnPayConfig;
	@Override
	public void run(String... args) throws Exception {
//		System.out.println(vnPayConfig);
	}
}
