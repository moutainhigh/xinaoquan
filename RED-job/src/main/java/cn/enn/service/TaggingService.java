package cn.enn.service;

import cn.enn.ExpressConfig;

/**
 * 调用规则引擎打标签
 *
 * @Author: tiantao
 * @Date: 2019/11/21 10:08 AM
 * @Version 1.0
 */
public interface TaggingService {
    /**
     * 解析规则
     * @param expressConfig 表达式配置
     * @throws Exception 解析异常
     * @return ExpressConfig
     */
    ExpressConfig execute(ExpressConfig expressConfig) throws Exception ;

}
