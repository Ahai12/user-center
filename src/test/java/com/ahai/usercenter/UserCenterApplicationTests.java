package com.ahai.usercenter;
import java.util.Date;


import com.ahai.usercenter.model.domain.UserInfo;
import com.ahai.usercenter.service.UserInfoService;
import com.ahai.usercenter.service.impl.UserInfoServiceImpl;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class UserCenterApplicationTests {


    @Resource
    private UserInfoService userInfoService;

    @Test
    void contextLoads() {

        UserInfo userInfo = new UserInfo();

        userInfo.setUserName("asd");
        userInfo.setUserAccount("131231");
        userInfo.setUserPassword("asda");
        userInfo.setPhone("");
        userInfo.setEmail("");
        userInfo.setUserStatus(0);
        userInfo.setCreateTime(new Date());
        userInfo.setUpdateTime(new Date());
        userInfo.setIsDelete(0);
        boolean result = userInfoService.save(userInfo);
        System.out.println(result);


    }

}
