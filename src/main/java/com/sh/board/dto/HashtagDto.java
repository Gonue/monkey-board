package com.sh.board.dto;

import com.sh.board.domain.Hashtag;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HashtagDto {
    private final Long id;
    private final String hashtagName;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;

    public static HashtagDto of(String hashtagName) {
            return new HashtagDto(null, hashtagName, null, null, null, null);
        }

        public static HashtagDto of(Long id, String hashtagName, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
            return new HashtagDto(id, hashtagName, createdAt, createdBy, modifiedAt, modifiedBy);
        }

        public static HashtagDto from(Hashtag entity) {
            return new HashtagDto(
                    entity.getId(),
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
