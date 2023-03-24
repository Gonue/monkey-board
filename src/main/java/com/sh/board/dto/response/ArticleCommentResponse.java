package com.sh.board.dto.response;

import com.sh.board.dto.ArticleCommentDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

//TODO:나중에 어노테이션 변경
@Data
public class ArticleCommentResponse {
    private final Long id;
    private final String content;
    private final LocalDateTime createdAt;
    private final String email;
    private final String nickname;
    private final String userId;
    private final Long parentCommentId;
    private final Set<ArticleCommentResponse> childComments;

    public static ArticleCommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId) {
            return ArticleCommentResponse.of(id, content, createdAt, email, nickname, userId,null);
    }
    public static ArticleCommentResponse of(Long id, String content, LocalDateTime createdAt, String email, String nickname, String userId, Long parentCommentId) {
            Comparator<ArticleCommentResponse> childCommentComparator = Comparator
                    .comparing(ArticleCommentResponse::getCreatedAt)
                    .thenComparingLong(ArticleCommentResponse::getId);
            return new ArticleCommentResponse(id, content, createdAt, email, nickname, userId, parentCommentId, new TreeSet<>(childCommentComparator));
    }

    public static ArticleCommentResponse from(ArticleCommentDto dto){
        String nickname = dto.getUserAccountDto().getNickname();
        if(nickname == null || nickname.isBlank()){
            nickname = dto.getUserAccountDto().getUserId();
        }
        return ArticleCommentResponse.of(
                dto.getId(),
                dto.getContent(),
                dto.getCreatedAt(),
                dto.getUserAccountDto().getEmail(),
                nickname,
                dto.getUserAccountDto().getUserId(),
                dto.getParentCommentId()
        );
    }

    public boolean hasParentComment(){
        return parentCommentId != null;
    }
}
