package com.sh.board.dto.request;

import com.sh.board.dto.ArticleDto;
import com.sh.board.dto.UserAccountDto;
import lombok.Data;

@Data
public class ArticleRequest {
    private final String title;
    private final String content;
    private final String hashtag;

    public static ArticleRequest of(String title, String content, String hashtag) {
            return new ArticleRequest(title, content, hashtag);
        }
        public ArticleDto toDto(UserAccountDto userAccountDto) {
            return ArticleDto.of(
                    userAccountDto,
                    title,
                    content,
                    hashtag
            );
        }
}
