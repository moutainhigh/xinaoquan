package cn.enn.service.impl;

import cn.enn.ExpressConfig;
import cn.enn.dao.RuleMapper;
import cn.enn.entity.DataSourceFieldVO;
import cn.enn.entity.RuleExpressionEntity;
import cn.enn.service.PreparDataService;
import cn.enn.service.TransformService;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.eternal.eventbus.EventResult;
import org.eternal.task.TaskEvent;
import org.eternal.task.handler.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @Author: tiantao
 * @Date: 2019/11/28 11:03 AM
 * @Version 1.0
 */
@Component
@Slf4j
public class PreparDataServiceImpl extends TaskService.Impl implements PreparDataService {

    @Autowired()
    private RuleMapper ruleMapper;
    @Autowired
    private TransformService transformService;

    @Override
    protected EventResult execute(TaskEvent taskEvent, EventResult eventResult) {

        System.out.println("PreparDataServiceImpl:------------------2");
        ExpressConfig config = taskEvent.getContent();
        prepar(config);
        return eventResult;
    }


    @Override
    public ExpressConfig prepar(ExpressConfig expressConfig){
        Map errorMap = new HashMap();
        List<Map<String,String>> contextList = new ArrayList<>();
        try {
            List<RuleExpressionEntity> listRuleExp = expressConfig.getExpressList();
            List ruleExpressionList = new ArrayList();
            List labelNameList = new ArrayList();
            for(RuleExpressionEntity ruleExpressionEntity:listRuleExp){
                String labelName = ruleExpressionEntity.getLabelName();
                labelNameList.add(labelName);
                //表达式
                String ruleExpression = ruleExpressionEntity.getRuleExpression();
                ruleExpressionList.add(ruleExpression);
            }
            //设置标签名称
            expressConfig.setLabelName(labelNameList);
            //设置表达式
            expressConfig.setExpressList(ruleExpressionList);
            //根据ruleId查询数据源字段
            List<DataSourceFieldVO> dataSourceFieldVos = expressConfig.getDataSourceList();
            //接口数据
            JSONObject jsonObject = expressConfig.getJsonObject();
            for (DataSourceFieldVO dataSourceFieldVO:dataSourceFieldVos){
                String fieldName = dataSourceFieldVO.getFieldName();
                String dataSourceType = dataSourceFieldVO.getDataSourceType();
                String tableName = dataSourceFieldVO.getDatasourceName();
                String relationField = dataSourceFieldVO.getRelationField();
                String relationShip = dataSourceFieldVO.getRelationship();
                if(!"1".equals(dataSourceType)){
                    Object value = jsonObject.get(fieldName);
                    if(null == value){log.debug("json输入项:"+fieldName+"不存在；"+tableName);
                        String errInfo = "json输入项:"+fieldName+"不存在";
                        errorMap.put("error",errInfo);
                        expressConfig.setErrData(errorMap);
                    }
                    expressConfig.getJsonObject();
                    Map map = transForm(tableName,fieldName,value.toString());
                    contextList.add(map);

                }else{
                    if (null == relationField){
                        log.debug("relationField为空");
                        errorMap.put("error","relationField为空");
                        expressConfig.setErrData(errorMap);
                    }
                    log.debug("数据关联关系："+relationField);
                    //获取关联字段
                    String empNo = getEmpNo(relationShip);
                    //取得关联字段的值
                    Object empValue = jsonObject.get(empNo);
                    if (null == empValue){
                        log.debug(empNo+"的值为空");
                        errorMap.put("error",empNo+"的值为空");
                        expressConfig.setErrData(errorMap);
                    }
                    List<Object> fieldNameValue = this.ruleMapper.queryDadaSourceValue(relationField,tableName,fieldName,empValue.toString());
                    if(fieldNameValue.size()>0){
                        for(Object num:fieldNameValue){
                            Map map = transForm(tableName,fieldName,num.toString());
                            contextList.add(map);
                        }

                    }else{
                        log.debug(tableName+"的"+fieldName+"字段无值");
                        errorMap.put("error",tableName+"的"+fieldName+"字段无值");
                        expressConfig.setErrData(errorMap);
                    }
                }

            }
            //设置context
            expressConfig.setContextValue(contextList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return expressConfig;

    }

    /**
     * 获取关联字段
     * @param relationShip 关联关系
     * @return 关联字段
     */
    private String getEmpNo(String relationShip){

        int index = relationShip.indexOf(".");
        int index2 = relationShip.indexOf("=");
        return relationShip.substring(index+1,index2);
    }

    /**
     * 解析jsonObject
     * @param jsonObject 接口数据
     * @param fieldName 数据源
     * @return 数据源对应的值
     */
    private Object parsJson(JsonObject jsonObject,String fieldName){

        return  jsonObject.get(fieldName);
    }

    /**
     * 转换类型
     * @param tableName 表明
     * @param fieldName 字段名
     * @param value 字段值
     * @return 转换结果
     */
    private Map transForm(String tableName,String fieldName,String value){
        Map map = new HashMap(16);
            boolean flag = isIntOrDouble(value);
            if (flag) {
                BigDecimal decimal = new BigDecimal(value);
                log.debug("类型转换结果：" + decimal);
                map.put(tableName + "#" + fieldName, decimal);
            } else {
                map.put(tableName + "#" + fieldName, value);
            }
        return map;
    }

    /**
     * 判断字符串是否为数字
     * @param str 字符串
     * @return boolean
     */
    private boolean isIntOrDouble(String str){
        if (null == str || "".equals(str)){
            return false;
        }else{
            return Pattern.matches("-?[0-9]*(\\.?)[0-9]*",str);
        }
    }



}
