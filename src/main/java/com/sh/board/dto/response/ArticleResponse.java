package com.sh.board.dto.response;


import com.sh.board.dto.ArticleDto;
import com.sh.board.dto.HashtagDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ArticleResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final Set<String> hashtags;
    private final LocalDateTime createdAt;
    private final String email;
    private final String nickname;


    public static ArticleResponse of(Long id, String title, String content, Set<String> hashtags, LocalDateTime createdAt, String email, String nickname) {
        return new ArticleResponse(id, title, content, hashtags, createdAt, email, nickname);
    }

    public static ArticleResponse from(ArticleDto dto){
        String nickname = dto.getUserAccountDto().getNickname();
        if(nickname == null || nickname.isBlank()){
            nickname = dto.getUserAccountDto().getUserId();
        }
        return new ArticleResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getHashtagDtos().stream()
                                .map(HashtagDto::getHashtagName)
                                        .collect(Collectors.toUnmodifiableSet()),
                dto.getCreatedAt(),
                dto.getUserAccountDto().getEmail(),
                nickname
        );
    }
}
