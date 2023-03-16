package com.sh.board.controller;

import com.sh.board.config.SecurityConfig;
import com.sh.board.domain.type.SearchType;
import com.sh.board.dto.ArticleWithCommentsDto;
import com.sh.board.dto.UserAccountDto;
import com.sh.board.service.ArticleService;
import com.sh.board.service.PaginationService;
import io.micrometer.core.instrument.search.Search;
import org.assertj.core.api.BDDAssertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("viewController - 게시글")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {
    private final MockMvc mvc;
    @MockBean private ArticleService articleService;
    @MockBean private PaginationService paginationService;
    public ArticleControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }
    @DisplayName("[view] {GET} 게시글 리스트 (게시판)페이지 - 정상호출")
    @Test
    public void given_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
        //Given
        BDDMockito.given(articleService.searchArticles(ArgumentMatchers.eq(null),ArgumentMatchers.eq(null), any(Pageable.class)))
                        .willReturn(Page.empty());
        BDDMockito.given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0,1,2,3,4));
        //When & Then
        mvc.perform(get("/articles"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("paginationBarNumbers"));
        BDDMockito.then(articleService).should().searchArticles(eq(null),eq(null), any(Pageable.class));
        BDDMockito.then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }
    @DisplayName("[view] {GET} 게시글 리스트 (게시판)페이지 - 검색어와 호출")
    @Test
    public void givenSerachKeyword_whenSearchingArticlesView_thenReturnsArticlesView() throws Exception {
        //Given
        SearchType searchType = SearchType.TITLE;
        String searchValue = "title";
        BDDMockito.given(articleService.searchArticles(ArgumentMatchers.eq(searchType),ArgumentMatchers.eq(searchValue), any(Pageable.class)))
                        .willReturn(Page.empty());
        BDDMockito.given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0,1,2,3,4));
        //When & Then
        mvc.perform(get("/articles")
                        .queryParam("searchType", searchType.name())
                        .queryParam("searchValue", searchValue)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("articles/index"))
                .andExpect(model().attributeExists("articles"))
                .andExpect(model().attributeExists("searchTypes"));
        BDDMockito.then(articleService).should().searchArticles(eq(searchType),eq(searchValue), any(Pageable.class));
        BDDMockito.then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
    }
    @DisplayName("[view] {GET} 게시글 상세 페이지 - 정상호출")
        @Test
        public void given_whenRequestingArticleView_thenReturnsArticleView() throws Exception {
            //Given
            Long articleId = 1L;
            BDDMockito.given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());
            //When & Then
            mvc.perform(get("/articles/" + articleId))
                    .andExpect(status().isOk())
                    .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                    .andExpect(view().name("articles/detail"))
                    .andExpect(model().attributeExists("article"))
                    .andExpect(model().attributeExists("articleComments"));
            BDDMockito.then(articleService).should().getArticle(articleId);
        }
    @Disabled("구현 중")
    @DisplayName("[view] {GET} 게시글 검색 전용 페이지 - 정상호출")
            @Test
            public void given_whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {
                //Given

                //When & Then
                mvc.perform(get("/articles/search"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                        .andExpect(model().attributeExists("article/search"));

            }
    @Disabled("구현 중")
    @DisplayName("[view] {GET} 게시글 해시태그 검색 페이지 - 정상호출")
            @Test
            public void given_whenRequestingArticleHashtagSearchView_thenReturnsArticleHashtagSearchView() throws Exception {
                //Given

                //When & Then
                mvc.perform(get("/articles/search-hashtag"))
                        .andExpect(status().isOk())
                        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                        .andExpect(model().attributeExists("article/search-hashtag"));
            }

    private ArticleWithCommentsDto createArticleWithCommentsDto(){
        return ArticleWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
                "#java",
                LocalDateTime.now(),
                "kim",
                LocalDateTime.now(),
                "kim"
        );
    }

    private UserAccountDto createUserAccountDto(){
        return UserAccountDto.of(
                1L,
                "Kim",
                "pw",
                "kim@gmail.com",
                "Kim",
                "memo",
                LocalDateTime.now(),
                "kim",
                LocalDateTime.now(),
                "kim"
                );
    }
}