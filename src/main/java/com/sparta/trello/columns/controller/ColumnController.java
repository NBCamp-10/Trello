package com.sparta.trello.columns.controller;

import com.sparta.trello.common.dto.CommonResponseDTO;
import com.sparta.trello.columns.dto.ColumnRequestDTO;
import com.sparta.trello.columns.dto.ColumnResponseDTO;
import com.sparta.trello.columns.service.ColumnService;
import com.sparta.trello.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api/columns")
@RequiredArgsConstructor
public class ColumnController {
    private final ColumnService columnService;

    // 컬럼 생성
    @PostMapping("/{boardId}")
    public ResponseEntity<CommonResponseDTO> createColumn(@PathVariable(name = "boardId") Long boardId, @RequestBody ColumnRequestDTO columnRequestDTO,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            columnService.createColumn(boardId, columnRequestDTO, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDTO("컬럼 생성 완료.", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 컬럼 조회
    @GetMapping
    public ResponseEntity<List<ColumnResponseDTO>> getColumnList() {
        List<ColumnResponseDTO> responseDTO = columnService.getColumnList();
        return ResponseEntity.ok().body(responseDTO);
    }

    // 컬럼 이름 수정
    @PatchMapping("/{columnId}")
    public ResponseEntity<CommonResponseDTO> updateColumnName(@PathVariable(name = "columnId") Long columnId, @RequestBody ColumnRequestDTO columnRequestDTO,
                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            columnService.updateColumnName(columnId, columnRequestDTO, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDTO("컬럼 이름 수정 완료.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 컬럼 삭제
    @DeleteMapping("/{columnId}")
    public ResponseEntity<CommonResponseDTO> deleteColumn(@PathVariable(name = "columnId") Long columnId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            columnService.deleteColumn(columnId, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDTO("컬럼 삭제 완료.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 컬럼 순서 이동
    @PatchMapping("/move/{columnId}")
    public ResponseEntity<CommonResponseDTO> moveColumn(@PathVariable(name = "columnId") Long columnId, @RequestBody ColumnRequestDTO columnRequestDTO,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            columnService.moveColumn(columnId, columnRequestDTO.getColumnIndex(), userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDTO("컬럼 이동 완료.", HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

}
