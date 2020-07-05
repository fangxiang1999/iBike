package com.yc.service;

import com.mongodb.client.result.UpdateResult;
import com.yc.bean.User;
import com.yc.utils.SmsUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * @author wys
 * @date 2020/6/21 - 11:37
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
    private Logger log= LogManager.getLogger(UserServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MongoTemplate mongoTemplate;





    @Override
    public boolean identity(User user) {
        //TODO 接入接口验证身份证是否合理
        int status=3;
        UpdateResult result=mongoTemplate.updateFirst(
                new Query(Criteria.where("phoneNum").is(user.getPhoneNum())),
                new Update().set("status",status)
                            .set("name",user.getName())
                            .set("idNum",user.getIdNum()),User.class);
        if(result.getModifiedCount()==1){
            return  true;
        }else{
        return false;
        }
    }

    @Override
    public boolean deposit(User user) {
        int status=2;   //状态变2
        int money=299;  //押金数
        UpdateResult result=mongoTemplate.updateFirst(
                new Query( Criteria.where("phoneNum").is(user.getPhoneNum()) ),
                new Update().set("status",status).set("deposit",money),User.class);
        if(result.getModifiedCount()==1){
            return true;
        }else{
            return false;
        }

    }

    /**
     * 验证验证码并注册用户
     * @param user
     * @return
     */
    @Override
    public boolean verify(User user) {
        boolean flag=false;
        String phoneNum=user.getPhoneNum();
        String verifyCode=user.getVerifyCode();
        //从redis中取验证码
        String code=stringRedisTemplate.opsForValue().get(phoneNum);
        System.out.println(user);
        if(verifyCode!=null&&verifyCode.equalsIgnoreCase(code)){
            //验证成功后，将用户信息保存到mongo
            mongoTemplate.insert(user);
            flag=true;
        }
        return flag;
    }

    @Override
    public void genVerifyCode(String nationCode, String phoneNum) throws Exception {
        //短信接口的appkey


        //生成验证码
        String code=(int)((Math.random()*9+1)*1000)+"";
        log.info("生成的验证码为:"+code);
//        SmsUtils.sendSms(code,new String[]{nationCode+phoneNum});
//        redisTemplate.

        //将数据保存到redis，redis的key是手机号，value是验证码，有效时间120s
        stringRedisTemplate.opsForValue().set(phoneNum,code,120, TimeUnit.SECONDS);


    }
}
