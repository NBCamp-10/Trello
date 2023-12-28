package com.sparta.trello.columns.service;

import com.sparta.trello.columns.dto.ColumnRequestDTO;
import com.sparta.trello.columns.dto.ColumnResponseDTO;
import com.sparta.trello.columns.entity.Columns;
import com.sparta.trello.columns.repository.ColumnsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColumnService {
    private final ColumnsRepository columnsRepository;
    private final BoardService boardService;

    // 컬럼 생성
    public void createColumn(Long boardId, ColumnRequestDTO columnRequestDTO) {
        Board board = boardService.findBoard(boardId);

        if(columnRequestDTO.getColumnName().isEmpty()) {
            throw new IllegalArgumentException("컬럼 이름을 입력하세요.");
        }

        // 컬럼 생성 권한? 확인

        columnsRepository.save(new Columns(board, columnRequestDTO));
    }


    // 컬럼 조회
    public List<ColumnResponseDTO> getColumnList() {
        return columnsRepository.findAll().stream()
                .map(ColumnResponseDTO::new)
                .collect(Collectors.toList());
    }

    // 컬럼 이름 수정
    @Transactional
    public void updateColumnName(Long columnId, ColumnRequestDTO columnRequestDTO) {
        Columns columns = columnsRepository.findById(columnId)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 columnId 입니다."));

        // 사용자 권한 확인

        columns.setColumnName(columnRequestDTO.getColumnName());
    }

    // 컬럼 삭제
    public void deleteColumn(Long columnId) {
        Columns columns = columnsRepository.findById(columnId)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 columnId 입니다."));

        // 사용자 권한 확인

        columnsRepository.delete(columns);
    }

    // 컬럼 순서 이동
    public void moveColumn(Long columnId, Long columnIndex) {
        Columns column = columnsRepository.findById(columnId)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 columnId 입니다."));

        Optional<Columns> existcolumns = columnsRepository.findByColumnIndex(columnIndex);

        if(existcolumns.isPresent()) {
            Columns exitcolumn = existcolumns.get();

            Long tmpIndex = exitcolumn.getColumnIndex();
            exitcolumn.setColumnIndex(column.getColumnIndex());
            column.setColumnIndex(tmpIndex);

            columnsRepository.save(exitcolumn);
            columnsRepository.save(column);
        } else {
            column.setColumnIndex(columnIndex);
            columnsRepository.save(column);
        }

    }
}
