package com.karma.compass.service

import spock.lang.Specification

import java.nio.charset.StandardCharsets

class KakaoApiServiceTest extends Specification {

    private KakaoApiService kakaoService

    def setup(){
        kakaoService = new KakaoApiService()
    }

    def "buildUri"() {
        given:
            String 한글주소 = "서울 상도동"
            def charset = StandardCharsets.UTF_8

        when:
            def uri = kakaoService.buildUri(한글주소)
            def 인코딩 = uri.toString()
            def 디코딩 = URLDecoder.decode(인코딩, charset)

        then:
            디코딩 ==  "https://dapi.kakao.com/v2/local/search/address.json?query=" + 한글주소
    }
}