package com.sh.board.service;

import com.sh.board.domain.Article;
import com.sh.board.domain.ArticleComment;
import com.sh.board.domain.UserAccount;
import com.sh.board.dto.ArticleCommentDto;
import com.sh.board.repository.ArticleCommentRepository;
import com.sh.board.repository.ArticleRepository;
import com.sh.board.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleCommentService {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment(Long articleId) {
        return articleCommentRepository.findByArticle_Id(articleId)
                .stream()
                .map(ArticleCommentDto::from)
                .collect(Collectors.toList());
    }
    public void saveArticleComment(ArticleCommentDto dto){
        try {
            Article article = articleRepository.getReferenceById(dto.getArticleId());
            UserAccount userAccount = userAccountRepository.getReferenceById(dto.getUserAccountDto().getUserId());
            articleCommentRepository.save(dto.toEntity(article,userAccount));
        } catch (EntityNotFoundException e){
            log.warn("댓글 저장 실패, 댓글 작성에 필요한 정보를 찾을수 없음. - {}", e.getLocalizedMessage());
        }
    }

    public void updateArticleComment(ArticleCommentDto dto){
        try {
            ArticleComment articleComment = articleCommentRepository.getReferenceById(dto.getId());
            if(dto.getContent() != null){
                articleComment.setContent(dto.getContent());
            }
        }catch (EntityNotFoundException e){
            log.warn("댓글 업데이트 실패, 댓글을 찾을 수 없음. - dto : {}" , dto);
        }
    }

    public void deleteArticleComment(Long articleCommentId, String  userId){
        articleCommentRepository.deleteByIdAndUserAccount_UserId(articleCommentId, userId);
    }
}
