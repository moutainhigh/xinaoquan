package cn.enn.base.vo;

import cn.enn.entity.CollectiveEntity;
import cn.enn.entity.LabelObjectEntity;
import cn.enn.entity.RuleExpressionEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 标签规则业务对象
 * @author Administrator
 */
@Data
@ApiModel(value="标签规则业务对象", description="标签规则")
public class RuleVo {
    @ApiModelProperty(value = "规则id")
    private Long ruleId;
    @ApiModelProperty(value = "规则名称")
    private String ruleName;
    @ApiModelProperty(value = "规则主体id")
    private String ruleBodyId;
    @ApiModelProperty(value = "规则主体")
    private String ruleBody;

    @ApiModelProperty(value = "规则类型,1标签规则2数据转换规则")
    private Integer ruleType;

    @ApiModelProperty(value = "规则状态")
    private Integer status;
    private String userName;
    private String realName;
    private String createType;

    @ApiModelProperty(value = "标签规则")
    private List<RuleExpressionEntity> ruleExpressionEntities;

    @ApiModelProperty(value = "协同对象")
    private List<CollectiveEntity> collectiveEntityList;

    @ApiModelProperty(value = "规则对象")
    private List<LabelObjectEntity> labelObjectEntityList;

    @ApiModelProperty(value = "规则数据源")
    private List<RuleDatasourceVO> ruleDatasourceVOList;


}
