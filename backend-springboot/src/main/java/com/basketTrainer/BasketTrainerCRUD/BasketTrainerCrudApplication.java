package com.basketTrainer.BasketTrainerCRUD;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class BasketTrainerCrudApplication implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

	private static final Logger logger = LoggerFactory.getLogger(BasketTrainerCrudApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BasketTrainerCrudApplication.class, args);
	}

	@Override
	public void customize(ConfigurableServletWebServerFactory factory) {
		String port = System.getenv("PORT");
		if (port != null) {
			int portNum = Integer.parseInt(port);
			factory.setPort(portNum);
			logger.info("Using port from Render: {}", portNum);
		} else {
			logger.warn("PORT environment variable is not set by Render, falling back to default (8080)");
			factory.setPort(8080); // Puerto por defecto como respaldo
		}
	}
}