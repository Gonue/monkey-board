package com.sh.board.dto;

import com.sh.board.domain.Article;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
//TODO:나중에 어노테이션 변경
@Data
public class ArticleWithCommentsDto {
    private final Long id;
    private final UserAccountDto userAccountDto;
    private final Set<ArticleCommentDto> articleCommentDtos;
    private final String title;
    private final String content;
    private final Set<HashtagDto> hashtagDtos;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;

    public static ArticleWithCommentsDto of(Long id, UserAccountDto userAccountDto, Set<ArticleCommentDto> articleCommentDtos, String title, String content, Set<HashtagDto> hashtagDtos, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
            return new ArticleWithCommentsDto(id, userAccountDto, articleCommentDtos, title, content, hashtagDtos, createdAt, createdBy, modifiedAt, modifiedBy);
        }

        public static ArticleWithCommentsDto from(Article entity) {
            return new ArticleWithCommentsDto(
                    entity.getId(),
                    UserAccountDto.from(entity.getUserAccount()),
                    entity.getArticleComments().stream()
                            .map(ArticleCommentDto::from)
                            .collect(Collectors.toCollection(LinkedHashSet::new))
                    ,
                    entity.getTitle(),
                    entity.getContent(),
                    entity.getHashtags().stream()
                            .map(HashtagDto::from)
                            .collect(Collectors.toUnmodifiableSet())
                    ,
                    entity.getCreatedAt(),
                    entity.getCreatedBy(),
                    entity.getModifiedAt(),
                    entity.getModifiedBy());
    }
}
