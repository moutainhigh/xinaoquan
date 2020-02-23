package cn.enn.service.impl;

import cn.enn.dao.LabelConditionMapper;
import cn.enn.entity.LabelConditionEntity;
import cn.enn.service.LabelConditionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service("LabelConditionService")
public class LabelConditionServiceImpl extends ServiceImpl<LabelConditionMapper, LabelConditionEntity> implements LabelConditionService {
}
