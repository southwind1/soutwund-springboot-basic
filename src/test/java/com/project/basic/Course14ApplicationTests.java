package com.project.basic;

import com.alibaba.fastjson.JSON;
import com.project.basic.entity.User;
import com.project.basic.util.RedisService;
import  org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @ClassName Course14ApplicationTests
 * @Description TODO
 * @Author PC
 * @DATE 2022/8/8 15:33
 * @Version
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class Course14ApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(Course14ApplicationTests.class);

    @Autowired
    private RedisService redisService;

    @Test
    public void contextLoads() {
        //测试redis的string类型
        redisService.setString("weichat","程序员私房菜");
        logger.info("我的微信公众号为：{}", redisService.getString("weichat"));

        // 如果是个实体，我们可以使用json工具转成json字符串，
        User user = new User("CSDN", "123456");
        redisService.setString("userInfo", JSON.toJSONString(user));
        logger.info("用户信息：{}", redisService.getString("userInfo"));
    }
}