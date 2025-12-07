package org.example.wedevelop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("org.example.wedevelop.mapper")
@SpringBootApplication
public class WeDevelopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeDevelopApplication.class, args);
	}

}
