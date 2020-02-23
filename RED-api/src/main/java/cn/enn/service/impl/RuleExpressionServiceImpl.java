package cn.enn.service.impl;

import cn.enn.dao.RuleExpressionMapper;
import cn.enn.entity.RuleExpressionEntity;
import cn.enn.service.RuleExpressionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 规则-详情 服务实现类
 * </p>
 *
 * @author Administrator
 * @since 2019-10-17
 */
@Service("RuleExpressionService")
public class RuleExpressionServiceImpl extends ServiceImpl<RuleExpressionMapper, RuleExpressionEntity> implements RuleExpressionService {

    @Override
    public List<RuleExpressionEntity> checkLabelName(String labelName) {
        Map map = new HashMap<String,String>();
        map.put("labelName",labelName);
        List<RuleExpressionEntity> list = this.baseMapper.checkLabelName(map);
        return list;
    }
}
