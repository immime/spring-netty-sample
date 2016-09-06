package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationProperties(prefix = "netty")
public class Application {

	private final int port = 9090;

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
		NettyServer bean = applicationContext.getBean(NettyServer.class);
		bean.start();
	}

	@Bean
	public NettyServer nettyServer() {
		return new NettyServer(1, 10, port);
	}
}
