package com.karma.compass.service;

import com.karma.compass.domain.kakao.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j      // for logging
@Service
@RequiredArgsConstructor
public class KakaoApiService {
    // @Value → inject value from application.yaml
    @Value("kazao.api.secret-key") private String kakaoApiSecretKey;
    @Value("kakao.api.base-url") private String kakaoApiBaseUrl;
    private final RestTemplate restTemplate;

    private URI getUri(String address){
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(kakaoApiBaseUrl);
        uriComponentsBuilder.queryParam("query", address);
        URI uri = uriComponentsBuilder.build().encode().toUri();
        log.info("[getUri] address:{} uri:{}", address, uri);
        return uri;
    }

    public KakaoApiResponseDto requestAddressSearch(String address){
        if (ObjectUtils.isEmpty(address)) return null;
        URI uri = getUri(address);
        HttpHeaders httpHeaders = new HttpHeaders();
        String authToken = String.format("KakaoAK %s", kakaoApiSecretKey);
        httpHeaders.set(HttpHeaders.AUTHORIZATION, authToken);
        HttpEntity httpEntity = new HttpEntity<>(httpHeaders);
        return restTemplate
                .exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponseDto.class)
                .getBody();
    }
}
