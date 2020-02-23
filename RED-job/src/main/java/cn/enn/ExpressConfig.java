package cn.enn;

import cn.enn.entity.RuleExpressionEntity;
import com.alibaba.fastjson.JSONObject;
import com.ql.util.express.DefaultContext;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: tiantao
 * @Date: 2019/11/21 9:48 AM
 * @Version 1.0
 */
@Data
public class ExpressConfig {

    /**
     * 任务批次号
     */
    String jobNumber;

    /**
     * **************************************************
     */

    /**
     * 规则ID
     */
    Long ruleId;

    /**
     * **************************************************
     */

    /**
     * 员工编号
     */
    String empNo;

    /**
     * 标签名
     */
    String tagName;

    /**
     * 标签值
     */
    String tagValue;

    /**
     * **************************************************
     */

    /**
     * 数据源Id
     */
    Long datasourceId;

    List dataSourceList;

    /**
     * **************************************************
     */


    String data;

    /**
     * 规则表达式
     */
    List<RuleExpressionEntity> expressList;

    /**
     * 规则引擎上下文
     */
    DefaultContext<String, Object> context = new DefaultContext<String, Object>();

    /**
     * context传值
     */
    List labelName;
    List contextValue;
    /**
     * 计算结果
     */
    Map resMap;
    /**
     * 接口数据
     */
    JSONObject jsonObject;

    /**
     * 错误信息
     */
    Map errData;

}
