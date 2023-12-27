package com.sparta.trello.card.service;

import com.sparta.trello.card.entity.Card;
import com.sparta.trello.card.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class DeadlineAlertService {

    @Autowired
    private CardRepository cardRepository;


//     10초마다 실행되는 스케줄링 메소드.
//     모든 카드의 마감일을 체크하고 알람을 전송합니다.

    @Scheduled(fixedRate = 10000) // 10초마다 실행
    public void checkAndSendDeadlineAlertForAllCards() {
        List<Card> allCards = cardRepository.findAll();
        for (Card card : allCards) {
            checkAndSendDeadlineAlert(card);
        }
    }


//     특정 카드의 마감일을 체크하고 알람을 전송하는 메소드.
//     param card 체크할 카드

    public void checkAndSendDeadlineAlert(Card card) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (card.getDeadline() != null) {
            LocalDateTime deadline = card.getDeadline();

            // 마감일이 현재 시간보다 이전이라면 알람 전송
            if (deadline.isBefore(currentDateTime)) {
                sendDeadlineAlert(card);
            }
        }
    }

//    마감일이 지났을 때 알람을 보내는 메소드.
//    실제 알람 전송 로직을 추가해야 합니다.
//    card 알람을 보낼 카드

    private void sendDeadlineAlert(Card card) {
        // 여기에 알람을 보내는 로직을 추가
        System.out.println("마감일이 지났습니다. 알람을 보냅니다. Card ID: " + card.getId());
        // 알람 전송 로직을 여기에 추가
    }
}

