package com.todoapp.todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//↓테스트 코드쪽에서 이게 방해해서 config쪽에 JpaConfig따로 만들고 거기서 설정해준다
//@EnableJpaAuditing //자동 시간 저장을 위한 어노테이션
@SpringBootApplication
public class TodoAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}

}
