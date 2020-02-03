package com.easyfly.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfiguration - Flight Booking Application implements a set of REST
 * endpoints to manage products we will create a Docket bean in a Spring Boot
 * configuration to configure Swagger 2 for the application. A Springfox Docket
 * instance provides the primary API configuration with sensible defaults and
 * convenience methods for configuration
 * 
 * @author Govindasamy.C
 * @since 03-02-2019
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

	/**
	 * we will create a Docket bean in a Spring Boot configuration to configure
	 * Swagger 2 for the application
	 * 
	 * @return docket object
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();
	}
}
