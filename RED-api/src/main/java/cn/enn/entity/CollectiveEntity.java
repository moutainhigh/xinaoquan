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
 * 协同伙伴关联表
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Data
@TableName("t_collective")
@ApiModel(value="协同对象", description="协同伙伴关联表")
public class CollectiveEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "协同人编号")
    @TableId(value = "collective_id", type = IdType.AUTO)
    private Long collectiveId;

    @ApiModelProperty(value = "用户编号")
    private String userName;

    @ApiModelProperty(value = "规则编号")
    private Long ruleId;

    @ApiModelProperty(value = "用户姓名")
    private String realName;

    public CollectiveEntity() {
    }

    public CollectiveEntity(Long collectiveId, String userName, Long ruleId, String realName) {
        this.collectiveId = collectiveId;
        this.userName = userName;
        this.ruleId = ruleId;
        this.realName = realName;
    }
}
