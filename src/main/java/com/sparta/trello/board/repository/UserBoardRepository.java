package com.sparta.trello.board.repository;

import com.sparta.trello.board.entity.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {
    Optional<UserBoard> findByBoardIdAndUserId(Long boardId, Long userId);

    
    //N+1 문제가 발생하여 FETCH JOIN을 이용해 해결
    @Query("SELECT ub FROM UserBoard ub JOIN FETCH ub.board WHERE ub.user.id = :userId")
    List<UserBoard> findAllByUserIdWithUserFetch(Long userId);

}