package com.abimael.deviceresources;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.info.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "Device management microservice REST API Documentation",
				description = "A REST API capable of persisting and managing device resources",
				version = "v1",
				contact = @Contact(
						name = "Abimael R Sergio",
						email = "abimaelr.sergio@gmail.com",
						url = "https://www.linkedin.com/in/abimaelsergio"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.linkedin.com/in/abimaelsergio/"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "Device management microservice REST API Documentation",
				url = "http://localhost:8080/swagger-ui/index.html"
		)
)
@SpringBootApplication
public class DeviceResourcesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceResourcesApplication.class, args);
	}

}
