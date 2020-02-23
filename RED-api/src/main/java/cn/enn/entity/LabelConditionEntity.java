package cn.enn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 标签规则条件
 * @author Administrator
 * @since 2019-11-04
 */
@Data
@TableName("t_rule_condition")
@ApiModel(value="标签条件对象", description="标签规则条件实体")
public class LabelConditionEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标签规则条件id")
    @TableId(value = "condition_id", type = IdType.AUTO)
    private Long conditionId;
    private Long ruleExpressionId;
    private Long ruleId;
    /**
     * 数据源字段id
     */
    private String datasourceFielsId;

    /**
     * 数据源字段名称
     */
    private String datasourceFieldName;
    /**
     * 条件显示名称
     */
    private String datasourceFieldShowName;
    /**
     * 回答列表，答案选项(保存选项的key，多选以逗号隔开)
     */
    private String answer;

    /**
     * 回答列表，答案选项(保存选项的value，多选以逗号隔开)
     */
    private String showAnswer;

}
