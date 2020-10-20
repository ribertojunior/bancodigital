package com.example.bancodigital;

import com.example.bancodigital.storage.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class BancodigitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancodigitalApplication.class, args);
	}

}
