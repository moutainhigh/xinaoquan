package cn.enn.dao;

import cn.enn.entity.RuleExpressionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 规则-详情 Mapper 接口
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Mapper
public interface RuleExpressionMapper extends BaseMapper<RuleExpressionEntity> {

    @Select(
            "<script>"+
                    "SELECT label_type,label_name FROM `t_rule_expression` where 1=1 "+
                    "<if test='labelName != null'>"+"and label_name=#{labelName}"+"</if>"+
                    "</script>")
    @Results(value = {
            @Result(property = "labelType", column = "label_type"),
            @Result(property = "labelName", column = "label_name"),
    })
    List<RuleExpressionEntity> checkLabelName(Map map);

}
