package cn.enn.base.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * 组织数据传输对象
 * @author Administrator
 */
@Data
@ApiModel(value="OrgTreeDTO对象", description="组织树对象")
public class OrgTreeDTO {

    @ApiModelProperty(value = "组织编码")
    private Integer orgCode;
    @ApiModelProperty(value = "组织名称")
    private String orgName;
    @ApiModelProperty(value = "子组列表")
    private ArrayList<OrgTreeDTO> subOrg;
}
