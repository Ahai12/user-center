package com.ahai.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author: ahai
 */

@Data
public class UserRegisterRequest implements Serializable {


    private static final long serialVersionUID = -761332024123161999L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;




}
