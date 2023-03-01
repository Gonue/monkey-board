package com.sh.board.service;

import com.sh.board.domain.Article;
import com.sh.board.domain.type.SearchType;
import com.sh.board.dto.ArticleDto;
import com.sh.board.dto.ArticleUpdateDto;
import com.sh.board.repository.ArticleRepository;
import org.assertj.core.api.BDDAssertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.xmlunit.util.Linqy;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @InjectMocks
    private ArticleService sut;
    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("게시글 검색시 게시글 리스트 반환")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList(){
        //Given
        //When
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword"); //제목, 본문, ID, 닉네임, 해시태그
        //Then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글 요청시 게시글 반환")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle(){
        //Given

        //When
        ArticleDto articles = sut.searchArticle(1L);
        //Then
        assertThat(articles).isNotNull();
    }

    @DisplayName("게시글 정보를 입력시 게시글 생성")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticle(){
        //Given
        given(articleRepository.save(any(Article.class))).willReturn(null);
        //When
        sut.saveArticle(ArticleDto.of(LocalDateTime.now(), "kim", "title", "content", "hashtag"));
        //Then
        BDDMockito.then(articleRepository).should().save(ArgumentMatchers.any(Article.class));
    }
    @DisplayName("게시글 ID와 수정 정보를 입력시 게시글 수정")
    @Test
    void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticle(){
        //Given
        given(articleRepository.save(any(Article.class))).willReturn(null);
        //When
        sut.updateArticle(1L, ArticleUpdateDto.of("title", "content", "hashtag"));
        //Then
        BDDMockito.then(articleRepository).should().save(ArgumentMatchers.any(Article.class));
    }

    @DisplayName("게시글 ID 입력시 게시글 삭제")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticle(){
        //Given
        willDoNothing().given(articleRepository).delete(any(Article.class));
        //When
        sut.deleteArticle(1L);
        //Then
        BDDMockito.then(articleRepository).should().delete(ArgumentMatchers.any(Article.class));
    }
}