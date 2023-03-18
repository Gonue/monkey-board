package com.sh.board.dto.response;


import com.sh.board.dto.ArticleWithCommentsDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

//TODO:나중에 어노테이션 변경
@Data
public class ArticleWithCommentResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String hashtag;
    private final LocalDateTime createdAt;
    private final String email;
    private final String nickname;
    private final Set<ArticleCommentResponse> articleCommentResponses;

    public static ArticleWithCommentResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname, Set<ArticleCommentResponse> articleCommentResponses) {
        return new ArticleWithCommentResponse(id, title, content, hashtag, createdAt, email, nickname, articleCommentResponses);
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
                dto.getHashtag(),
                dto.getCreatedAt(),
                dto.getUserAccountDto().getEmail(),
                nickname,
                dto.getArticleCommentDtos().stream()
                        .map(ArticleCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new))
        );
    }
}
