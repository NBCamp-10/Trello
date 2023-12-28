package com.sparta.trello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling //@EnableScheduling 어노테이션을 추가함으로써 스케줄링이 활성화되며,
                  // @Scheduled 어노테이션이 사용된 메소드들이 정상적으로 동작하게 됩니다.
public class TrelloApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrelloApplication.class, args);
    }

}
