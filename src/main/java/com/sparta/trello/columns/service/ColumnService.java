package com.sparta.trello.columns.service;

import com.sparta.trello.board.entity.Board;
import com.sparta.trello.board.service.BoardService;
import com.sparta.trello.columns.dto.ColumnRequestDTO;
import com.sparta.trello.columns.dto.ColumnResponseDTO;
import com.sparta.trello.columns.entity.Columns;
import com.sparta.trello.columns.repository.ColumnsRepository;
import com.sparta.trello.user.entity.User;
import com.sparta.trello.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColumnService {
    private final ColumnsRepository columnsRepository;
    private final BoardService boardService;

    // 컬럼 생성
    public void createColumn(Long boardId, ColumnRequestDTO columnRequestDTO, User user) {
        Board board = boardService.findByBoard(boardId);

        if(columnRequestDTO.getColumnName() == null) {
            throw new IllegalArgumentException("컬럼 이름을 입력하세요.");
        } else if(columnsRepository.findByColumnName(columnRequestDTO.getColumnName()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 컬럼 이름입니다.");
        }

        Long columnIndex = columnsRepository.countByBoardId(boardId)+1L;

        columnsRepository.save(new Columns(board, columnRequestDTO.getColumnName(), columnIndex, user));
    }


    // 컬럼 조회
    public List<ColumnResponseDTO> getColumnList() {
        return columnsRepository.findAll().stream()
                .map(ColumnResponseDTO::new)
                .collect(Collectors.toList());
    }

    // 컬럼 이름 수정
    @Transactional
    public void updateColumnName(Long columnId, ColumnRequestDTO columnRequestDTO, User user) {
        Columns column = getColumn(user, columnId);

        column.setColumnName(columnRequestDTO.getColumnName());
    }

    // 컬럼 삭제
    public void deleteColumn(Long columnId, User user) {
        Columns column = getColumn(user, columnId);

        columnsRepository.delete(column);
    }

    // 컬럼 순서 이동
    @Transactional
    public void moveColumn(Long columnId, Long targetIndex, User user) {
        Columns column = getColumn(user, columnId);
        Long currentColumnIndex = column.getColumnIndex();

        // 해당 컬럼이 포함된 보드의 모든 컬럼 가져오기
        List<Columns> columnsList = columnsRepository.findByBoardId(column.getBoard().getId());

        for(Columns columns: columnsList) {
            Long columnsIndex = columns.getColumnIndex();

            if(currentColumnIndex < targetIndex) {
                if(columnsIndex > currentColumnIndex && columnsIndex <= targetIndex) {
                    columns.setColumnIndex(columnsIndex-1);
                }
            } else if (currentColumnIndex > targetIndex) {
                if(columnsIndex >= targetIndex && columnsIndex < currentColumnIndex) {
                    columns.setColumnIndex(columnsIndex+1);
                }
            }
        }
        column.setColumnIndex(targetIndex);
    }

    public Columns getColumn(User user, Long columnId) {
        Columns column = columnsRepository.findById(columnId)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 columnId 입니다."));

        boolean isAdmin = user.getRole().equals(UserRoleEnum.ADMIN);

        if (column.getUser().getId().equals(user.getId()) || isAdmin) {
            return column;
        } else {
            throw new RejectedExecutionException("작성자 및 관리자만 댓글을 삭제할 수 있습니다.");
        }
    }

}
