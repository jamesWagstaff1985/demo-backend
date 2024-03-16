package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication {
	static String broker = "tcp://localhost:1883";
	static String clientId = "demo_client";

	public static void main(String[] args) throws MqttException {
		SpringApplication.run(DemoApplication.class, args);
	}

	@PostConstruct
	void init() {

		//IMqttClient subscriber = Mqtt.getInstance();
		IMqttClient client = Mqtt.getInstance();

		try {

		client.subscribe("master/topic", (topic, message) -> {
			new Thread(() -> {
				try {
					ObjectMapper mapper = new ObjectMapper();
					SlaveItem item = mapper.readValue(message.getPayload(), SlaveItem.class);
					System.out.println(item.toString());

					Random random = new Random();
					int randomValue = random.nextInt(3) + 1; // Generates 1, 2, or 3
					System.out.println(randomValue);

					TileItem tileItem = new TileItem(item.getSlaveTopic(), randomValue, 255);
					System.out.println(tileItem);
					MqttMessage mqttMessage = new MqttMessage(mapper.writeValueAsBytes(tileItem));
					mqttMessage.setQos(0);

					System.out.println("Sending message: " + mapper.writeValueAsString(tileItem));
					client.publish(item.getSlaveTopic(), mqttMessage);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}).start();

		});
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
