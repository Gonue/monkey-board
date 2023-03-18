package com.sh.board.dto.request;

import com.sh.board.dto.ArticleCommentDto;
import com.sh.board.dto.UserAccountDto;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.sh.board.domain.ArticleComment} entity
 */
@Data
public class ArticleCommentRequest implements Serializable {
    private final Long articleId;
    private final String content;


    public static ArticleCommentRequest of (Long articleId, String content){
        return new ArticleCommentRequest(articleId, content);
    }

    public ArticleCommentDto toDto(UserAccountDto userAccountDto){
        return ArticleCommentDto.of(
                articleId,
                userAccountDto,
                content
        );
    }
}