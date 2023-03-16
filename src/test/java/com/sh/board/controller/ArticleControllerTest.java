//package com.sh.board.controller;
//
//import com.sh.board.config.SecurityConfig;
//import com.sh.board.domain.constant.FormStatus;
//import com.sh.board.domain.type.SearchType;
//import com.sh.board.dto.ArticleDto;
//import com.sh.board.dto.ArticleWithCommentsDto;
//import com.sh.board.dto.UserAccountDto;
//import com.sh.board.dto.request.ArticleRequest;
//import com.sh.board.response.ArticleResponse;
//import com.sh.board.service.ArticleService;
//import com.sh.board.service.PaginationService;
//import com.sh.board.util.FormDataEncoder;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.BDDMockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Set;
//
//import static org.assertj.core.api.BDDAssertions.then;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.BDDMockito.willDoNothing;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
//import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@DisplayName("viewController - 게시글")
//@Import({SecurityConfig.class, FormDataEncoder.class})
//@WebMvcTest(ArticleController.class)
//class ArticleControllerTest {
//    private final MockMvc mvc;
//    private final FormDataEncoder formDataEncoder;
//    @MockBean private ArticleService articleService;
//    @MockBean private PaginationService paginationService;
//    public ArticleControllerTest(@Autowired MockMvc mvc, @Autowired FormDataEncoder formDataEncoder) {
//        this.mvc = mvc;
//        this.formDataEncoder = formDataEncoder;
//    }
//    @DisplayName("[view] {GET} 게시글 리스트 (게시판)페이지 - 정상호출")
//    @Test
//    public void given_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {
//        //Given
//        given(articleService.searchArticles(ArgumentMatchers.eq(null),ArgumentMatchers.eq(null), any(Pageable.class)))
//                        .willReturn(Page.empty());
//        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0,1,2,3,4));
//        //When & Then
//        mvc.perform(get("/articles"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                .andExpect(view().name("articles/index"))
//                .andExpect(model().attributeExists("articles"))
//                .andExpect(model().attributeExists("paginationBarNumbers"));
//        BDDMockito.then(articleService).should().searchArticles(eq(null),eq(null), any(Pageable.class));
//        BDDMockito.then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
//    }
//    @DisplayName("[view] {GET} 게시글 리스트 (게시판)페이지 - 검색어와 호출")
//    @Test
//    public void givenSerachKeyword_whenSearchingArticlesView_thenReturnsArticlesView() throws Exception {
//        //Given
//        SearchType searchType = SearchType.TITLE;
//        String searchValue = "title";
//        given(articleService.searchArticles(ArgumentMatchers.eq(searchType),ArgumentMatchers.eq(searchValue), any(Pageable.class)))
//                        .willReturn(Page.empty());
//        given(paginationService.getPaginationBarNumbers(anyInt(), anyInt())).willReturn(List.of(0,1,2,3,4));
//        //When & Then
//        mvc.perform(get("/articles")
//                        .queryParam("searchType", searchType.name())
//                        .queryParam("searchValue", searchValue)
//                )
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                .andExpect(view().name("articles/index"))
//                .andExpect(model().attributeExists("articles"))
//                .andExpect(model().attributeExists("searchTypes"));
//        BDDMockito.then(articleService).should().searchArticles(eq(searchType),eq(searchValue), any(Pageable.class));
//        BDDMockito.then(paginationService).should().getPaginationBarNumbers(anyInt(), anyInt());
//    }
//    @DisplayName("[view] {GET} 게시글 상세 페이지 - 정상호출")
//        @Test
//        public void given_whenRequestingArticleView_thenReturnsArticleView() throws Exception {
//            //Given
//            Long articleId = 1L;
//            given(articleService.getArticle(articleId)).willReturn(createArticleWithCommentsDto());
//            //When & Then
//            mvc.perform(get("/articles/" + articleId))
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                    .andExpect(view().name("articles/detail"))
//                    .andExpect(model().attributeExists("article"))
//                    .andExpect(model().attributeExists("articleComments"));
//            BDDMockito.then(articleService).should().getArticle(articleId);
//        }
//    @Disabled("구현 중")
//    @DisplayName("[view] {GET} 게시글 검색 전용 페이지 - 정상호출")
//            @Test
//            public void given_whenRequestingArticleSearchView_thenReturnsArticleSearchView() throws Exception {
//                //Given
//
//                //When & Then
//                mvc.perform(get("/articles/search"))
//                        .andExpect(status().isOk())
//                        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                        .andExpect(model().attributeExists("article/search"));
//
//            }
//    @Disabled("구현 중")
//    @DisplayName("[view] {GET} 게시글 해시태그 검색 페이지 - 정상호출")
//            @Test
//            public void given_whenRequestingArticleHashtagSearchView_thenReturnsArticleHashtagSearchView() throws Exception {
//                //Given
//
//                //When & Then
//                mvc.perform(get("/articles/search-hashtag"))
//                        .andExpect(status().isOk())
//                        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                        .andExpect(model().attributeExists("article/search-hashtag"));
//            }
//
//    private ArticleWithCommentsDto createArticleWithCommentsDto(){
//        return ArticleWithCommentsDto.of(
//                1L,
//                createUserAccountDto(),
//                Set.of(),
//                "title",
//                "content",
//                "#java",
//                LocalDateTime.now(),
//                "kim",
//                LocalDateTime.now(),
//                "kim"
//        );
//    }
//    @DisplayName("[view][GET] 새 게시글 작성 페이지")
//        @Test
//        void givenNothing_whenRequesting_thenReturnsNewArticlePage() throws Exception {
//            // Given
//
//            // When & Then
//            mvc.perform(get("/articles/form"))
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                    .andExpect(view().name("articles/form"))
//                    .andExpect(model().attribute("formStatus", FormStatus.CREATE));
//        }
//
//        @DisplayName("[view][POST] 새 게시글 등록 - 정상 호출")
//        @Test
//        void givenNewArticleInfo_whenRequesting_thenSavesNewArticle() throws Exception {
//            // Given
//            ArticleRequest articleRequest = ArticleRequest.of("new title", "new content", "#new");
//            willDoNothing().given(articleService).saveArticle(any(ArticleDto.class));
//
//            // When & Then
//            mvc.perform(
//                    post("/articles/form")
//                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                            .content(formDataEncoder.encode(articleRequest))
//                            .with(csrf())
//            )
//                    .andExpect(status().is3xxRedirection())
//                    .andExpect(view().name("redirect:/articles"))
//                    .andExpect(redirectedUrl("/articles"));
//            then(articleService).should().saveArticle(any(ArticleDto.class));
//        }
//
//        @DisplayName("[view][GET] 게시글 수정 페이지")
//        @Test
//        void givenNothing_whenRequesting_thenReturnsUpdatedArticlePage() throws Exception {
//            // Given
//            long articleId = 1L;
//            ArticleDto dto = createArticleDto();
//            given(articleService.getArticle(articleId)).willReturn(dto);
//
//            // When & Then
//            mvc.perform(get("/articles/" + articleId + "/form"))
//                    .andExpect(status().isOk())
//                    .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
//                    .andExpect(view().name("articles/form"))
//                    .andExpect(model().attribute("article", ArticleResponse.from(dto)))
//                    .andExpect(model().attribute("formStatus", FormStatus.UPDATE));
//            then(articleService).should().getArticle(articleId);
//        }
//
//        @DisplayName("[view][POST] 게시글 수정 - 정상 호출")
//        @Test
//        void givenUpdatedArticleInfo_whenRequesting_thenUpdatesNewArticle() throws Exception {
//            // Given
//            long articleId = 1L;
//            ArticleRequest articleRequest = ArticleRequest.of("new title", "new content", "#new");
//            willDoNothing().given(articleService).updateArticle(eq(articleId), any(ArticleDto.class));
//
//            // When & Then
//            mvc.perform(
//                    post("/articles/" + articleId + "/form")
//                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                            .content(formDataEncoder.encode(articleRequest))
//                            .with(csrf())
//            )
//                    .andExpect(status().is3xxRedirection())
//                    .andExpect(view().name("redirect:/articles/" + articleId))
//                    .andExpect(redirectedUrl("/articles/" + articleId));
//            then(articleService).should().updateArticle(eq(articleId), any(ArticleDto.class));
//        }
//
//        @DisplayName("[view][POST] 게시글 삭제 - 정상 호출")
//        @Test
//        void givenArticleIdToDelete_whenRequesting_thenDeletesArticle() throws Exception {
//            // Given
//            long articleId = 1L;
//            willDoNothing().given(articleService).deleteArticle(articleId);
//
//            // When & Then
//            mvc.perform(
//                    post("/articles/" + articleId + "/delete")
//                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                            .with(csrf())
//            )
//                    .andExpect(status().is3xxRedirection())
//                    .andExpect(view().name("redirect:/articles"))
//                    .andExpect(redirectedUrl("/articles"));
//            then(articleService).should().deleteArticle(articleId);
//        }
//
//
//        private ArticleDto createArticleDto() {
//            return ArticleDto.of(
//                    createUserAccountDto(),
//                    "title",
//                    "content",
//                    "#java"
//            );
//        }
//
//    private UserAccountDto createUserAccountDto(){
//        return UserAccountDto.of(
//                "Kim",
//                "1234",
//                "kim@gmail.com",
//                "Kim",
//                "memo",
//                LocalDateTime.now(),
//                "kim",
//                LocalDateTime.now(),
//                "kim"
//                );
//    }
//}