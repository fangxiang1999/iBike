package com.yc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author wys
 * @date 2020/6/16 - 21:05
 */
@Service
public class LogServiceImpl implements LogService{
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(String log) {
        //相当于SQL 的 insert into log values(log)
        mongoTemplate.save(log,"logs");
    }
}
