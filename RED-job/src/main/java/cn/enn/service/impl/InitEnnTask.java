package cn.enn.service.impl;

import cn.enn.ExpressConfig;
import cn.enn.common.utils.HttpUtil;
import cn.enn.dao.RuleMapper;
import cn.enn.entity.DataSourceFieldVO;
import cn.enn.entity.RuleExpressionEntity;
import cn.enn.service.InitConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.eternal.eventbus.EventResult;
import org.eternal.task.TaskEvent;
import org.eternal.task.handler.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: tiantao
 * @Date: 2019/11/21 10:01 AM
 * @Version 1.0
 */
@Component
@Slf4j
public class InitEnnTask extends TaskService.Impl implements InitConfig {
    @Autowired()
    private RuleMapper ruleMapper;
    /**
     * 准备数据，表达式和表达式计算变量
     */
    @Override
    public ExpressConfig initConfig(ExpressConfig expConfig) {
        //根据ruleId查询表达式
        List<RuleExpressionEntity> ruleExpressionEntityList =  this.ruleMapper.queryExpressByIdOfJob(expConfig.getRuleId());

        //根据ruleId查询数据源字段:本地数据
        List<DataSourceFieldVO> dataSourceFieldList = this.ruleMapper.queryDataSourceField(expConfig.getRuleId());
        //通过接口传过来的数据
        JSONObject jsonObject = JSON.parseObject(expConfig.getData());
        log.debug("接口字符串："+jsonObject.toString());
        expConfig.setJobNumber(UUID.randomUUID().toString());
        expConfig.setRuleId(expConfig.getRuleId());
        //设置规则表达式
        expConfig.setExpressList(ruleExpressionEntityList);
        //设置数据源
        expConfig.setDataSourceList(dataSourceFieldList);
        //设置接口数据
        expConfig.setJsonObject(jsonObject);
        Map map = new HashMap(16);
        if (ruleExpressionEntityList.size()==0){
            map.put("error","此规则表达式为空");
            expConfig.setErrData(map);
        }else if (dataSourceFieldList.size()==0){
            map.put("error","此规则数据源为空");
            expConfig.setErrData(map);
        }
        return expConfig;
    }

    String getRuleEntity(Long ruleId) {
        String ruleJson = HttpUtil.doGet("http://10.39.32.130:30124/RuleEngineDesigner/rule/queryRuleById?ruleId=" + ruleId);

        JSONObject jsonObject = JSON.parseObject(ruleJson);

        String content = jsonObject.getString("content");

        //RuleEntity ruleEntity = JSON.parseObject(content,RuleEntity.class);

        return content;
    }


    @Override
    public EventResult execute(TaskEvent event, EventResult result) {
        System.out.println("InitEnnTask:----------------1");
        ExpressConfig expressConfig = event.getContent();
        initConfig(expressConfig);
        //event.withContent(expressConfig);
        return result;
    }


}
