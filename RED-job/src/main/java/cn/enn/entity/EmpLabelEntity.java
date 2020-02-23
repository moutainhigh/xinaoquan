package cn.enn.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_emp_label")
public class EmpLabelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Long empLabelId;
    //员工编号
    private String empNo;
    //规则id
    private Long ruleId;
    //标签值
    private String labelName;
}
