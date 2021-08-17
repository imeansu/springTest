package com.example.server.controller;

import com.example.server.dto.Req;
import com.example.server.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("/api/server")
public class ServerApiController {


//    query=%EA%B0%88%EB%B9%84%EC%A7%91
//    &display=10
//    &start=1
//    &sort=random

    @GetMapping("/test-flask")
    public String testFlask(){
        URI uri = UriComponentsBuilder
                .fromUriString("http://3.38.103.192")
                .path("/test-spring")
                .encode()
                .build()
                .toUri();
        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .build();
        System.out.println(uri);
        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        return result.getBody();
    }


    @GetMapping("/naver")
    public String naver(){

        String query = "갈비집";

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query", query)
                .queryParam("display", 10)
                .queryParam("start", 1)
                .queryParam("sort", "random")
                .encode(Charset.forName("UTF-8"))
                .build()
                .toUri();
        log.info("uri: {}", uri);

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "YvV8FboMufOYEBLO4gBq")
                .header("X-Naver-Client-Secret", "o2nvLAhARQ")
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);

        return result.getBody();
    }

    @GetMapping("hello")
    public User hello(@RequestParam String name, @RequestParam int age){
        User user = new User();
        user.setName(name);
        user.setAge(age);
        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}")
    public Req<User> post(
                    @RequestBody Req<User> user,
                     @PathVariable int userId,
                     @PathVariable String userName,
                     @RequestHeader("x-authorization") String authorization,
                     @RequestHeader("custom-header") String customHeader){
        log.info("userId : {}, userName: {}", userId, userName);
        log.info("authorization: {}, custom : {}", authorization, customHeader);
        log.info("client req : {}", user);

        Req<User> response = new Req<>();
        response.setHeader(
                new Req.Header()
        );
        response.setResBody(user.getResBody());

        return response;
    }
}
