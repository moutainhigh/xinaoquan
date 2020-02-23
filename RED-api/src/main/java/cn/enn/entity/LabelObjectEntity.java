package cn.enn.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@TableName("t_label_object")
@ApiModel(value = "标签对象")
public class LabelObjectEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 标签对象编码
     */
    @TableId
    private Long labelObjectId;
    /**
     * 标签对象组织ID
     */
    private String labelObjectOrgId;
    /**
     * 标签对象组织
     */
    @ApiModelProperty("标签对象组织")
    private String labelObjectOrg;
    /**
     * 标签对象创值id
     */
    private String labelObjectCreationId;
    /**
     * 标签对象创值英文
     */
    @ApiModelProperty("标签对象创值中文")
    private String labelObjectCreation;

    /**
     * 标签对象创值中文
     */
    @ApiModelProperty("标签对象创值英文")
    private String labelObjectCreationEn;

    /**
     * 标签对象角色ID
     */
    private String labelObjectRoleId;
    /**
     * 标签对象角色
     */
    @ApiModelProperty("标签对象角色")
    private String labelObjectRole;
    /**
     * 标签对象所属标签ID
     */
    private String labelObjectLabelId;
    /**
     * 标签对象所属标签
     */
    @ApiModelProperty("标签对象所属标签")
    private String labelObjectLabel;
    /**
     * 规则id
     */
    @ApiModelProperty("规则id")
    private Long ruleId;
}
