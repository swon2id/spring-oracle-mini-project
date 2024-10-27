package com.kh.jdbc_oracle_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcOracleSpringApplication {
	public static Integer currMemberNum = null;
	public static void main(String[] args) {
		SpringApplication.run(JdbcOracleSpringApplication.class, args);
	}
}
