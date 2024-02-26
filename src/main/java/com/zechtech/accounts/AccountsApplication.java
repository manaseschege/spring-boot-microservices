package com.zechtech.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts Microservice REST API Documentation",
                description = "ZechBank Accounts Microservices REST API Documentation",
                version = "v1",
				contact = @Contact(
						name = "Manasses Chege",
                        url = "https://www.linkedin.com/in/manasses-chege-b133b3269/",
                        email = "manaseschege0@gmail.com"
				),
				license = @License(
						name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "ZechBank Accounts mocroservices REST API Documentation",
                url = "https://github.com/manasses-chege/accounts-microservice"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
