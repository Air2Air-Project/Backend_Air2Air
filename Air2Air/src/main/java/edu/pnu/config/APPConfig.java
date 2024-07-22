package edu.pnu.config;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class APPConfig {
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	ObjectMapper objectMapper() {
		ObjectMapper objectMapper = JsonMapper.builder().enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS).build()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
		return objectMapper;
	}
}
