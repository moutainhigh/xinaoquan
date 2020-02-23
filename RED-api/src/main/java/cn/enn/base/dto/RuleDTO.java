package cn.enn.base.dto;

import cn.enn.entity.CollectiveEntity;
import cn.enn.entity.LabelConditionEntity;
import cn.enn.entity.LabelObjectEntity;
import cn.enn.entity.RuleExpressionEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * 标签规则数据传输对象
 * @author Administrator
 */
@Data
@ApiModel(value="RuleDTO对象", description="标签规则数据传输对象")
public class RuleDTO implements Serializable {

    private static final long serialVersionUID = -6546814380090006086L;
    private Long ruleId;

    @ApiModelProperty(value = "创建者id")
    private String creatorUserName;

    @ApiModelProperty(value = "创建者")
    private String creatorRealName;

    @ApiModelProperty(value = "规则名称")
    private String ruleName;

    @ApiModelProperty(value = "规则主体id")
    private String ruleBodyId;

    @ApiModelProperty(value = "规则主体")
    private String ruleBody;

    @ApiModelProperty(value = "用户编号")
    private String userName;

    @ApiModelProperty(value = "用户姓名")
    private String realName;

    @ApiModelProperty(value = "标签名称")
    private String labelName;//查询收藏时使用

    @ApiModelProperty(value = "标签归属对象")
    private String labelType;//查询收藏时使用

    @ApiModelProperty(value = "收藏id")
    private Long collectId;//收藏id,查询收藏时使用

    //@ApiModelProperty(value = "标签对象")
    //private String labelObject;//说明：在条件里保存

    //@ApiModelProperty(value = "标签条件")
    //private String labelCondition;//说明：列表里保存

    //@ApiModelProperty(value = "规则表达式")
    //private String ruleExpression;//说明：列表里保存

    //@ApiModelProperty(value = "规则描述")
    //private String ruleDescription;//说明：在表达式列表里保存

    //@ApiModelProperty(value = "搜索关键字")
    //private String searchKeyWord;//说明：好象不用

    private Integer status;
    /**
     * 协同对象
     */
    private List<CollectiveEntity> collectiveEntityList;
    /**
     * 规则详情
     */
    private List<RuleExpressionEntity> ruleExpressionEntityList;
    /**
     * 标签对象
     */
    private List<LabelObjectEntity> labelObjectEntityList;
    /**
     * 标签条件
     */
    private List<LabelConditionEntity> labelConditionEntityList;


}
