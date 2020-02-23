/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package cn.enn.service.impl;


import cn.enn.dao.UserDao;
import cn.enn.entity.UserEntity;
import cn.enn.form.LoginForm;
import cn.enn.service.TokenService;
import cn.enn.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
	@Autowired
	private TokenService tokenService;

	@Override
	public UserEntity queryByMobile(String mobile) {
		return baseMapper.selectOne(new QueryWrapper<UserEntity>().eq("mobile", mobile));
	}

	@Override
	public Map<String, Object> login(LoginForm form) {
//		UserEntity user = queryByMobile(form.getMobile());
//		Assert.isNull(user, "手机号或密码错误");
//
//		//密码错误
//		if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
//			throw new RRException("手机号或密码错误");
//		}
//
//		//获取登录token
//		TokenEntity tokenEntity = tokenService.createToken(user.getUserId());
//
		Map<String, Object> map = new HashMap<>(2);
//		map.put("token", tokenEntity.getToken());
//		map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());
//
		return map;
	}

}
