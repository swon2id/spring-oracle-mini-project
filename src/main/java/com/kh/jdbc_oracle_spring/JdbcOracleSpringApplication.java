package com.kh.jdbc_oracle_spring;

import com.kh.jdbc_oracle_spring.vo.MemberVo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JdbcOracleSpringApplication {
	public static Integer currMemberNum = null;
	public static List<MemberVo> members = new ArrayList<>();
	public static void main(String[] args) {
		SpringApplication.run(JdbcOracleSpringApplication.class, args);
	}
}
