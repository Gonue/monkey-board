package com.sh.board.dto;

import com.sh.board.domain.Article;
import com.sh.board.domain.UserAccount;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.sh.board.domain.Article} entity
 */
//TODO:나중에 어노테이션 변경
@Data
public class ArticleDto {
    private final Long id;
    private final UserAccountDto userAccountDto;
    private final String title;
    private final String content;
    private final String hashtag;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;

    public static ArticleDto of(UserAccountDto userAccountDto, String title, String content, String hashtag) {
            return new ArticleDto(null, userAccountDto, title, content, hashtag, null, null, null, null);
        }
    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, String hashtag, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtag, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article entity){
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
    public Article toEntity(){
        return Article.of(userAccountDto.toEntity(),title,content,hashtag);
    }
}