package com.sh.board.dto;

import com.sh.board.domain.Article;
import com.sh.board.domain.UserAccount;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final Set<HashtagDto> hashtagDtos;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;

    public static ArticleDto of(UserAccountDto userAccountDto, String title, String content, Set<HashtagDto> hashtagDtos) {
        return new ArticleDto(null, userAccountDto, title, content, hashtagDtos, null, null, null, null);
    }

    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, Set<HashtagDto> hashtagDtos, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtagDtos, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static ArticleDto from(Article entity) {
        return new ArticleDto(
                entity.getId(),
                UserAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtags().stream()
                        .map(HashtagDto::from)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }

    public Article toEntity(UserAccount userAccount) {
        return Article.of(
                userAccount,
                title,
                content
        );
    }
}