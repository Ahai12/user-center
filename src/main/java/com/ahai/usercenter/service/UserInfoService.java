package com.ahai.usercenter.service;

import com.ahai.usercenter.model.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author Ahai
* @description 针对表【user_info(用户信息)】的数据库操作Service
* @createDate 2022-11-07 11:48:44
*/
public interface UserInfoService extends IService<UserInfo> {


    /**
     *     //用户注册逻辑，成功则返回新增用户的id
     * @param userAccount 账户
     * @param userPassword 密码
     * @param checkPassword 校验密码
     * @return 用户id
     */
    Long userRegistry(String userAccount,String userPassword,String checkPassword);

    /**
     * 用户登录
     * @param userAccount 账户
     * @param userPassword 密码
     * @param request
     * @return 返回脱敏后的用户信息
     */
    UserInfo userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户信息脱敏
     * @param userInfo 用户所有信息
     * @return 脱敏后的信息
     */
    UserInfo getSafeUserInfo(UserInfo userInfo);

    /**
     * 用户注销
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
}
