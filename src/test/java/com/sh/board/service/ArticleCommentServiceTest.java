package com.sh.board.service;

import com.sh.board.domain.Article;
import com.sh.board.domain.ArticleComment;
import com.sh.board.dto.ArticleCommentDto;
import com.sh.board.repository.ArticleCommentRepository;
import com.sh.board.repository.ArticleRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("비즈니스 로직 - 댓글")
@ExtendWith(MockitoExtension.class)
class ArticleCommentServiceTest {
    @InjectMocks private ArticleCommentService sut;
    @Mock private ArticleRepository articleRepository;
    @Mock private ArticleCommentRepository articleCommentRepository;

    @Disabled
    @DisplayName("게시글 ID로 조회시 해당 댓글 리스트 반환")
    @Test
    void givenArticleId_whenSearchingComments_thenReturnsComments(){
        //Given
        Long articleId = 1L;
//        BDDMockito.given(articleRepository.findById(articleId)).willReturn(Optional.of(Article.of("1","")));
        //When
        List<ArticleCommentDto> articleComments = sut.searchArticleComment(articleId);
        //Then
        assertThat(articleComments).isNotNull();
        BDDMockito.then(articleRepository).should().findById(articleId);
    }

}