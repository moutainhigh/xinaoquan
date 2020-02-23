package cn.enn.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 创值对象
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Data
@TableName("t_rule_target")
@ApiModel(value="TRuleTarget对象", description="创值对象")
public class RuleTargetEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规则编号")
    private Integer ruleId;

    @ApiModelProperty(value = "角色编号")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private Integer roleName;

    @ApiModelProperty(value = "组织编号")
    private Integer orgCode;

    @ApiModelProperty(value = "组织名称")
    private String orgName;


}
