package com.mooctest.service;

import com.google.gson.Gson;
import com.mooctest.domainObject.WordParser;
import com.mooctest.factory.WordParserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author guochao
 * @date 2020-05-15 18:30
 */
@Component
public class ParserAsyncImpl {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;
    @Value("${redis.timeout}")
    private int EXP_TIMES;
    @Value("${redis.feature}")
    private String REDIS_FEATURE;

    @Async
    public Future<String> asyncParserFile(MultipartFile uploadFile, String fileName, String token) throws IOException {
        WordParser wordParser = WordParserFactory.createWordParser();

//         设置临时的redis缓存，用于判断正在解析中
//        AsyncResult<String> asyncResult = new AsyncResult<>(token + "-" + REDIS_FEATURE);
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        valueOperations.set(token + "-" + asyncResult, true, EXP_TIMES, TimeUnit.SECONDS);

        wordParser.parser(uploadFile, fileName);
        // 存入redis中
        Gson gson = new Gson();
        String content = gson.toJson(wordParser);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(token, content, EXP_TIMES, TimeUnit.SECONDS);
        return new AsyncResult<String>("Result");
    }
}
