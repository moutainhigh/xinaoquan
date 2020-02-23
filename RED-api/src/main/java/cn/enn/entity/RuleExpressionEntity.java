package cn.enn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * <p>
 * 规则-详情
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Data
@TableName("t_rule_expression")
@ApiModel(value="标签规则对象", description="规则-详情")
public class RuleExpressionEntity  implements Serializable {


    private static final long serialVersionUID = -3305983279462822757L;
    @ApiModelProperty(value = "规则详情编号")
    @TableId(value = "rule_expression_id", type = IdType.AUTO)
    private Long ruleExpressionId;

    @ApiModelProperty(value = "规则编号")
    private Long ruleId;

    /*@ApiModelProperty(value = "标签条件编号")
    private Long conditionId;*/

    @ApiModelProperty(value = "标签英文名称")
    private String labelName;

    @ApiModelProperty(value = "标签中文名称")
    private String labelShowName;

    @ApiModelProperty(value = "规则描述")
    private String ruleDescription;

    @ApiModelProperty(value = "标签条件")
    private String labelCondition;

    @ApiModelProperty(value = "规则表达式")
    private String ruleExpression;

    @ApiModelProperty(value = "规则表达式，部件")
    private String ruleExpressionComponent;

    @ApiModelProperty(value = "规则表达式，部件类型")
    private String ruleExpressionComponentType;

    private String creatorUserName;


//    @ApiModelProperty(value = "序号")
//    private String serialNumber;

    @ApiModelProperty(value = "标签类别id")
    private Long labelTypeId;

    @ApiModelProperty(value = "标签类别")
    private String labelType;
    /**
     * 标签条件
     */
    private List<LabelConditionEntity> labelConditionEntities;

}
