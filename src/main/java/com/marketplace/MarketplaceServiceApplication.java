package com.marketplace;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Marketplace API", version = "v1"))
public class MarketplaceServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(MarketplaceServiceApplication.class, args);
	}
}
