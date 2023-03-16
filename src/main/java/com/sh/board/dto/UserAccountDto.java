package com.sh.board.dto;

import com.sh.board.domain.UserAccount;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.sh.board.domain.UserAccount} entity
 */
//TODO:나중에 어노테이션 변경
@Data
public class UserAccountDto {
    private final String userId;
    private final String userPassword;
    private final String email;
    private final String nickname;
    private final String memo;
    private final LocalDateTime createdAt;
    private final String createdBy;
    private final LocalDateTime modifiedAt;
    private final String modifiedBy;

    public static UserAccountDto of(String userId, String userPassword, String email, String nickname, String memo) {
            return new UserAccountDto(userId, userPassword, email, nickname, memo, null, null, null, null);
        }
    public static UserAccountDto of(String userId, String userPassword, String email, String nickname, String memo, LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new UserAccountDto(userId, userPassword, email, nickname, memo, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static UserAccountDto from(UserAccount entity) {
        return new UserAccountDto(
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getMemo(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
    public UserAccount toEntity(){
        return UserAccount.of(
                userId,
                userPassword,
                email,
                nickname,
                memo);
    }
}
