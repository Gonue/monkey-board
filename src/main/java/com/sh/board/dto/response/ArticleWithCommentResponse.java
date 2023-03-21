package com.sh.board.dto.response;

import com.sh.board.dto.ArticleWithCommentsDto;
import com.sh.board.dto.HashtagDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ArticleWithCommentResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final Set<String> hashtags;
    private final LocalDateTime createdAt;
    private final String email;
    private final String nickname;
    private final String userId;
    private final Set<ArticleCommentResponse> articleCommentResponses;

    public static ArticleWithCommentResponse of(Long id, String title, String content, Set<String> hashtags, LocalDateTime createdAt, String email, String nickname, String userId,Set<ArticleCommentResponse> articleCommentResponses) {
        return new ArticleWithCommentResponse(id, title, content, hashtags, createdAt, email, nickname, userId, articleCommentResponses);
    }

    public static ArticleWithCommentResponse from(ArticleWithCommentsDto dto){
        String nickname = dto.getUserAccountDto().getNickname();
        if(nickname == null || nickname.isBlank()){
            nickname = dto.getUserAccountDto().getUserId();
        }
        return new ArticleWithCommentResponse(
                dto.getId(),
                dto.getTitle(),
                dto.getContent(),
                dto.getHashtagDtos().stream()
                                .map(HashtagDto::getHashtagName)
                                .collect(Collectors.toUnmodifiableSet()),
                dto.getCreatedAt(),
                dto.getUserAccountDto().getEmail(),
                nickname,
                dto.getUserAccountDto().getUserId(),
                dto.getArticleCommentDtos().stream()
                        .map(ArticleCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}
