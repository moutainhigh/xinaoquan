/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package cn.enn.controller;


import cn.enn.annotation.Login;
import cn.enn.common.utils.Constant;
import cn.enn.common.utils.GlobalSession;
import cn.enn.common.utils.HttpUtil;
import cn.enn.common.utils.R;
import cn.enn.form.LoginForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

/**
 * 登录接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/api")
@Api(tags="登录接口")
@Slf4j
public class LoginController {
    private final Base64.Encoder encoder = Base64.getEncoder();

    /**
     * 系统对接-ad账号
     * @param userName 用户名称
     * @param passWord 用户密码
     * @return ticket 令牌
     */
    @ApiOperation("系统对接-ad账号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName",required = true,paramType ="query",dataType="string",defaultValue ="tiantaoc", value = "用户名称"),
            @ApiImplicitParam(name = "passWord",required = true,paramType ="query",dataType="string",defaultValue ="1a@12345",value = "用户密码")
    })
    @PostMapping("account")
    @Deprecated
    public String account(@RequestParam(value = "userName") String userName, @RequestParam(value = "passWord") String passWord,HttpServletRequest request){

        final byte[] textByte = passWord.getBytes();
        final String encodedText = encoder.encodeToString(textByte);
        String jsonStr = "{\"password\":\""+encodedText+"\",\"userName\":\""+userName+"\"}";
        String res = HttpUtil.doPost(Constant.AUTH,jsonStr, Constant.APPSECRET);
        request.getSession().setAttribute(Constant.USER_SESSION_KEY,userName);
        return res;
    }

    @PostMapping("login")
    @ApiOperation("登录")
    public R login(@RequestBody LoginForm form){
        GlobalSession.setSessionAttribute("userForm",form);
        return R.ok();
    }

    @Login
    @PostMapping("logout")
    @ApiOperation("退出")
    public R logout(String userName){
        GlobalSession.removeSessionAttribute("userForm");
        return R.ok();
    }




}
