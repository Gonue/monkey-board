package com.sh.board.dto.security;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DTO - Kakao OAuth 2.0 인증 응답 데이터 테스트")
class KakaoOAuth2ResponseTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @DisplayName("인증 결과를 Map(deserialized json)으로 받으면, 카카오 인증 응답 객체로 변환한다.")
    @Test
    void givenMapFromJson_whenInstantiating_thenReturnsKakaoResponseObject() throws Exception {
        // Given
        String serializedResponse = "{\n" +
                "    \"id\": 1234567890,\n" +
                "    \"connected_at\": \"2022-01-02T00:12:34Z\",\n" +
                "    \"properties\": {\n" +
                "        \"nickname\": \"홍길동\"\n" +
                "    },\n" +
                "    \"kakao_account\": {\n" +
                "        \"profile_nickname_needs_agreement\": false,\n" +
                "        \"profile\": {\n" +
                "            \"nickname\": \"홍길동\"\n" +
                "        },\n" +
                "        \"has_email\": true,\n" +
                "        \"email_needs_agreement\": false,\n" +
                "        \"is_email_valid\": true,\n" +
                "        \"is_email_verified\": true,\n" +
                "        \"email\": \"test@gmail.com\"\n" +
                "    }\n" +
                "}";
        Map<String, Object> attributes = mapper.readValue(serializedResponse, new TypeReference<>() {});

        // When
        KakaoOAuth2Response result = KakaoOAuth2Response.from(attributes);

        // Then
        assertThat(result)
                .hasFieldOrPropertyWithValue("id", 1234567890L)
                .hasFieldOrPropertyWithValue("connectedAt", ZonedDateTime.parse("2022-01-02T00:12:34Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                        .withZoneSameInstant(ZoneId.systemDefault())
                        .toLocalDateTime()
                )
                .hasFieldOrPropertyWithValue("kakaoAccount.profile.nickname", "홍길동")
                .hasFieldOrPropertyWithValue("kakaoAccount.email", "test@gmail.com");
    }

}