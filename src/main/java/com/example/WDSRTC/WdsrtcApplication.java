package com.example.WDSRTC;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WdsrtcApplication {

	@Bean
	public SocketIOServer socketIOServer() {
		Configuration config = new Configuration();
		config.setPort(9092);
		config.setOrigin("*");
		return new SocketIOServer(config);
	}

	public static void main(String[] args) {
		SpringApplication.run(WdsrtcApplication.class, args);
	}

}
