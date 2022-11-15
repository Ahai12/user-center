package com.ahai.usercenter.controller;


import com.ahai.usercenter.commons.BaseResponse;
import com.ahai.usercenter.commons.ErrorCode;
import com.ahai.usercenter.commons.ResultUtils;
import com.ahai.usercenter.exception.BusinessException;
import com.ahai.usercenter.model.domain.UserInfo;
import com.ahai.usercenter.model.request.UserRegisterRequest;
import com.ahai.usercenter.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ahai.usercenter.constant.UserConstant.ROOT_ROLE;
import static com.ahai.usercenter.constant.UserConstant.USER_LOGIN_STATE;


@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegistry(@RequestBody UserRegisterRequest registerRequest) {

        if (registerRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = registerRequest.getUserAccount();
        String userPassword = registerRequest.getUserPassword();
        String checkPassword = registerRequest.getCheckPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {

            throw new BusinessException(ErrorCode.PARAMS_ERROR,"输入错误，请检查用户名和密码");
        }

        Long userId = userInfoService.userRegistry(userAccount, userPassword, checkPassword);
        return ResultUtils.success(userId);
    }

    @PostMapping("/login")
    public BaseResponse<UserInfo> userLogin(@RequestBody UserRegisterRequest registerRequest, HttpServletRequest request) {

        if (registerRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = registerRequest.getUserAccount();
        String userPassword = registerRequest.getUserPassword();


        if (StringUtils.isAnyBlank(userAccount, userPassword)) {

            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
//            throw new BusinessException(ErrorCode.PARAMS_ERROR,"输入错误，请检查用户名和密码");
        }

        UserInfo userInfo = userInfoService.userLogin(userAccount, userPassword, request);

        return ResultUtils.success(userInfo);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {

        if(request == null){

            throw new BusinessException(ErrorCode.PARAMS_ERROR);

        }

        int result = userInfoService.userLogout(request);

        return ResultUtils.success(result);
    }


    @GetMapping("/search")
    public BaseResponse<List<UserInfo>> userSearch(String userName, HttpServletRequest request) {

        //判断是否登录
        // 鉴权
        if (!isRoot(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("user_name", userName);
        }
        List<UserInfo> userInfos = userInfoService.list(queryWrapper);
        //用户脱敏
        List<UserInfo> userInfoList = userInfos.stream().map(userInfo -> userInfoService.getSafeUserInfo(userInfo)).collect(Collectors.toList());
        return ResultUtils.success(userInfoList);

    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> userDelete(@RequestBody Long userId, HttpServletRequest request) {

        if (userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请检查id是否正确");
        }
        //判断是否登录
        //鉴权
        if (!isRoot(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH,"该用户没有操作权限");
        }

        boolean result = userInfoService.removeById(userId);
        return ResultUtils.success(result);
    }


    @GetMapping("/current")
    public BaseResponse<UserInfo> getCurrentUserInfo(HttpServletRequest request){
//        UserInfo currentUser =(UserInfo) request.getSession().getAttribute(USER_LOGIN_STATE);
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserInfo currentUser = (UserInfo) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        Long currentId = currentUser.getId();
        UserInfo user = userInfoService.getById(currentId);
        UserInfo safeUserInfo = userInfoService.getSafeUserInfo(user);
        return ResultUtils.success(safeUserInfo);
    }


    // 鉴权函数
    public boolean isRoot(HttpServletRequest request) {

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(USER_LOGIN_STATE);
        return userInfo != null && userInfo.getUserRole() == ROOT_ROLE;
    }

    //判断是否登录
//    public boolean isLogin(HttpServletRequest request) {
//        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
//            return false;
//        }
//        return true;
//    }


}
