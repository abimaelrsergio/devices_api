package com.abimael.deviceresources;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Device management REST API Documentation",
				description = "A REST API capable of persisting and managing device resources",
				version = "v1",
				contact = @Contact(
						name = "Abimael Rodrigues Sergio",
						email = "abimaelr.sergio@gmail.com",
						url = "https://www.linkedin.com/in/abimaelsergio"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.org/licenses/LICENSE-2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "Device management REST API Documentation",
				url = "http://localhost:8080/swagger-ui/index.html"
		)
)
@SpringBootApplication
public class DeviceResourcesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceResourcesApplication.class, args);
	}

}
