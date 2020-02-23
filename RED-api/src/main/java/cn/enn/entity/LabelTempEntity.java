package cn.enn.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@TableName("t_label_temp")
public class LabelTempEntity implements Serializable {

    private static final long serialVersionUID = 7557101191778956549L;
    @ApiModelProperty("编码")
    @TableId(value = "tempid", type = IdType.AUTO)
    private Long tempid;
    @ApiModelProperty("标签名称")
    private  String tempname;
    @ApiModelProperty("标签类型")
    private  String temptype;
    @ApiModelProperty("标签条件")
    private  String tempcondition;
    @ApiModelProperty("标签表达式")
    private  String tempexpression;

}
