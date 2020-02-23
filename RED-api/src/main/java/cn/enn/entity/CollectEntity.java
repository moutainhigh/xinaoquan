package cn.enn.entity;

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
public class CollectEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收藏id")
    @TableId(value = "collect_id", type = IdType.AUTO)
    private Integer collectId;


    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "规则id")
    private Long ruleId;

    @ApiModelProperty(value = "收藏或引用")
    private String collectType;


}
