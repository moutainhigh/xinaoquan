package cn.enn.dao;

import cn.enn.base.dto.CollectDTO;
import cn.enn.base.dto.RuleDTO;
import cn.enn.base.vo.CollectVO;
import cn.enn.entity.CollectEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * <p>
 * 收藏关联表 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Mapper
public interface CollectMapper extends BaseMapper<CollectEntity> {
    /**
     * 根据id查询收藏
     * @param ruleId
     * @return
     */
    @Select("SELECT * FROM `t_collect` c where c.rule_id=#{ruleId}")
    CollectEntity getByCollectId(Long ruleId);
    /**
     * 查询我的收藏
     * @param page
     * @param createId
     * @return
     */
    @Select("")
    Page<RuleDTO> getQuestionByPagenByPage(Page page, Long createId);

    /**
     * 条件查询我的收藏
     * @return
     */
    @Select("<script>"+
            "SELECT "+
            " c.collect_id,GROUP_CONCAT(DISTINCT r.rule_id  ) rule_id,GROUP_CONCAT(DISTINCT r.rule_name) rule_name,GROUP_CONCAT(DISTINCT r.rule_body) rule_body,GROUP_CONCAT(DISTINCT lc.label_name) label_name,GROUP_CONCAT(DISTINCT lc.label_type) label_type,GROUP_CONCAT(DISTINCT r.creator_real_name) creator_real_name"+
            " FROM t_collect c"+
            " LEFT JOIN t_rule r on c.rule_id=r.rule_id"+
            " LEFT JOIN t_rule_expression lc on r.rule_id=lc.rule_id"+
            " WHERE 1=1 "+
            "<if test='status != null'>"+
            " and r.status=#{status} "+
            "</if>"+
            "<if test='createId != null'>"+
            " and c.user_name = #{createId} "+
            "</if>"+
            "<if test='newKeyWord != null and newKeyWord !=\"\"'>"+
            " and CONCAT(r.rule_name,r.rule_body,lc.label_name,lc.label_type) REGEXP '${newKeyWord}'"+
            "</if>"+
            " GROUP BY c.collect_id  ORDER BY r.rule_id DESC"+
            "</script>")
    Page<CollectVO> searchCollect(Page page, String createId, Integer status, String newKeyWord);

    /**
     * 收藏查询返回信息
     * @param page 分页
     * @param createId 当前用户
     * @param status 我的、草稿、
     * @param newKeyWord 关键字
     * @return Page<RuleEntity>
     */
    Page<CollectDTO> searchCollect2(Page page, Long createId,Integer status,String newKeyWord);
    /**
     * 总数统计
     * @param createId
     * @param status
     * @param newKeyWord
     * @return
     */
    Long serachCollect2Count(Long createId,Integer status,String newKeyWord);

     /* 根据ruleId批量删除
     * @param ruleIds
     */
    void removeByRuleIds(List<Long> ruleIds);
}
