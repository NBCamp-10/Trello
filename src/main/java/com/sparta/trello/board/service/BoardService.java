package com.sparta.trello.board.service;

import com.sparta.trello.board.dto.BoardRequestDto;
import com.sparta.trello.board.dto.BoardResponseDto;
import com.sparta.trello.board.dto.UserBoardResponseDto;
import com.sparta.trello.board.entity.Board;
import com.sparta.trello.board.entity.UserBoard;
import com.sparta.trello.board.repository.BoardRepository;
import com.sparta.trello.board.repository.UserBoardRepository;
import com.sparta.trello.user.entity.User;
import com.sparta.trello.user.entity.UserRoleEnum;
import com.sparta.trello.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final UserBoardRepository userBoardRepository;

    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        Board board = new Board(requestDto, user);
        boardRepository.save(board);

        UserBoard userBoard = new UserBoard(UserRoleEnum.ADMIN, board, user);
        userBoardRepository.save(userBoard);
        return new BoardResponseDto(board);
    }

    public List<UserBoardResponseDto> getUserIncludeBoard(User user) {

        return userBoardRepository.findAllByUserIdWithUserFetch(user.getId())
                .stream()
                .map(UserBoardResponseDto::new)
                .toList();
    }

    @Transactional
    public BoardResponseDto updateBoard(User user, Long boardId, BoardRequestDto requestDto) {
    
        //유저 초대 체크
        if (userBoardRepository.findByBoardIdAndUserId(boardId, user.getId()).isEmpty()) {
            throw new IllegalArgumentException("초대되지 않은 유저입니다..");
        }

        Board board = findByBoard(boardId);

        board.updateBoard(requestDto);
        return new BoardResponseDto(board);
    }

    public void deleteBoard(User user, Long boardId) {

        //관리자 체크
        Board board = findByBoard(boardId);
        if (!board.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("관리자만 삭제 가능합니다.");
        }

        boardRepository.delete(board);
    }
    @Transactional
    public void inviteUserToBoard(User user, Long boardId, Long invitedUserId) {

        //관리자 체크
        Board board = findByBoard(boardId);
        if (!board.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("관리자만 초대 가능합니다.");
        }

        User invitedUser = userRepository.findById(invitedUserId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Optional<UserBoard> userBoardOptional = userBoardRepository.findByBoardIdAndUserId(boardId, invitedUserId);
        if (userBoardOptional.isPresent()) {
            throw new IllegalArgumentException("이미 초대된 유저입니다.");
        }

        UserBoard userBoard = new UserBoard(UserRoleEnum.USER, board, invitedUser);
        userBoardRepository.save(userBoard);
    }

    @Transactional
    public void changeUserRoleInBoard(User user, Long boardId, Long userId) {
        //관리자 체크
        Board board = findByBoard(boardId);
        if (!board.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("관리자만 권한변경이 가능합니다.");
        }

        Optional<UserBoard> userBoardOptional = userBoardRepository.findByBoardIdAndUserId(boardId, userId);

        if (userBoardOptional.isPresent()) {
            UserBoard userBoard = userBoardOptional.get();

            if (userBoard.getRole() == UserRoleEnum.ADMIN) {
                userBoard.setRole((UserRoleEnum.USER));
            } else if (userBoard.getRole() == UserRoleEnum.USER) {
                userBoard.setRole(UserRoleEnum.ADMIN);
            }
        } else {
            throw new IllegalArgumentException("해당 보드에 유저가 없습니다.");
        }
    }

    public Board findByBoard(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("해당 보드가 존재하지 않습니다."));
    }


}
