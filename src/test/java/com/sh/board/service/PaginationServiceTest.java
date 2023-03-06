package com.sh.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
ㄴ
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("비즈니스 로직 - 페이지네이션")
@SpringBootTest
class PaginationServiceTest {
    private final PaginationService sut;

    public PaginationServiceTest(@Autowired PaginationService sut) {
        this.sut = sut;
    }

//    @DisplayName("현재 페이지 번호와 총 페이지 수가 주어질때, 페이징 바 리스트 생성")
//    @MethodSource
//    @ParameterizedTest
//    void givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers(int currentPageNumber, int totalPages, List<Integer> expected){
//        //Given
//
//        //When
//        sut.getPaginationBarNumbers();
//        //Then
//    }
}