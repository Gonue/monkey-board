package com.sh.board.service;

import com.sh.board.dto.ArticleCommentDto;
import com.sh.board.repository.ArticleCommentRepository;
import com.sh.board.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment(Long articleId) {
        return List.of();
    }
    public void saveArticleComment(ArticleCommentDto dto){}

    public void updateArticleComment(ArticleCommentDto dto){}

    public void deleteArticleComment(Long articleCommentId){}
}
