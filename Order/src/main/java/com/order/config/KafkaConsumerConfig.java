
package com.order.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;

import com.order.event.PaymentResultEvent;

@Configuration
public class KafkaConsumerConfig {

	@Bean
	public ConsumerFactory<String, PaymentResultEvent> paymentConsumerFactory() {

		Map<String, Object> config = new HashMap<>();

		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

		config.put(ConsumerConfig.GROUP_ID_CONFIG, "order-group");

		config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);

		JacksonJsonDeserializer<PaymentResultEvent> deserializer = new JacksonJsonDeserializer<>(
				PaymentResultEvent.class);

		deserializer.setUseTypeHeaders(false);

		return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
	}

	@Bean(name = "kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, PaymentResultEvent> kafkaListenerContainerFactory() {

		var factory = new ConcurrentKafkaListenerContainerFactory<String, PaymentResultEvent>();

		factory.setConsumerFactory(paymentConsumerFactory());

		return factory;
	}
}
