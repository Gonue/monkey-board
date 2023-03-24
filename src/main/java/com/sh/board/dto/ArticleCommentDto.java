package com.sh.board.dto;

import com.sh.board.domain.Article;
import com.sh.board.domain.ArticleComment;
import com.sh.board.domain.UserAccount;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.sh.board.domain.ArticleComment} entity
 */
//TODO:나중에 어노테이션 변경
@Data
public class ArticleCommentDto {
    private final Long id;
    private final Long articleId;
    private final UserAccountDto userAccountDto;
    private final Long parentCommentId;
    private final String content;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;

    public static ArticleCommentDto of(Long articleId, UserAccountDto userAccountDto, String content) {
    return ArticleCommentDto.of(articleId, userAccountDto, null, content);
    }

    public static ArticleCommentDto of(Long articleId, UserAccountDto userAccountDto, Long parentCommentId, String content) {
        return ArticleCommentDto.of(null, articleId, userAccountDto, parentCommentId, content,null,null,null,null);
    }

    public static ArticleCommentDto of(Long id, Long articleId, UserAccountDto userAccountDto, Long parentCommentId, String content, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
    return new ArticleCommentDto(id, articleId, userAccountDto, parentCommentId, content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleCommentDto from(ArticleComment entity){
        return new ArticleCommentDto(
                entity.getId(),
                entity.getArticle().getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getParentCommentId(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
    public ArticleComment toEntity(Article article, UserAccount userAccount){
        return ArticleComment.of(
                article,
                userAccount,
                content
        );
    }
}