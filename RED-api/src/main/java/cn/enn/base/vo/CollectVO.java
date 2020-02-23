package cn.enn.base.vo;

import cn.enn.entity.RuleEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 收藏关联表
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Data
@TableName("t_collect")
@ApiModel(value="TCollect对象", description="收藏关联表")
public class CollectVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收藏id")
    @TableId(value = "collect_id", type = IdType.AUTO)
    private Integer collectId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "规则id")
    private String ruleId;

    @ApiModelProperty(value = "收藏或引用")
    private String collectType;

    @ApiModelProperty(value = "创建者id")
    private String creatorUserName;

    @ApiModelProperty(value = "创建者")
    private String creatorRealName;

    @ApiModelProperty(value = "规则名称")
    private String ruleName;

    @ApiModelProperty(value = "规则主体")
    private String ruleBody;

    @ApiModelProperty(value = "用户姓名")
    private String realName;

    @ApiModelProperty(value = "标签名称")
    private String labelName;

    @ApiModelProperty(value = "标签归属对象")
    private String labelType;

    @ApiModelProperty(value = "标签规则状态")
    private Integer status;

}
