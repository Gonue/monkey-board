package com.sh.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DisplayName("비즈니스 로직 - 페이지네이션")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = PaginationService.class)
class PaginationServiceTest {
    private final PaginationService sut;

    public PaginationServiceTest(@Autowired PaginationService paginationService) {
        this.sut = paginationService;
    }

    @DisplayName("현재 페이지 번호와 총 페이지 수가 주어질때, 페이징 바 리스트 생성")
    @MethodSource
    @ParameterizedTest
    void givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers(int currentPageNumber, int totalPages, List<Integer> expected){
        //Given

        //When
        List<Integer> actual = sut.getPaginationBarNumbers(currentPageNumber,totalPages);
        //Then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments>givenCurrentPageNumberAndTotalPages_whenCalculating_thenReturnsPaginationBarNumbers(){
        return Stream.of(
                arguments(0, 10, List.of(0,1,2,3,4)),
                arguments(1, 10, List.of(0,1,2,3,4)),
                arguments(2, 10, List.of(0,1,2,3,4)),
                arguments(3, 10, List.of(1,2,3,4,5)),
                arguments(4, 10, List.of(2,3,4,5,6)),
                arguments(5, 10, List.of(3,4,5,6,7)),
                arguments(9, 10, List.of(7,8,9))
        );
    }

    @DisplayName("현재 설정되어 있는 페이지네이션 바의 길이")
    @Test
    void givenNothing_whenCalling_thenReturnsCurrentBarLength(){

        int barLength = sut.currentBarLength();

        assertThat(barLength).isEqualTo(5);
    }
}