package com.sh.board.dto;

import com.sh.board.domain.Hashtag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class HashtagWithArticlesDto {
    private final Long id;
    private final Set<ArticleDto> articles;
    private final String hashtagName;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;


    public static HashtagWithArticlesDto of(Set<ArticleDto> articles, String hashtagName) {
            return new HashtagWithArticlesDto(null, articles, hashtagName, null, null, null, null);
        }

        public static HashtagWithArticlesDto of(Long id, Set<ArticleDto> articles, String hashtagName, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
            return new HashtagWithArticlesDto(id, articles, hashtagName, createdAt, createdBy, modifiedAt, modifiedBy);
        }

        public static HashtagWithArticlesDto from(Hashtag entity) {
            return new HashtagWithArticlesDto(
                    entity.getId(),
                    entity.getArticles().stream()
                            .map(ArticleDto::from)
                            .collect(Collectors.toUnmodifiableSet())
                    ,
                    entity.getHashtagName(),
                    entity.getCreatedAt(),
                    entity.getCreatedBy(),
                    entity.getModifiedAt(),
                    entity.getModifiedBy()
            );
        }
        public Hashtag toEntity() {
            return Hashtag.of(hashtagName);
        }
}
