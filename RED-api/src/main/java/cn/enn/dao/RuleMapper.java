package cn.enn.dao;

import cn.enn.base.dto.RuleDTO;
import cn.enn.entity.LabelConditionEntity;
import cn.enn.entity.RuleEntity;
import cn.enn.entity.RuleExpressionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 标签规则 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Mapper
public interface RuleMapper extends BaseMapper<RuleEntity> {
    List<RuleEntity> queryRuleViewListOfJob();
    /**
     * 搜索方法
     * @param page 分页参数
     * @param newKeyWord 关键词
     * @param status 状态
     * @param userId 用户id
     * @return Page<RuleEntity>
     */
    Page<RuleEntity> queryRuleView(Page page,String newKeyWord,int ruleType,int status,String userId);
    /**
     * 查询总数
     * @param newKeyWord 关键词
     * @param status 状态
     * @param userId 用户id
     * @return queryRuleViewCount
     */
    Long queryRuleViewCount(String newKeyWord,int status,String userId);
    /**
     * 级联查询规则和规则详情
     * @param page 分页
     * @param createId 当前用户
     * @param status 我的、草稿、
     * @param newKeyWord 关键字
     * @return Page<RuleEntity>
     */
    @Select(
            "<script>"+
                    "SELECT * FROM `t_rule`  where 1=1 and status !=2"+
                    "<if test='status ==1 '>"+" and status=1"+"</if>"+
                    "<if test='status ==0 '>"+" and status=0"+"</if>"+
                    "<if test='createId != null'>"+"and creator_user_name=#{createId}"+"</if>"+
                    "<if test='null != newKeyWord'>" +
                    " and concat(" +
                    "IFNULL( rule_name, '' )," +
                    "IFNULL( rule_body, '' )," +
                    "IFNULL( label_object, '')," +
                    "IFNULL( creator_real_name,'')"+
                    ")"+
                    "REGEXP '${newKeyWord}' " +
                    "</if>"+
                    " order by rule_id desc"+
                    "</script>")
    @Results(value = {
            @Result(property = "ruleId", column = "rule_id"),
            @Result(property = "ruleName", column = "rule_name"),
            @Result(property = "ruleBodyId", column = "rule_body_id"),
            @Result(property = "ruleBody", column = "rule_body"),
            @Result(property = "createType", column = "create_type"),
            @Result(property = "labelConditionEntityList", column = "rule_id",
                    many = @Many(select = "cn.enn.dao.RuleMapper.findConditionByRuleId")),
    })
    Page<RuleEntity> getAllOfRuleAndExpression(Page page,Long createId,Integer status,String newKeyWord);



    /**
     * 根据标签规则id查询标签条件
     * @param ruleId
     * @return
     */
    @Select("<script>"+
            "SELECT * FROM `t_label_condition` " +
            "WHERE  rule_id = #{ruleId}" +
            "</script>")
    @Results(@Result(property = "ruleId",column = "rule_id"))
    List<LabelConditionEntity> findConditionByRuleId(Long ruleId);
    /**
     * 根据ruleId 查询规则详情
     * @param ruleId 规则ID
     * @return  List<RuleExpressionEntity>
     */
    @Mapper
    @Select("<script>"+
            "SELECT * FROM `t_rule_expression` " +
            "WHERE  rule_id = #{ruleId}" +
    "</script>")
    @Results(@Result(property = "ruleId",column = "rule_id"))
    List<RuleExpressionEntity> findExpressionByRuleId(Long ruleId);
    /**
     * 查询我的规则/我的草稿
     * @param page 分页
     * @param createId 创建人
     * @param status 草稿状态
     * @return Page<RuleDTO>
     */
    Page<RuleDTO> getQuestionByPage(Page page, Long createId,int status);

    /**
     * 查询我的收藏
     * @param page 分页
     * @param createId 创建人
     * @return Page<RuleDTO>
     */
    /*@Select("SELECT\n" +
            "u.rule_id,u.rule_name,u.rule_body,GROUP_CONCAT(e.label_name)AS label_name,GROUP_CONCAT(e.label_type)as label_type\n" +
            "FROM t_collect c \n" +
            "LEFT JOIN t_rule u on c.rule_id=u.rule_id \n" +
            "LEFT JOIN t_rule_expression e on c.rule_id=e.rule_id\n" +
            "where c.user_name=#{createId} and u.status<>2\n" +
            "GROUP BY c.rule_id")*/
    @Select("SELECT\n"+
            "c.collect_id,r.rule_id,r.rule_name,r.rule_body_id,r.rule_body,r.creator_real_name,GROUP_CONCAT(lc.label_name) labelName,GROUP_CONCAT(lc.label_type) labelType,GROUP_CONCAT(re.rule_expression) ruleExpression\n"+
            "FROM t_collect c\n"+
            "LEFT JOIN t_rule r on c.rule_id=r.rule_id\n"+
            "LEFT JOIN t_label_condition lc on r.rule_id=lc.rule_id\n"+
            "LEFT JOIN t_rule_expression re ON lc.rule_id=re.rule_id\n"+
            "WHERE c.user_name=#{createId} AND r.status<>2\n"+
            "GROUP BY c.collect_id,r.rule_id,lc.condition_id\n"+
            "ORDER BY c.collect_id DESC"
    )
    Page<RuleDTO> getCollectByPage(Page page, Long createId);

    /**
     * 关键字搜索
     * @param newKeyWord 关键字
     * @param page 分页
     * @return Page<RuleDTO>
     */
    Page<RuleDTO> searchKeyWord(Page page,String newKeyWord,String userId);


    @Select("<script>" +
           "select r.rule_id,r.rule_name,r.rule_body_id,r.rule_body,e.rule_expression_id,e.label_name,e.label_type\n" +
            " <if test=\"null == createId\">\n" +
            "  ,r.creator_real_name\n" +
            "  </if>\n" +
            "  from t_rule r left join t_rule_expression e on r.rule_id=e.rule_id\n" +
            "  WHERE r.`status`!= 2\n" +
            "  <if test=\"null != createId\">\n" +
            "    AND r.`creator_user_name` = ${createId}\n" +
            "  </if>\n" +
            "  <if test=\"status == 0\">\n" +
            "      and r.`status` = 0\n" +
            "  </if>\n" +
            "  <if test=\"status == 1\">\n" +
            "      AND r.`status` = 1\n" +
            "  </if>"+

            "</script>")
    Page<RuleDTO> queryRuleAndExpression(Page page, Long createId,int status);

    RuleEntity specificExpression(Long ruleId);

    /**
     * 查询规则
     * @param ruleId 规则id
     * @return RuleEntity
     */
    RuleEntity queryRuleById(Long ruleId);




}
