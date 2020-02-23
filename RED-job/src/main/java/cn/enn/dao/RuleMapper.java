package cn.enn.dao;


import cn.enn.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface RuleMapper {
    /**
     * 查询规则
     * @param ruleId 规则id
     * @return List<RuleEntity>
     */
    @Results(id = "ruleMap",value = {
            @Result(column = "rule_id",property = "ruleId",id = true),
            @Result(column = "rule_name",property = "ruleName"),
            @Result(column = "creator_user_name",property = "creatorUserName"),
            @Result(column = "rule_id",property = "ruleExpressionEntity",many = @Many(select = "cn.enn.dao.RuleMapper.queryExpressByIdOfJob")),
            @Result(column = "rule_id",property = "datasourceFieldEntity",many = @Many(select = "cn.enn.dao.RuleMapper.queryDatasourceFieldByRuleId"))
    })
    @Select("SELECT rule_id,rule_name,creator_user_name FROM t_rule where rule_id=#{ruleId}")
    List<RuleEntity> queryRuleOfJob(@Param("ruleId")Long ruleId);

    /**
     * 查询表达式
     * @param ruleId 规则id
     * @return rule_expression
     */
    @Results(id = "expressionMap",value = {
            @Result(column = "rule_expression_id",property = "ruleExpressionId",id = true),
            @Result(column = "rule_id",property = "ruleId"),
            @Result(column = "label_name",property = "labelName"),
            @Result(column = "label_type",property = "labelType"),
            @Result(column = "rule_expression",property = "ruleExpression")

    })
    @Select("SELECT rule_expression_id,rule_id,label_name,label_type,rule_expression FROM t_rule_expression where rule_id=#{ruleId}")
    List<RuleExpressionEntity> queryExpressByIdOfJob(@Param("ruleId")Long ruleId);

    /**
     * 查询field_name
     * @param ruleId 规则id
     * @return field_name
     */
    @Select("SELECT d.datasource_field_id,d.datasource_id,d.field_name FROM `t_datasource_field` d WHERE datasource_id in(SELECT datasource_id FROM t_rule_datasource td where td.rule_id=#{ruleId})")
    List<DatasourceFieldEntity> queryDatasourceFieldByRuleId(@Param("ruleId")Long ruleId);

    /**
     * 查询ContextValue
     * @param fieldName 表名
     * @param creatorUserName 人
     * @return List<DynamicTable>
     */
    @Results(id = "DynamicTableMap2",value = {
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "empNo",property = "empNo"),
            @Result(column = "sellingPrice",property = "sellingPrice")
    })
    @Select("select t.empNo,t.sellingPrice from ${fieldName} t where t.empNo=#{creatorUserName}")
    List<DynamicTable>  queryDatasourceFieldValue(String fieldName, String creatorUserName);

    /**
     * 根据ruleId查询表名
     * @param ruleId 规则id
     * @return 表明
     */
    @Select("SELECT datasource_name FROM t_rule_datasource td where td.rule_id=#{ruleId}")
    List queryTableName(long ruleId);
    /**
     * 根据ruleId查询表字段
     */


    /**
     * 查询ContextValue
     * @param tableName 表明
     * @param filedName 字段
     * @param empNo 员工
     * @return  ContextValue
     */
    @Select("select ${filedName} from ${tableName} where ${relationField}=${empNo}")
    List<Object>  queryDadaSourceValue(String relationField,String tableName,String filedName,String empNo);

    /**
     * 根据规则id查询数据源字段
     * @param ruleId 规则id
     * @return 数据源字段
     */
    @Select("SELECT d.field_name,f.datasource_name\n" +
            "FROM `t_datasource_field` d LEFT JOIN t_datasource f on d.datasource_id=f.datasource_id\n" +
            "WHERE d.datasource_id IN (SELECT td.datasource_id FROM t_rule_datasource td \n" +
            "WHERE td.rule_id = #{ruleId})")
    List<DataSourceFieldVO> queryField(long ruleId);

    @Select("SELECT DISTINCT df.datasource_id,df.field_name,df.field_show_name,rd.datasource_name,rd.relationship,rd.relationfield,d.datasource_type\n" +
            "FROM t_datasource_field df LEFT JOIN t_rule_datasource rd  on rd.datasource_id=df.datasource_id\n" +
            "LEFT JOIN t_datasource d on df.datasource_id=d.datasource_id\n"+
            "WHERE rd.rule_id=#{ruleId}")
    List<DataSourceFieldVO> queryDataSourceField(long ruleId);
    /**
     * 判断数据表是否存在
     * @param tableName 标名
     * @return String
     */
    @Select("select TABLE_NAME from INFORMATION_SCHEMA.TABLES where  TABLE_NAME=#{tableName} ")
    String isTableName(String tableName);

    /**
     * 根据topic和appId 查询RuleId
     * @param topic 主题
     * @param appId 应用
     * @return RuleId
     */
    @Select("SELECT tc.RuleId FROM t_topic_correspondence tc WHERE tc.Topic=#{topic}")
    String queryRuleIdByTopic(String topic,String appId);
}