package com.ahai.usercenter.service.impl;
import java.util.Date;

import com.ahai.usercenter.commons.ErrorCode;
import com.ahai.usercenter.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ahai.usercenter.model.domain.UserInfo;
import com.ahai.usercenter.service.UserInfoService;
import com.ahai.usercenter.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ahai.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author Ahai
* @description 针对表【user_info(用户信息)】的数据库操作Service实现
* @createDate 2022-11-07 11:48:44
*/
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

    /**
     * 加盐值
     */
    private static final String SALT = "AHAI";



    @Override
    public Long userRegistry(String userAccount, String userPassword, String checkPassword) {


        //账户，密码，校验密码不为空
        if (StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
           throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码为空");
        }

        if (userAccount.length() < 4 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账户过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");
        }
        if(!userPassword.equals(checkPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次密码不一致");
        }
        // 账户不能包含特殊字符
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(userAccount);
        boolean matchResult = matcher.find();
        if (matchResult){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户中不能包含特殊字符");
        }

        //密码加密

        String newPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //账户名不重复
        //为什么在这里才判断账户名重复？因为这个判断会查询数据库，降低程序的效率，所以放在后面执行
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",userAccount);
        int count = this.count(queryWrapper);
        if (count > 0){

            throw new BusinessException(ErrorCode.USERDuplication_ERROR,"该用户已注册");
        }

        //插入用户
        UserInfo userInfo = new UserInfo();
        userInfo.setUserAccount(userAccount);
        userInfo.setUserPassword(newPassword);
        boolean saveResult = this.save(userInfo);
        if (!saveResult){

            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户注册失败");
        }
        return userInfo.getId();
    }


    @Override
    public UserInfo userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.单点登录校验
        //账户，密码，不为空
        if (StringUtils.isAnyBlank(userAccount,userPassword)){

            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码为空");

        }

        if (userAccount.length() < 4 ){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账户过短");

        }
        if (userPassword.length() < 8){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短");

        }

        // 账户不能包含特殊字符
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(userAccount);
        boolean matchResult = matcher.find();
        if (matchResult){
            log.info("user login failed,please check your account");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户中含有特殊字符");

        }

        //密码加密后匹配
        //如果用户处于 已删除状态，要保证我们无法查询出来这条用户信息，需要使用mybatisplus的逻辑删除配置

        String newPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account",userAccount);
        queryWrapper.eq("user_password",newPassword);

        UserInfo userInfo = this.getOne(queryWrapper);
        //用户不存在
        if (userInfo == null){
            log.info("user login failed,please check your account");
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"用户不存在");

        }

        UserInfo safeUserInfo = getSafeUserInfo(userInfo);

        //3、获取用户登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE,safeUserInfo);

        return safeUserInfo;
    }

    /**
     * 用户注销
     * @param request
     * @return
     */

    @Override
    public int userLogout(HttpServletRequest request) {

        request.getSession().removeAttribute(USER_LOGIN_STATE);

        return 1;

    }

    @Override
    public UserInfo getSafeUserInfo(UserInfo userInfo){
        //用户信息脱敏
        if(userInfo == null){

            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户信息为空");

        }
        UserInfo safeUser = new UserInfo();
        safeUser.setId(userInfo.getId());
        safeUser.setUserName(userInfo.getUserName());
        safeUser.setUserAccount(userInfo.getUserAccount());
        safeUser.setAvatarUrl(userInfo.getAvatarUrl());
        safeUser.setGender(userInfo.getGender());
        safeUser.setPhone(userInfo.getPhone());
        safeUser.setEmail(userInfo.getEmail());
        safeUser.setUserStatus(userInfo.getUserStatus());
        safeUser.setCreateTime(userInfo.getCreateTime());
        safeUser.setUserRole(userInfo.getUserRole());

        return safeUser;
    }


}




