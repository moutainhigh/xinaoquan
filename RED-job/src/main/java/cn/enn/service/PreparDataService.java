package cn.enn.service;

import cn.enn.ExpressConfig;

/**
 * 准备数据
 *
 * @Author: tiantao
 * @Date: 2019/11/21 10:13 AM
 * @Version 1.0
 */
public interface PreparDataService {

    ExpressConfig prepar(ExpressConfig expressConfig)throws Exception;
}
