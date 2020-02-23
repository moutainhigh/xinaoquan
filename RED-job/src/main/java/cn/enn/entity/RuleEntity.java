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
 * 标签规则
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Data
@TableName("t_rule")
@ApiModel(value="Rule对象", description="标签规则")
public class RuleEntity  implements Serializable {

    private static final long serialVersionUID = 3177555918906827166L;
    @ApiModelProperty(value = "规则编码")
    @TableId(value = "rule_id", type = IdType.AUTO)
    private Long ruleId;

    @ApiModelProperty(value = "规则名称")
    private String ruleName;

    @ApiModelProperty(value = "规则主体id")
    private String ruleBodyId;

    @ApiModelProperty(value = "规则主体")
    private String ruleBody;

    private Integer ruleType;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "创建者id")
    private String creatorUserName;

    @ApiModelProperty(value = "创建者")
    private String creatorRealName;

    @ApiModelProperty(value = "创建时间")
    private String creatTime;

    @ApiModelProperty(value = "最后修改时间")
    private String updateTime;

    private String createType;

    private String updateUserName;
    /**
     * 规则-详情
     */
    private List<RuleExpressionEntity> ruleExpressionEntity;

    /**
     * 数据源Context
     */
    private List<DatasourceFieldEntity> datasourceFieldEntity;



}
