package com.sh.board.dto.request;

import com.sh.board.dto.ArticleDto;
import com.sh.board.dto.HashtagDto;
import com.sh.board.dto.UserAccountDto;
import lombok.Data;

import java.util.Set;

@Data
public class ArticleRequest {
    private final String title;
    private final String content;

    public static ArticleRequest of(String title, String content) {
            return new ArticleRequest(title, content);
    }

    public ArticleDto toDto(UserAccountDto userAccountDto){
        return toDto(userAccountDto, null);
    }
    public ArticleDto toDto(UserAccountDto userAccountDto, Set<HashtagDto> hashtagDtos) {
        return ArticleDto.of(
                userAccountDto,
                title,
                content,
                hashtagDtos
        );
    }
}
