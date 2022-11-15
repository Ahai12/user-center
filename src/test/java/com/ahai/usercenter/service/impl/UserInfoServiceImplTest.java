package com.ahai.usercenter.service.impl;
import java.util.Date;

import com.ahai.usercenter.model.domain.UserInfo;
import com.ahai.usercenter.service.UserInfoService;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class UserInfoServiceImplTest {

    @Resource
    private UserInfoService userInfoService;

    @Test
    public void testAddUser(){
        //测试账户为空
        String userAccount = "";
        String userPassword = "aaaaaaaa";
        String checkPassword = "aaaaaaaa";
        Long result = userInfoService.userRegistry(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1L,result);
        //测试密码为空
        userAccount = "1231";
        userPassword = "";
        checkPassword = "aaaaaaaa";
        result = userInfoService.userRegistry(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1L,result);
        //测试校验密码为空
        userAccount = "1231";
        userPassword = "aaaaaaaa";
        checkPassword = "";
        result = userInfoService.userRegistry(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1L,result);
        //测试账户长度小于4
        userAccount = "123";
        userPassword = "aaaaaaaa";
        checkPassword = "aaaaaaaa";
        result = userInfoService.userRegistry(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1L,result);
        //测试密码长度小于8
        userAccount = "12313";
        userPassword = "aaaaaaa";
        checkPassword = "aaaaaaa";
        result = userInfoService.userRegistry(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1L,result);
        //测试校验密码长度小于8
        userAccount = "12313";
        userPassword = "aaaaaaa";
        checkPassword = "aaaaaa";
        result = userInfoService.userRegistry(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1L,result);
        //密码和校验密码一致性测试
        userAccount = "12313";
        userPassword = "aaaaaaaa";
        checkPassword = "aaaaaaab";
        result = userInfoService.userRegistry(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1L,result);
        //账户包含特殊字符测试
        userAccount = "123_ 11";
        userPassword = "aaaaaaaa";
        checkPassword = "aaaaaaaa";
        result = userInfoService.userRegistry(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1L,result);
        //账户名重名测试
        userAccount = "131231";
        userPassword = "aaaaaaaa";
        checkPassword = "aaaaaaaa";
        result = userInfoService.userRegistry(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1L,result);



    }

}