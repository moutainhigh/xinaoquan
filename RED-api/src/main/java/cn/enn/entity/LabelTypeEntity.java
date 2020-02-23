package cn.enn.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Administrator
 * @date 2019/10/24
 */
@Data
@TableName("t_label_type")
public class LabelTypeEntity {

    private Long labelId;
    private Long parentId;
    private String labelName;
    private Long ruleId;

}
