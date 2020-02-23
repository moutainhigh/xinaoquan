package cn.enn.config;

import com.ql.util.express.Operator;

/**
 * 自定义操作符Joint，基于阿里规则引擎
 * @author Administrator
 */
public class JointOperator extends Operator {
    private static final long serialVersionUID = -6314289873952243601L;

    @Override
    public Object executeInner(Object[] list){
        StringBuffer str = new StringBuffer();
        for (Object data:list){
            str.append(data);
        }
        return str;
    }
}
