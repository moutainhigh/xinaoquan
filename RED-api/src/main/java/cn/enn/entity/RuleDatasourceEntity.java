package cn.enn.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 规则数据源
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Data
@TableName("t_rule_datasource")
@ApiModel(value="TRuleDatasource对象", description="规则数据源")
public class RuleDatasourceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long ruleDatasourceId;

    @ApiModelProperty(value = "规则编码")
    private Long ruleId;

    @ApiModelProperty(value = "数据源编号")
    private Long datasourceId;

    @ApiModelProperty(value = "表英文显示名称")
    private String datasourceName;

    @ApiModelProperty(value = "表中文显示名称")
    private String datasourceShowName;

    @ApiModelProperty(value = "逻辑主或逻辑从")
    private String logic;

    @ApiModelProperty(value = "关系表达式，英文")
    private String relationship;

    @ApiModelProperty(value = "关系表达式，中文")
    private String relationshipShow;

    //保存从表的关联字段，（国树龄）
    private String relationfield;

    //关联字段连接符
    private String symbol;

}
