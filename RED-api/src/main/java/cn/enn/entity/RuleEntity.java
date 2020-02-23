package cn.enn.entity;

import cn.enn.base.vo.RuleDatasourceVO;
import cn.enn.base.vo.RuleDatasourceVoOut;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
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

    @ApiModelProperty(value = "规则类型，1标签规则2数据转换规则")
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
     * 协同
     */
    private List<CollectiveEntity> collectiveEntityList;
    /**
     * 标签对象
     */
    private List<LabelObjectEntity> labelObjectEntities;
    /**
     * 规则数据源
     */
    private List<RuleDatasourceVoOut> ruleDatasourceVOList;





}
